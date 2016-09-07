package com.example.ok.testselectimage;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by Ok on 2016/8/22.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // the following line is important
        Fresco.initialize(getApplicationContext());
    }
}
