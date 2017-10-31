package com.mycustomview.zen;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.mycustomview.R;

/**
 * Created by TR 105 on 2017/10/24.
 */

public class StartView extends View {
    private Context context;

    private int innerBackground = Color.GREEN;
    private int outerBackground = Color.GRAY;
    private int roundWidth = 20;
    private int progressTextSize = 15;
    private int progressTextColor = Color.RED;

    private Paint mOuterPaint;
    private Bitmap bitmapFocus, bitmapNomal;
    private int padding = 5;
    private int mPadding = 0;

    public StartView(Context context) {
        this(context, null);
    }

    public StartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        mPadding = (int) dip2px(padding);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StartView);

        int idNomal = typedArray.getResourceId(R.styleable.StartView_startNomal, 0);
        if (idNomal == 0) {
            throw new RuntimeException("idNomal  is null!");

        }
        bitmapNomal = BitmapFactory.decodeResource(getResources(), idNomal);
        int idFocus = typedArray.getResourceId(R.styleable.StartView_startFocus, 0);
        if (idFocus == 0) {
            throw new RuntimeException("idFocus  is null!");

        }
        bitmapFocus = BitmapFactory.decodeResource(getResources(), idFocus);
        initPaint();


    }

    private void initPaint() {
        mOuterPaint = new Paint();
        mOuterPaint.setAntiAlias(true);


    }

    private int startCount = 5;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = (bitmapNomal.getWidth() * startCount) + ((startCount + 1) * mPadding);

        int height = bitmapNomal.getHeight();
        setMeasuredDimension(width, height);

    }

    private int sp2px(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    private float dip2px(int dip) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bitmapNomal == null) {
            Toast.makeText(context, "bitmapNomal==null", Toast.LENGTH_SHORT).show();
        }


        for (int i = 0; i < startCount; i++) {

            int x = i * bitmapNomal.getWidth() + mPadding * (i + 1);




            if (i<focusCount){
                canvas.drawBitmap(bitmapFocus, x, 0, null);
            }else {
                canvas.drawBitmap(bitmapNomal, x, 0, null);
            }



        }

    }

    private int focusCount = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_DOWN:

                float x = event.getX();//当前手指的位置

                Log.e("=====",""+getWidth() / 5);

                int z = (int) (x / (getWidth() / 5));

                if (z < 0) {
                    focusCount = 0;
                } else {

                    if (z != focusCount) {
                        focusCount = z;
                        invalidate();
                    }
                }

                break;

        }


        return true;
    }
}
