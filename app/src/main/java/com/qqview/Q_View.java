package com.qqview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2017/11/14.
 */

public class Q_View extends View {
    private Paint mPaint, mLinePaint;


    private PointF changePointF = new PointF();
    private PointF movePointF = new PointF();

    private int changeCircleR = 10;
    private int moveCircleR = 10;
    private int maxDistance = 100;

    public Q_View(Context context) {
        this(context, null);
    }

    public Q_View(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Q_View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);


        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setDither(true);
        mLinePaint.setColor(Color.RED);
        mLinePaint.setStrokeWidth(2);
        mLinePaint.setStyle(Paint.Style.STROKE);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        drawCircle(canvas);


    }


    private float distance = 0;

    private void drawCircle(Canvas canvas) {

        //如果拉动的距离大于100那么让它消失
        distance = (float) Math.abs(getDistance(movePointF, changePointF));


        if (distance <= 100) {
            int r = (int) ((1 - ((distance) / 100)) * changeCircleR);

            r = Math.max(r, 3);

            canvas.drawCircle(changePointF.x, changePointF.y, r, mPaint);
            getPath(canvas, r);
        }


        canvas.drawCircle(movePointF.x, movePointF.y, moveCircleR, mPaint);


    }

    private void getPath(Canvas canvas, int r) {
        Path path = new Path();

        float tanB = (changePointF.x - movePointF.x) / (changePointF.y - movePointF.y);
        // 求角a
        double arcTanB = Math.atan(tanB);

        float p1x = (float) (changePointF.x + Math.cos(arcTanB) * r);
        float p1y = (float) (changePointF.y - Math.sin(arcTanB) * r);
        float p2x = (changePointF.x + movePointF.x) / 2;
        float p2y = (changePointF.y + movePointF.y) / 2;

        float p3x = (float) (movePointF.x + Math.cos(arcTanB) * moveCircleR);
        float p3y = (float) (movePointF.y - Math.sin(arcTanB) * moveCircleR);


        float p4x = (float) (movePointF.x - Math.cos(arcTanB) * moveCircleR);
        float p4y = (float) (movePointF.y + Math.sin(arcTanB) * moveCircleR);


        float p5x = (float) (changePointF.x - Math.cos(arcTanB) * r);
        float p5y = (float) (changePointF.y + Math.sin(arcTanB) * r);

//        path.moveTo(p1x, p1y);
//        //path.lineTo(p1x, p1y);
//        path.lineTo(p3x, p3y);
//
//        path.lineTo(p4x, p4y);
//        path.lineTo(p5x, p5y);
//        path.close();


        path.moveTo(p1x, p1y);
        path.quadTo(p2x, p2y, p3x, p3y);
        path.lineTo(p4x, p4y);
        path.quadTo(p2x, p2y, p5x, p5y);
        path.close();
        canvas.drawPath(path, mPaint);


//        Path path = new Path();
//
//        path.lineTo(200, 200);
//        path.lineTo(400, 0);
//
//        canvas.drawPath(path, mLinePaint);
    }


    private float dX = 0f;
    private float dY = 0f;
    private float mX = 0f;
    private float mY = 0f;

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        switch (event.getAction()) {


            case MotionEvent.ACTION_DOWN: {

                dX = event.getX();
                dY = event.getY();
                changePointF.set(dX, dY);
                movePointF.set(dX, dY);

                break;
            }
            case MotionEvent.ACTION_MOVE: {
                mX = event.getX();
                mY = event.getY();
                movePointF.set(mX, mY);
                break;
            }
            case MotionEvent.ACTION_UP: {


                break;
            }

        }


        invalidate();

        return true;
    }


    /**
     * 获取两个圆之间的距离
     *
     * @param point1
     * @param point2
     * @return
     */
    private double getDistance(PointF point1, PointF point2) {
        return Math.sqrt((point1.x - point2.x) * (point1.x - point2.x) + (point1.y - point2.y) * (point1.y - point2.y));
    }
}
