package com.github.pengrad.uw_facebook_boo.utils;

import android.support.design.widget.AppBarLayout;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * stas
 * 9/4/15
 * Switch view enabled/disabled for expanded/collapsed app bar
 */
public class AppBarViewToggle implements AppBarLayout.OnOffsetChangedListener {

    private WeakReference<View> mViewRef;

    public AppBarViewToggle(View view) {
        mViewRef = new WeakReference<>(view);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        View view = mViewRef.get();
        if (view != null) {
            if (verticalOffset == 0) { // appBar full open
                view.setEnabled(true);
            } else {
                view.setEnabled(false);
            }
        }
    }
}
