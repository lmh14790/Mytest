package com.example.ok.mytest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Toast;

/**
 * Created by Ok on 2016/9/5.
 * 通过制自定义补间动画来实现平滑滚动
 */
public class MyScoller3 extends ViewGroup{
    private int width,higth;
    public int lWidth;
    double oldTime,newTime;
    private double oldX,nowX;
    private int move;
    private long time;
    private boolean isMove;
    SoomthMoveAnimation mAnimation;
    /**
     * 要滑动到的目标位置
     */
    private int mTargetPosition;
    /**
     * 当前所处位置
     */
    private int mCourrentPosition;
    /**
     * 判断是左滑还是右滑
     */
    private int mode;
    public MyScoller3(Context context) {
        super(context);
        initView(context);
    }

    public MyScoller3(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MyScoller3(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }
    public void initView(Context context){
        DisplayMetrics dm=new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        width=dm.widthPixels;
         higth=dm.heightPixels;
            initTouchEvent();
        mAnimation=new SoomthMoveAnimation(this);
        mAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isMove=true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isMove=false;
                mCourrentPosition=getScrollX();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    /**
     *
     * @param mode
     * @param dx
     * @param duration
     */
    public void startMove2(int mode,int dx,int duration){
        if(mode==1){
            if(mTargetPosition-dx<0){
                Toast.makeText(this.getContext(),"已经到第一页了",Toast.LENGTH_SHORT).show();
                return;
            }
            mTargetPosition += -dx;
        }else {
            if(mTargetPosition-dx>lWidth){
                Toast.makeText(this.getContext(),"已经到最后一页了",Toast.LENGTH_SHORT).show();
                return;
            }
            mTargetPosition+=-dx;
        }
        mAnimation.setDuration(duration);
        mAnimation.setCourrentPosition(mCourrentPosition);
        mAnimation.setTargetPosition(mTargetPosition);
        startAnimation(mAnimation);
       // animator.setDuration(duration);
        //animator.
//        //animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//
//            }
//        });

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(width,higth);
        int childCount=getChildCount();
        measureChildren(widthMeasureSpec,heightMeasureSpec);
//        for (int i = 0; i < childCount; i++) {
//            View child=getChildAt(i);
//            Log.e("tag","----------------------------------------------------------------"+i+"---------------------------");
//            widthMeasureSpec=MeasureSpec.makeMeasureSpec(width/10,MeasureSpec.EXACTLY);
//            heightMeasureSpec=MeasureSpec.makeMeasureSpec(higth/10,MeasureSpec.EXACTLY);
//            child.measure(widthMeasureSpec,heightMeasureSpec);
//    }

}

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        int startLeft = 0; // 每个子视图的起始布局坐标
        int startTop = 40; // 间距设置为10px 相当于 android：marginTop= "10px"
        int childCount = getChildCount();
        for (int l = 0; l < childCount;l++) {
            View child = getChildAt(l);

            child.layout(startLeft, startTop,
                    startLeft +width,
                    startTop+higth);
            startLeft = startLeft + width;
            //校准每个子View的起始布局位置
            //三个子视图的在屏幕中的分布如下 [0 , 320] / [320,640] / [640,960]
        }
        lWidth=startLeft-1080;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

      public void initTouchEvent(){
          this.setOnTouchListener(new OnTouchListener() {
              @Override
              public boolean onTouch(View v, MotionEvent event) {
                  switch (event.getAction()){
                      case  MotionEvent.ACTION_DOWN:
                          oldX= event.getX();
                          oldTime= System.currentTimeMillis();
                          break;
                       case MotionEvent.ACTION_MOVE:
                           nowX= event.getX();
                           newTime=System.currentTimeMillis();
                           move+=(nowX-oldX);
                           time+=(newTime-oldTime);
                           if(Math.abs(move)>=5&&!isMove){
                               Log.e("tag","-----------------进来了----------------");
                               if(move>=0){
                                   startMove2(1, move, (int) (time)/10);
                               }else {
                                   startMove2(0,move, (int) (time)/10);
                               }
                               move=0;
                               time=0;
                           }

                           oldX=(int) event.getX();
                           oldTime=System.currentTimeMillis();
                           break;
                       case  MotionEvent.ACTION_UP:
                          // mScroller.forceFinished(true);
                           oldX= (int) event.getX();
                           oldTime= System.currentTimeMillis();
                           //animator.end();
                           break;
                  }

                  return true;
              }
          });

      }
}

