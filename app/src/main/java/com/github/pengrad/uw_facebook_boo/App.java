package com.github.pengrad.uw_facebook_boo;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.squareup.leakcanary.LeakCanary;

/**
 * stas
 * 9/2/15
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FacebookSdk.sdkInitialize(getApplicationContext());
        LeakCanary.install(this);
    }
}
