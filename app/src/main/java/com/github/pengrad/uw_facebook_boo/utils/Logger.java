package com.github.pengrad.uw_facebook_boo.utils;

import android.util.Log;

import com.github.pengrad.uw_facebook_boo.BuildConfig;

/**
 * Stas Parshin
 * 05 September 2015
 */
public class Logger {

    public static void log(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void log(String tag, String msg, Throwable tr) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, msg, tr);
        }
    }

}
