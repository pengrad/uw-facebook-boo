package com.github.pengrad.uw_facebook_boo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;

import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.github.pengrad.uw_facebook_boo.comments.CommentsData;
import com.github.pengrad.uw_facebook_boo.comments.CommentsRecyclerAdapter;
import com.github.pengrad.uw_facebook_boo.comments.DragDownLayout;
import com.github.pengrad.uw_facebook_boo.comments.FacebookCommentsRequest;
import com.github.pengrad.uw_facebook_boo.utils.recyclerview.EndlessRecyclerOnScrollListener;
import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.github.pengrad.uw_facebook_boo.utils.Logger.log;


public class CommentsActivity extends AppCompatActivity implements DragDownLayout.DragListener, GraphRequest.Callback {

    private static final String TAG = "CommentsActivity";
    private static final String POST_ID = "postid";

    public static void start(Activity context, String postId) {
        Intent intent = new Intent(context, CommentsActivity.class);
        intent.putExtra(POST_ID, postId);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.slide_up, 0);
    }

    @Bind(R.id.dragLayout) DragDownLayout mDragDownLayout;
    @Bind(R.id.recycler_view) RecyclerView mRecyclerView;
    @Bind(R.id.progressbar) ProgressBar mProgressBar;

    CommentsRecyclerAdapter mCommentsAdapter;
    GraphRequest mNextPageRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        ButterKnife.bind(this);

        initView();

        String postId = getIntent().getStringExtra(POST_ID);
        FacebookCommentsRequest.createRequest(postId, this).executeAsync();

        Log.d(TAG, "onCreate() postId: " + postId);
    }

    private void initView() {
        mDragDownLayout.init(R.id.cardview, this);

        mCommentsAdapter = new CommentsRecyclerAdapter(null);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mCommentsAdapter);
        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            public void onLoadMore(int current_page) {
                if (mNextPageRequest != null) {
                    mNextPageRequest.executeAsync();
                }
            }
        });
    }

    // Facebook Request callback
    @Override
    public void onCompleted(GraphResponse graphResponse) {
        log(TAG, "onCompleted() called with: " + "graphResponse = [" + graphResponse.toString() + "]");
        mProgressBar.setVisibility(View.GONE);

        if (graphResponse.getError() != null) {
            if (graphResponse.getError().getCategory() == FacebookRequestError.Category.LOGIN_RECOVERABLE) {
                finish();
            }
        } else {
            CommentsData commentsData = new Gson().fromJson(graphResponse.getRawResponse(), CommentsData.class);
            mCommentsAdapter.addAll(commentsData.data);
            prepareNextPageRequest(graphResponse);
        }
    }

    void prepareNextPageRequest(GraphResponse graphResponse) {
        mNextPageRequest = graphResponse.getRequestForPagedResults(GraphResponse.PagingDirection.NEXT);
        if (mNextPageRequest != null) mNextPageRequest.setCallback(this);
    }

    // Should drag layout process this event
    @Override
    public boolean shouldIntercept(MotionEvent event) {
        // allow only if can't scroll up comments
        return !ViewCompat.canScrollVertically(mRecyclerView, -1);
    }

    // Callback - view dragged to the end, finish without animation
    @Override
    public void onDragEnd() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    // back pressing finish, animate slide down
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.slide_down);
    }
}
