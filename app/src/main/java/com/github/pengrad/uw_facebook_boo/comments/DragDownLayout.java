package com.github.pengrad.uw_facebook_boo.comments;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;


public class DragDownLayout extends RelativeLayout {
    private static final String TAG = "DragLayout";

    private View mCapturedView;
    private DragListener mDragListener;

    private ViewDragHelper mDragHelper;
    private int mTop;
    private int mVerticalRange;

    public DragDownLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(View capturedView, DragListener dragListener) {
        mCapturedView = capturedView;
        mDragListener = dragListener;
    }

    @Override
    protected void onFinishInflate() {
        mDragHelper = ViewDragHelper.create(this, 1.0f, new DragHelperCallback());
        super.onFinishInflate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mVerticalRange = (int) (h * 0.5);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (mDragHelper.shouldInterceptTouchEvent(event) && mDragListener.shouldIntercept(event)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mDragListener.shouldIntercept(event)) {
            mDragHelper.processTouchEvent(event);
            return true;
        } else {
            return super.onTouchEvent(event);
        }
    }

    @Override
    public void computeScroll() { // needed for automatic settling.
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public interface DragListener {
        boolean shouldIntercept(MotionEvent event);

        void onDragEnd();
    }

    public class DragHelperCallback extends ViewDragHelper.Callback {

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            mTop = top;
            if (top >= mVerticalRange) {
                mDragListener.onDragEnd();
            }
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return mVerticalRange;
        }

        @Override
        public boolean tryCaptureView(View view, int i) {
            return (view.getId() == mCapturedView.getId());
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            final int topBound = getPaddingTop();
            final int bottomBound = mVerticalRange;
            return Math.min(Math.max(top, topBound), bottomBound);
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            final int top = mTop == mVerticalRange ? mVerticalRange : getPaddingTop();

            if (mDragHelper.settleCapturedViewAt(0, top)) {
                ViewCompat.postInvalidateOnAnimation(DragDownLayout.this);
            }
        }
    }
}

