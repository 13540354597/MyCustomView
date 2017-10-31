package com.mycustomview.zen;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Switch;

import com.mycustomview.R;

/**
 * Created by TR 105 on 2017/10/24.
 */

public class ChangeView extends View {

    private int mRectColor = Color.BLACK;
    private int mCircleColor = Color.RED;

    private int mTriangleColor = Color.GREEN;
    private Shape mShape = Shape.Triangle;


    private Paint mRectPaint, mCirclePaint, mTrianglePaint;


    public ChangeView(Context context) {
        this(context, null);
    }

    public ChangeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChangeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        initPaint();

    }

    private void initPaint() {
        mRectPaint = new Paint();
        mRectPaint.setAntiAlias(true);
        mRectPaint.setStyle(Paint.Style.FILL);
        mRectPaint.setColor(mRectColor);

        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setColor(mCircleColor);

        mTrianglePaint = new Paint();
        mTrianglePaint.setAntiAlias(true);
        mTrianglePaint.setStyle(Paint.Style.FILL);
        mTrianglePaint.setColor(mTriangleColor);


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

    private Path path;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        switch (mShape) {
            case Circle: {

                canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2, mCirclePaint);

                break;
            }
            case Square: {
                RectF rectF = new RectF(0, 0, getWidth(), getHeight());
                canvas.drawRect(rectF, mRectPaint);
                break;
            }
            case Triangle: {


                if (path == null) {
                    path = new Path();
                    path.moveTo(getWidth() / 2, 0);
                    path.lineTo(0, (float) ((getWidth() / 2) / Math.tan(Math.toRadians(30))));
                    path.lineTo(getWidth(), (float) ((getWidth() / 2) / Math.tan(Math.toRadians(30))));
                    path.close();


                }

                canvas.drawPath(path, mTrianglePaint);
                break;
            }

        }

    }

    public enum Shape {
        Circle, Square, Triangle
    }


    public void changeShape() {
        switch (mShape) {
            case Square: {
                mShape=Shape.Triangle;
                break;
            }
            case Triangle: {
                mShape=Shape.Circle;

                break;
            }
            case Circle: {
                mShape=Shape.Square;

                break;
            }
        }

        invalidate();
    }
}
