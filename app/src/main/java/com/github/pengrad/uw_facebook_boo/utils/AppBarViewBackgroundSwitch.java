package com.github.pengrad.uw_facebook_boo.utils;

import android.support.annotation.DrawableRes;
import android.support.design.widget.AppBarLayout;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * stas
 * 9/4/15
 */
public class AppBarViewBackgroundSwitch implements AppBarLayout.OnOffsetChangedListener {

    private static final String TAG = "AppBarSwitch";

    private static final int OFFSET = 50; // dp

    private WeakReference<View> mViewRef;
    private int mExpandedRes, mCollapsedRes;

    public AppBarViewBackgroundSwitch(View view, @DrawableRes int expandedRes, @DrawableRes int collapsedRes) {
        mViewRef = new WeakReference<>(view);
        mExpandedRes = expandedRes;
        mCollapsedRes = collapsedRes;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        View view = mViewRef.get();
        if (view != null) {
//            Log.d(TAG, "onOffsetChanged() called with: " + "verticalOffset = [" + verticalOffset + "]" + " toolbarHeight = " + view.getHeight());
            if (Math.abs(verticalOffset) - OFFSET <= view.getHeight()) { // app bar enough opened
                view.setBackgroundResource(mExpandedRes);
            } else {
                view.setBackgroundResource(mCollapsedRes);

            }
        }
    }
}
