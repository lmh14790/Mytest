package com.example.ok.mytest;

import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @InjectView(R.id.MyScoller)
    MyScoller mMyScoller;
    @InjectView(R.id.myButton)
    Button mMyButton;
    @InjectView(R.id.myButton1)
    Button mMyButton1;
    private int wdith;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        wdith = dm.widthPixels;
        mMyButton.setOnClickListener(this);
        mMyButton1.setOnClickListener(this);
       // Log.e("tag","===================================onCreate======================");

    }

    @Override
    protected void onStart() {
        super.onStart();
      //  Log.e("tag","===================================onStart======================");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
       // Log.e("tag","===================================onRestart======================");
    }

    @Override
    protected void onResume() {
        super.onResume();
       // Log.e("tag","===================================onResume======================");
    }

    @Override
    protected void onPause() {
        super.onPause();
      //  Log.e("tag","===================================onPause======================");

    }

    @Override
    protected void onStop() {
        super.onStop();
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = pm.isScreenOn();
       // Log.e("tag","===================================onStop======================"+isScreenOn);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // Log.e("tag","===================================onDestroy======================");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.myButton:
                if(mMyScoller.getScrollX()==mMyScoller.lWidth){
                    Toast.makeText(this,"已经到最后一页",Toast.LENGTH_SHORT).show();
                    return;
                }
                mMyScoller.startMove(1,1080,3000);
                Log.e("tag",mMyScoller.getScrollX()+"asdasdsaaaaaa");
                break;
            case R.id.myButton1:
                if(mMyScoller.getScrollX()==0){
                    Toast.makeText(this,"已经到第一页",Toast.LENGTH_SHORT).show();
                    return;
                }
                mMyScoller.startMove(2,1080,3000);
               //mMyScoller.scrollBy(-wdith, 0);
//                Intent intent=new Intent(this,Main2Activity.class);
//                startActivity(intent);
                Log.e("tag",mMyScoller.getScrollX()+"asdasdsaaaaaa");
                break;

        }
    }

    //private class ScreenBroadcastReceiver
}
