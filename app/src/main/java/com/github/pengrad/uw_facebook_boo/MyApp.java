package com.github.pengrad.uw_facebook_boo;

import android.app.Application;

import com.facebook.FacebookSdk;

/**
 * stas
 * 9/2/15
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FacebookSdk.sdkInitialize(getApplicationContext());
    }
}
