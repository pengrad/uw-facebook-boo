package com.github.pengrad.uw_facebook_boo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

public class SecondActivity extends AppCompatActivity implements GraphRequest.Callback {

    private static final String TAG = "SecondActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        getPosts();
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
        Log.d(TAG, "onCompleted() called with: " + "graphResponse = [" + graphResponse.getJSONObject() + "]");

    }
}
