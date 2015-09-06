package com.github.pengrad.uw_facebook_boo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.pengrad.uw_facebook_boo.feed.FeedData;
import com.github.pengrad.uw_facebook_boo.utils.TouchImageView;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ImageActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ImageActivity";
    private static final String MESSAGE = "message";
    private static final String PICTURE = "picture";

    public static void start(Activity context, FeedData.Post post) {
        Intent intent = new Intent(context, ImageActivity.class);
        intent.putExtra(MESSAGE, post.message);
        intent.putExtra(PICTURE, post.full_picture);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.slide_up, 0);
    }

    @Bind(R.id.imageView) TouchImageView mImageView;
    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.text_message) TextView mTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle("");
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String picture = intent.getStringExtra(PICTURE);
        String message = intent.getStringExtra(MESSAGE);

        mTextMessage.setText(message);

        Picasso.with(this).load(picture).into(mImageView);
        mImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mToolbar.getVisibility() == View.VISIBLE) {
            mToolbar.setVisibility(View.INVISIBLE);
            mTextMessage.setVisibility(View.INVISIBLE);
        } else {
            mToolbar.setVisibility(View.VISIBLE);
            mTextMessage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_image, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.slide_down);
    }
}
