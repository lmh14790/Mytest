package com.example.ok.mytest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class Main3Activity extends AppCompatActivity {

    @InjectView(R.id.MyScoller)
    MyScoller3 mMyScoller;
    @InjectView(R.id.myButton)
    Button mMyButton;
    @InjectView(R.id.myButton1)
    Button mMyButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.myButton, R.id.myButton1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.myButton:
                Intent intent=new Intent(this,Main2Activity.class);
                startActivity(intent);
                break;
            case R.id.myButton1:
                Intent intent1=new Intent(this,Main2Activity.class);
                startActivity(intent1);
                break;
        }
    }
}
