package com.example.ok.mytest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by Ok on 2016/9/5.
 */
public class MyScoller extends ViewGroup{
    private int width,higth;
    public int lWidth;
    private Scroller mScroller;
    double oldTime,newTime;
    private double oldX,nowX;
    private int move;
    private long time;
    private boolean isMove;
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
    public MyScoller(Context context) {
        super(context);
        initView(context);
    }

    public MyScoller(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MyScoller(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }
    public void initView(Context context){
        LinearLayout l1=new LinearLayout(context);
        l1.setBackgroundColor(Color.BLUE);
        LinearLayout l2=new LinearLayout(context);
        l1.setBackgroundColor(Color.GREEN);
        LinearLayout l3=new LinearLayout(context);
        l1.setBackgroundColor(Color.BLACK);

        try {
            mScroller=new Scroller(context, LinearInterpolator.class.newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

//        addView(l1,0);
//        addView(l2,1);
//        addView(l3,2);

        DisplayMetrics dm=new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        width=dm.widthPixels;
         higth=dm.heightPixels;
            initTouchEvent();
    }

    /**
     * 设置开始移动至下一屏  的位移大小和动画时常
     */
    public void startMove(int mode,int dx,int duration){
        if(mode==1){
            if(mTargetPosition==lWidth){
                return;
            }
            mTargetPosition += dx;
        }else {
            if(mTargetPosition==0){
                return;
            }
            mTargetPosition-=dx;
        }
        Log.e("tag", "----startMove---- mTargetPosition " + mTargetPosition);
        mScroller.startScroll(mCourrentPosition,0,mTargetPosition-mCourrentPosition,0,duration);
        invalidate();

    }

    @Override
    public void computeScroll() {
      if(mScroller.computeScrollOffset()){
          Log.e("tag", mScroller.getCurrX() + "======" + mScroller.getCurrY());
          scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
          isMove=true;
          Log.e("tag", "### getleft is " + getLeft() + " ### getRight is " + getRight());
         invalidate();
      }else {
          mCourrentPosition=getScrollX();
          Log.e("tag","scrollx"+mCourrentPosition);
          isMove=false;
      }
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
        Log.i("tag", "--- start onLayout --");
        int startLeft = 0; // 每个子视图的起始布局坐标
        int startTop = 40; // 间距设置为10px 相当于 android：marginTop= "10px"
        int childCount = getChildCount();
        Log.i("tag", "--- onLayout childCount is -->" + childCount);
        for (int l = 0; l < childCount;l++) {
            View child = getChildAt(l);
            Log.e("tag",child.getMeasuredWidth()+"????????????????????????");
            child.layout(startLeft, startTop,
                    startLeft +width,
                    startTop+higth);
            startLeft = startLeft + width;
            Log.e("tag",startLeft+"===============");
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
                          Log.e("tag","Oldx============="+oldX);
                          oldTime= System.currentTimeMillis();
                          break;
                       case MotionEvent.ACTION_MOVE:
                           nowX= event.getX();
                           newTime=System.currentTimeMillis();
                           Log.e("tag","Oldx============="+oldX);
                           Log.e("tag","nowX============="+nowX);

                           move+=(nowX-oldX);
                           time+=(newTime-oldTime);
                           Log.e("tag","Move============="+move);
                           Log.e("tag","time============="+time);
                           if(Math.abs(move)>=5&&!isMove){
                               if(move>0){
                                   startMove(1, -move, (int) (time));
                               }else {
                                   startMove(0,move, (int) (time));
                               }
                               move=0;
                               time=0;
                           }

                           oldX=(int) event.getX();
                           oldTime=System.currentTimeMillis();
                           break;
                       case  MotionEvent.ACTION_UP:
                           mScroller.forceFinished(true);
                           oldX= (int) event.getX();
                           oldTime= System.currentTimeMillis();
                           invalidate();
                           break;
                  }

                  return true;
              }
          });

      }
}

