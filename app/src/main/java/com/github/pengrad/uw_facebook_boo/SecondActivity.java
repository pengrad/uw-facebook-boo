package com.github.pengrad.uw_facebook_boo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.github.pengrad.uw_facebook_boo.recyclerview.ItemClickListener;
import com.google.gson.Gson;

public class SecondActivity extends AppCompatActivity implements GraphRequest.Callback, ItemClickListener<FeedData.Post> {

    private static final String TAG = "SecondActivity";
    private FeedRecyclerAdapter mFeedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initView();
        getPosts();
    }

    private void initView() {

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
    }

    @Override
    public void onCompleted(GraphResponse graphResponse) {
        String json = graphResponse.getJSONObject().toString();
        FeedData feedData = new Gson().fromJson(json, FeedData.class);
        mFeedAdapter.addAll(feedData.data);
    }

    @Override
    public void onItemClick(FeedData.Post item) {
        Toast.makeText(this, "Open second activity", Toast.LENGTH_SHORT).show();
    }
}
