package com.github.pengrad.uw_facebook_boo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.github.pengrad.uw_facebook_boo.recyclerview.ItemClickListener;
import com.google.gson.Gson;

import org.json.JSONObject;

public class SecondActivity extends AppCompatActivity implements GraphRequest.Callback, ItemClickListener<FeedData.Post> {

    private static final String TAG = "SecondActivity";
    private FeedRecyclerAdapter mFeedAdapter;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initView();
        getPosts();
    }

    private void initView() {
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mFeedAdapter = new FeedRecyclerAdapter(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mFeedAdapter);
    }

    private void getPosts() {
        AccessToken token = AccessToken.getCurrentAccessToken();
        GraphRequest request = GraphRequest.newGraphPathRequest(token, "/Boo/feed", this);

        Bundle parameters = new Bundle();
        parameters.putString("fields", "message,picture,comments.limit(0).summary(true)");
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

    @Override
    public void onItemClick(FeedData.Post item) {
        Toast.makeText(this, "Open third activity", Toast.LENGTH_SHORT).show();
    }
}
