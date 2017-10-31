package com.touchi;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by TR 105 on 2017/10/28.
 */

public class ChildView extends View {

    private String TAG = "ChildView:";

    public ChildView(Context context) {
        super(context);
    }

    public ChildView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ChildView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        Log.e(TAG, "dispatchTouchEvent()");
        return super.dispatchTouchEvent(event);
    }

    //onTouch是在dispatchTouchEvent()之后


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "onTouchEvent()");
        return super.onTouchEvent(event);
    }

    //onClick是在onTouchEvent之后
}
