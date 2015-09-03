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

public class MainActivity extends AppCompatActivity implements FacebookCallback<LoginResult> {

    CallbackManager mCallbackManager;
    LoginManager mLoginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mCallbackManager = CallbackManager.Factory.create();
        mLoginManager = LoginManager.getInstance();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        Toast.makeText(this, "Login successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel() {
        Toast.makeText(this, "Login canceled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(FacebookException e) {
        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    @OnClick(R.id.buttonFbLogin)
    void connectFacebook() {
        mLoginManager.logInWithPublishPermissions(this, null);
    }

    public void openBooFeed() {
        if (!isLoggedIn()) {
            Toast.makeText(this, "Login first", Toast.LENGTH_SHORT).show();
            return;
        }
        startActivity(new Intent(this, SecondActivity.class));
    }
}
