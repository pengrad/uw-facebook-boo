package com.github.pengrad.uw_facebook_boo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.login.widget.LoginButton;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        LoginButton loginButton = (LoginButton) findViewById(R.id.loginButton);
    }
}
