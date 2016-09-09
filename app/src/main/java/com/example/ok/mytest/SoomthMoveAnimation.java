package com.example.ok.mytest;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by Ok on 2016/9/9.
 */
public class SoomthMoveAnimation extends Animation {
    private View mView;
     /**
     * 要滑动到的目标位置
     */
    private int mTargetPosition;
    private int dx;
    public int getCourrentPosition() {
        return mCourrentPosition;
    }

    public void setCourrentPosition(int courrentPosition) {
        mCourrentPosition = courrentPosition;
    }

    public int getTargetPosition() {
        return mTargetPosition;
    }

    public void setTargetPosition(int targetPosition) {
        mTargetPosition = targetPosition;
    }

    /**
     * 当前所处位置
     */
    private int mCourrentPosition;
    public SoomthMoveAnimation(View view) {
        mView = view;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        dx=mTargetPosition-mCourrentPosition;
        setFillAfter(true);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
         mView.scrollTo((int)(mCourrentPosition+(dx*interpolatedTime)),0);
    }
}
