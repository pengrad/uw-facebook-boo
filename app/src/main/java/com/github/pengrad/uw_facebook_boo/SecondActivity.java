package com.github.pengrad.uw_facebook_boo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.github.pengrad.uw_facebook_boo.utils.AppBarViewBackgroundSwitch;
import com.github.pengrad.uw_facebook_boo.utils.AppBarViewToggle;
import com.github.pengrad.uw_facebook_boo.utils.recyclerview.EndlessRecyclerOnScrollListener;
import com.github.pengrad.uw_facebook_boo.utils.recyclerview.ItemClickListener;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SecondActivity extends AppCompatActivity implements GraphRequest.Callback, ItemClickListener<FeedData.Post>, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "SecondActivity";

    @Bind(R.id.swipeRefresh) SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.recycler_view) RecyclerView mRecyclerView;
    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.header) ImageView mImageHeader;
    @Bind(R.id.appbar) AppBarLayout mAppBarLayout;

    FeedRecyclerAdapter mFeedAdapter;
    GraphRequest mNextRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ButterKnife.bind(this);

        initView();

        // first data loading with forced refreshing view
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                getPosts();
            }
        });
    }

    private void initView() {
        setSupportActionBar(mToolbar);

        Picasso.with(this).load(R.drawable.boo_header).fit().centerCrop().into(mImageHeader);

        mFeedAdapter = new FeedRecyclerAdapter(this);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mFeedAdapter);
        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            public void onLoadMore(int current_page) {
                if (mNextRequest != null) {
                    mNextRequest.executeAsync();
                }
            }
        });

        // add darkened top on expanded image
        mAppBarLayout.addOnOffsetChangedListener(new AppBarViewBackgroundSwitch(mToolbar, R.drawable.bg_transparent_toolbar, R.drawable.bg_transparent));

        // disable swipe layout until app bar full expanded
        mAppBarLayout.addOnOffsetChangedListener(new AppBarViewToggle(mSwipeRefreshLayout));

        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_second, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            logOut();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void logOut() {
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemClick(FeedData.Post item) {
        Toast.makeText(this, "Open third activity", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        mFeedAdapter.clear();
        getPosts();
    }

    public void getPosts() {
        mSwipeRefreshLayout.setRefreshing(true);
        AccessToken token = AccessToken.getCurrentAccessToken();
        GraphRequest request = GraphRequest.newGraphPathRequest(token, "/Boo/posts", this);

        Bundle parameters = new Bundle();
        parameters.putString("fields", "message,full_picture,likes.limit(0).summary(true),comments.limit(0).summary(true)");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void onCompleted(GraphResponse graphResponse) {
        Log.d(TAG, "onCompleted() called with: " + "graphResponse = [" + graphResponse.toString() + "]");

        mSwipeRefreshLayout.setRefreshing(false);

        JSONObject jsonObject = graphResponse.getJSONObject();
        if (jsonObject != null) {
            FeedData feedData = new Gson().fromJson(jsonObject.toString(), FeedData.class);
            mFeedAdapter.addAll(feedData.data);
        }
        prepareNextRequest(graphResponse);
    }

    void prepareNextRequest(GraphResponse graphResponse) {
        mNextRequest = graphResponse.getRequestForPagedResults(GraphResponse.PagingDirection.NEXT);
        mNextRequest.setCallback(this);
    }
}
