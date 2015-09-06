package com.github.pengrad.uw_facebook_boo.ui;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;

/**
 * Stas Parshin
 * 05 September 2015
 * Disabled by default, should call enable() for one cycle out/in
 */
public class ZoomAnimation {

    public static final float ALPHA = 0.7f;
    public static final int DURATION = 250;

    public static final float X = 0.9f;
    public static final float Y = 0.9f;
    public static final int PIVOT_TYPE = Animation.RELATIVE_TO_SELF;
    public static final float PIVOT = 0.5f;

    private Animation mZoomIn;
    private Animation mZoomOut;
    private boolean mEnabled;

    public ZoomAnimation() {
        mZoomIn = createZoomIn();
        mZoomOut = createZoomOut();
        mEnabled = false;
    }

    private Animation createZoomIn() {
        ScaleAnimation zoomAnimation = new ScaleAnimation(X, 1, Y, 1, PIVOT_TYPE, PIVOT, PIVOT_TYPE, PIVOT);
        AlphaAnimation alphaAnimation = new AlphaAnimation(ALPHA, 1);

        return combineAnimations(zoomAnimation, alphaAnimation);
    }

    private Animation createZoomOut() {
        ScaleAnimation zoomAnimation = new ScaleAnimation(1, X, 1, Y, PIVOT_TYPE, PIVOT, PIVOT_TYPE, PIVOT);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, ALPHA);

        return combineAnimations(zoomAnimation, alphaAnimation);
    }

    private Animation combineAnimations(Animation... animations) {
        AnimationSet animationSet = new AnimationSet(true);
        for (Animation animation : animations) {
            animationSet.addAnimation(animation);
        }
        animationSet.setDuration(DURATION);
        animationSet.setFillAfter(true);
        return animationSet;
    }

    public void enable() {
        mEnabled = true;
    }

    public void zoomOut(View view) {
        if (mEnabled) {
            view.startAnimation(mZoomOut);
        }
    }

    public void zoomIn(View view) {
        if (mEnabled) {
            view.startAnimation(mZoomIn);
            mEnabled = false;
        }
    }
}
