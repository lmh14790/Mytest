package com.example.ok.testselectimage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zfdang.multiple_images_selector.ImagesSelectorActivity;
import com.zfdang.multiple_images_selector.SelectorSettings;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 732;
    @InjectView(R.id.my_picture)
    CircleImageView myPicture;

    private TextView tvResults;
    private ArrayList<String> mResults = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        Button bt = (Button) findViewById(R.id.button);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start multiple photos selector
                Intent intent = new Intent(MainActivity.this, ImagesSelectorActivity.class);
                // max number of images to be selected
                intent.putExtra(SelectorSettings.SELECTOR_MAX_IMAGE_NUMBER, 5);
                // min size of image which will be shown; to filter tiny images (mainly icons)
                intent.putExtra(SelectorSettings.SELECTOR_MIN_IMAGE_SIZE, 100000);
                // show camera or not
                intent.putExtra(SelectorSettings.SELECTOR_SHOW_CAMERA, true);
                // pass current selected images as the initial value
                intent.putStringArrayListExtra(SelectorSettings.SELECTOR_INITIAL_SELECTED_LIST, mResults);
                // start the selector
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        tvResults = (TextView) findViewById(R.id.results);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // get selected images from selector
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                mResults = data.getStringArrayListExtra(SelectorSettings.SELECTOR_RESULTS);
                assert mResults != null;

                // show results in textview
                StringBuilder sb = new StringBuilder();
                sb.append(String.format("Totally %d images selected:", mResults.size())).append("\n");
                for (String result : mResults) {
                    sb.append(result).append("\n");
                }
                tvResults.setText(sb.toString());
            }
        }
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true ;
        Bitmap bitmap = BitmapFactory.decodeFile(mResults.get(0),newOpts);
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        Toast.makeText(this,w+"",Toast.LENGTH_SHORT).show();
        newOpts.inJustDecodeBounds = false ;
        double d1=w/100;
        double d2=h/100;
        newOpts.inSampleSize = (int) Math.min(d1,d2);
        Log.e("tag",newOpts.inSampleSize+"????????");
         bitmap= BitmapFactory.decodeFile(mResults.get(0),newOpts);
        myPicture.setImageBitmap(bitmap);
        super.onActivityResult(requestCode, resultCode, data);
    }

}
