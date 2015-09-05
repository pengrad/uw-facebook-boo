package com.github.pengrad.uw_facebook_boo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.github.pengrad.uw_facebook_boo.utils.Logger.log;

public class LoginActivity extends AppCompatActivity implements FacebookCallback<LoginResult> {

    public static final String TAG = "LoginActivity";

    CallbackManager mCallbackManager;
    LoginManager mLoginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        mCallbackManager = CallbackManager.Factory.create();
        mLoginManager = LoginManager.getInstance();
        mLoginManager.registerCallback(mCallbackManager, this);

        if (isLoggedIn()) {
            openFeedScreen();
        }
    }

    @OnClick(R.id.buttonFbLogin)
    void connectFacebook() {
        mLoginManager.logInWithPublishPermissions(this, null);
    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null && !accessToken.isExpired();
    }

    public void openFeedScreen() {
        Intent intent = new Intent(this, FeedActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

    /* Facebook Login callbacks */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        log(TAG, "onSuccess() called with: " + "loginResult = [" + loginResult + "]");
        openFeedScreen();
    }

    @Override
    public void onCancel() {
        Toast.makeText(this, "Login canceled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(FacebookException e) {
        log(TAG, "onError() called with: " + "e = [" + e + "]", e);
        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
    }
}
