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

public class LetterView extends View {
    private Context context;


    private Paint mTextPaint, mTextPaint2;
    public static String[] mLetters = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"};

    public LetterView(Context context) {
        this(context, null);
    }

    public LetterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;


        initPaint();


    }

    private void initPaint() {
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStrokeWidth(sp2px(16));
        mTextPaint.setColor(Color.BLACK);

        mTextPaint2 = new Paint();
        mTextPaint2.setAntiAlias(true);
        mTextPaint2.setStrokeWidth(sp2px(16));
        mTextPaint2.setColor(Color.GREEN);
    }

    private int itemHeight = 0;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int textWidth = (int) mTextPaint.measureText("A");
        int width = getPaddingLeft() + textWidth + getPaddingRight();
        int height = MeasureSpec.getSize(heightMeasureSpec);

        itemHeight = (height - getPaddingTop() - getPaddingBottom()) / mLetters.length;
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

        for (int i = 0; i < mLetters.length; i++) {


            if (i == touchCount) {
                drawText(canvas, i, mTextPaint2);
            } else {
                drawText(canvas, i, mTextPaint);
            }


        }


    }

    private void drawText(Canvas canvas, int i, Paint paint) {
        String text = mLetters[i];
        Rect textBound = new Rect();
        paint.getTextBounds(text, 0, text.length(), textBound);
        int x = getWidth() / 2 - textBound.width() / 2;
        int letterCenterY = i * itemHeight + itemHeight / 2 + getPaddingTop();
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int baseLine = letterCenterY + dy;
        canvas.drawText(text, x, baseLine, paint);
    }

    private int touchCount = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:

                float touchY = event.getY();
                int count = (int) ((touchY - getPaddingTop()) / itemHeight);


                if (touchCount == count) {
                    break;
                }
                touchCount = count;
                letterViewListener.onTouch(mLetters[touchCount],false);

                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                letterViewListener.onTouch(mLetters[touchCount],true);
                break;


        }

        return true;
    }


    private LetterViewListener letterViewListener;
    public void setOnLetterViewTouchListener(LetterViewListener letterViewListener){
        this.letterViewListener=letterViewListener;
    }

    public interface LetterViewListener {

        void onTouch(String str, boolean b);

    }
}
