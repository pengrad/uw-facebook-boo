package com.github.pengrad.uw_facebook_boo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.github.pengrad.uw_facebook_boo.recyclerview.ItemClickListener;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SecondActivity extends AppCompatActivity implements GraphRequest.Callback, ItemClickListener<FeedData.Post> {

    private static final String TAG = "SecondActivity";

    @Bind(R.id.progressBar) ProgressBar mProgressBar;
    @Bind(R.id.recycler_view) RecyclerView mRecyclerView;
    @Bind(R.id.anim_toolbar) Toolbar mToolbar;
    @Bind(R.id.header) ImageView mImageHeader;

    FeedRecyclerAdapter mFeedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ButterKnife.bind(this);

        initView();
        getPosts();
    }

    private void initView() {
        setSupportActionBar(mToolbar);

        Picasso.with(this).load(R.drawable.boo_header).fit().centerCrop().into(mImageHeader);

        mFeedAdapter = new FeedRecyclerAdapter(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mFeedAdapter);
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

    public void getPosts() {
        AccessToken token = AccessToken.getCurrentAccessToken();
        GraphRequest request = GraphRequest.newGraphPathRequest(token, "/Boo/feed", this);

        Bundle parameters = new Bundle();
        parameters.putString("fields", "message,full_picture,likes.limit(0).summary(true),comments.limit(0).summary(true)");
        request.setParameters(parameters);
        request.executeAsync();
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCompleted(GraphResponse graphResponse) {
        mProgressBar.setVisibility(View.GONE);
        JSONObject jsonObject = graphResponse.getJSONObject();
        if (jsonObject != null) {
            FeedData feedData = new Gson().fromJson(jsonObject.toString(), FeedData.class);
            mFeedAdapter.addAll(feedData.data);
        }
    }
}
