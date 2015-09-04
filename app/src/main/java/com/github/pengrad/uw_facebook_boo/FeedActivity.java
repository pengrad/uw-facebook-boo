package com.github.pengrad.uw_facebook_boo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.github.pengrad.uw_facebook_boo.feed.FacebookFeedRequest;
import com.github.pengrad.uw_facebook_boo.feed.FeedData;
import com.github.pengrad.uw_facebook_boo.feed.FeedRecyclerAdapter;
import com.github.pengrad.uw_facebook_boo.utils.AppBarViewBackgroundSwitch;
import com.github.pengrad.uw_facebook_boo.utils.AppBarViewToggle;
import com.github.pengrad.uw_facebook_boo.utils.StyleMaker;
import com.github.pengrad.uw_facebook_boo.utils.recyclerview.EndlessRecyclerOnScrollListener;
import com.github.pengrad.uw_facebook_boo.utils.recyclerview.ItemClickListener;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.github.pengrad.uw_facebook_boo.utils.Logger.log;

public class FeedActivity extends AppCompatActivity implements GraphRequest.Callback, ItemClickListener<FeedData.Post>, SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = "FeedActivity";

    @Bind(R.id.swipeRefresh) SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.recycler_view) RecyclerView mRecyclerView;
    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.header) ImageView mImageHeader;
    @Bind(R.id.appbar) AppBarLayout mAppBarLayout;

    FeedRecyclerAdapter mFeedAdapter;
    GraphRequest mNextPageRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ButterKnife.bind(this);

        initView();

        // first data loading with forced refreshing view
        mSwipeRefreshLayout.post(new Runnable() {
            public void run() {
                startFeedRequest();
            }
        });
    }

    private void initView() {
        setSupportActionBar(mToolbar);

        Picasso.with(this).load(R.drawable.boo_header).fit().centerCrop().into(mImageHeader);

        // add darkened top on expanded image
        mAppBarLayout.addOnOffsetChangedListener(new AppBarViewBackgroundSwitch(mToolbar, R.drawable.bg_transparent_toolbar, R.drawable.bg_transparent));

        // disable swipe layout until app bar full expanded
        mAppBarLayout.addOnOffsetChangedListener(new AppBarViewToggle(mSwipeRefreshLayout));

        StyleMaker.swipeRefreshLayout(mSwipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mFeedAdapter = new FeedRecyclerAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mFeedAdapter);
        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            public void onLoadMore(int current_page) {
                if (mNextPageRequest != null) {
                    mNextPageRequest.executeAsync();
                }
            }
        });
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

    // Swipe Refresh Layout listener
    @Override
    public void onRefresh() {
        mFeedAdapter.clear();
        startFeedRequest();
    }

    public void startFeedRequest() {
        mSwipeRefreshLayout.setRefreshing(true);
        FacebookFeedRequest.createRequest(this).executeAsync();
    }

    // Facebook Request callback
    @Override
    public void onCompleted(GraphResponse graphResponse) {
        log(TAG, "onCompleted() called with: " + "graphResponse = [" + graphResponse.toString() + "]");

        mSwipeRefreshLayout.setRefreshing(false);

        if (graphResponse.getError() != null) {
            if (graphResponse.getError().getCategory() == FacebookRequestError.Category.LOGIN_RECOVERABLE) {
                logOut();
            }
        } else {
            FeedData feedData = new Gson().fromJson(graphResponse.getRawResponse(), FeedData.class);
            mFeedAdapter.addAll(feedData.data);

            prepareNextPageRequest(graphResponse);
        }
    }

    void prepareNextPageRequest(GraphResponse graphResponse) {
        mNextPageRequest = graphResponse.getRequestForPagedResults(GraphResponse.PagingDirection.NEXT);
        mNextPageRequest.setCallback(this);
    }
}
