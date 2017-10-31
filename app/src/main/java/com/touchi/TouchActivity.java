package com.touchi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.mycustomview.R;

/**
 * Created by TR 105 on 2017/10/28.
 */

public class TouchActivity extends AppCompatActivity {
    private String TAG = "TouchActivity:";


    ChildView child;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.touch_activity);
        child = findViewById(R.id.child);


        child.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.e(TAG, "onTouch()");

                return false;
            }
        });
        child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick()");
            }
        });
    }
}
