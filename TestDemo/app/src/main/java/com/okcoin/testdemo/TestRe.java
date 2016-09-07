package com.okcoin.testdemo;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * Created by Ok on 2016/8/16.
 */
public class TestRe extends LinearLayout {
   private View  mHeadView;
    private LayoutParams layoutParams;
    public TestRe(Context context) {
        super(context);
        init(context);
    }

    public TestRe(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TestRe(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TestRe(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }
     public void init(Context context){
         View view= LayoutInflater.from(context).inflate(R.layout.head_view,null);
         layoutParams= (LayoutParams) this.getLayoutParams();
         this.setOrientation(VERTICAL);
         addHeadView(view);
     }
    public void addHeadView(View view){
//        ViewGroup.LayoutParams layoutParams=view.getLayoutParams();
//        layoutParams.height=0;
         //view.invalidate();
        this.addView(view,0);

    }
   public void  setHeadHight(int headHight){
       ValueAnimator animator=ValueAnimator.ofInt(0,headHight);
       animator.setDuration(2000);

   }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
