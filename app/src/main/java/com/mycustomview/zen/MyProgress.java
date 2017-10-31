package com.mycustomview.zen;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.mycustomview.R;

/**
 * Created by TR 105 on 2017/10/24.
 */

public class MyProgress extends View {

    private int innerBackground = Color.GREEN;
    private int outerBackground = Color.GRAY;
    private int roundWidth = 20;
    private int progressTextSize = 15;
    private int progressTextColor = Color.RED;

    private Paint mOuterPaint, innerPaint, textPaint;


    public MyProgress(Context context) {
        this(context, null);
    }

    public MyProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyProgress);

        innerBackground = typedArray.getColor(R.styleable.MyProgress_innerBackground, innerBackground);
        outerBackground = typedArray.getColor(R.styleable.MyProgress_outerBackground, outerBackground);
        roundWidth = (int) typedArray.getDimension(R.styleable.MyProgress_roundWidth, dip2px(10));
        progressTextSize = typedArray.getDimensionPixelSize(R.styleable.MyProgress_progressTextSize,
                sp2px(progressTextSize));
        progressTextColor = typedArray.getColor(R.styleable.MyProgress_progressTextColor, progressTextColor);

        initPaint();

    }

    private void initPaint() {
        mOuterPaint = new Paint();
        mOuterPaint.setAntiAlias(true);
        mOuterPaint.setStyle(Paint.Style.STROKE);
        mOuterPaint.setColor(outerBackground);
        mOuterPaint.setStrokeWidth(roundWidth);

        innerPaint = new Paint();
        innerPaint.setAntiAlias(true);
        innerPaint.setStyle(Paint.Style.STROKE);
        innerPaint.setColor(innerBackground);
        innerPaint.setStrokeWidth(roundWidth);
        innerPaint.setStrokeCap(Paint.Cap.ROUND);

        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setColor(progressTextColor);
        textPaint.setTextSize(progressTextSize);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(Math.min(width, height), Math.min(width, height));

    }

    private int sp2px(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    private float dip2px(int dip) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }

    private int nowProgress = 50;
    private int maxProgress = 100;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //1.绘制园
        int r = getWidth() / 2 - roundWidth / 2;
        int cx = getWidth() / 2;
        int cy = getHeight() / 2;
        int distance = roundWidth / 2;

        canvas.drawCircle(cx, cy, r, mOuterPaint);


        //2.绘制可变动的圆弧
        float percent = (float) nowProgress / maxProgress;
        RectF rectF = new RectF(0 + distance, 0 + distance, getWidth() - distance, getWidth() - distance);
        canvas.drawArc(rectF, 0, 360 * percent, false, innerPaint);

        //3.绘制文字
        String text = ((int) (percent * 100)) + "%";


        Rect textBound = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), textBound);
        int x = getWidth() / 2 - textBound.width() / 2;
        Paint.FontMetricsInt fontMetricsInt = textPaint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int baseLine = getHeight() / 2 + dy;
        canvas.drawText(text, x, baseLine, textPaint);

    }


    public void setMax(int max) {
        if (max < 0) {
        }
        this.maxProgress = max;
    }

    public void setNow(int now) {
        if (now < 0) {
        }
        this.nowProgress = now;
        invalidate();
    }
}
