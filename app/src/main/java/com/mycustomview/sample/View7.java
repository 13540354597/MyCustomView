package com.mycustomview.sample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.net.VpnService;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TR 105 on 2017/9/20.
 * <p>
 * <p>
 * 使用贝塞尔曲线化一个纯园
 */

public class View7 extends View {
    private float specialValue = 0.551915024494f;
    private int r = 50;
    private int maxTranslate = 200;

    private int animation = 1000;


    private Paint mPaint;

    private float cDistance;


    private HPoint p1 = new HPoint();
    private VPoint p2 = new VPoint();
    private HPoint p3 = new HPoint();
    private VPoint p4 = new VPoint();


    private String TAG = "=======";

    public View7(Context context) {
        super(context);
        init();

    }


    public View7(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        /**
         * 画笔
         */
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

        /**
         * path
         */
        path = new Path();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e(TAG + "onMeasure:", "宽:" + widthMeasureSpec + "高:" + heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.e(TAG + "onLayout:", "left:" + left + "top:" + top + "right:" + right + "bottom:" + bottom);
    }


    private int screenWidth;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e(TAG + "onSizeChanged:", "宽:" + w + "高:" + h);
        cDistance = specialValue * 0.45f;
        screenWidth = w;
    }


    private Path path;

    private float translateX = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        path.reset();
        canvas.translate(r, r);

        if (timePoint >= 0 && timePoint <= 0.2) {
            model1(timePoint);


        } else if (timePoint > 0.2 && timePoint <= 0.5) {
            model2(timePoint);
        } else if (timePoint > 0.5 && timePoint <= 0.8) {
            model3(timePoint);
        } else if (timePoint > 0.8 && timePoint <= 1) {
            model4(timePoint);
        }

        float offset = screenWidth - r * 2 / timePoint;

        move(offset);


        path.moveTo(p1.x, p1.y);
        path.cubicTo(p1.right.x, p1.right.y, p2.bottom.x, p2.bottom.y, p2.x, p2.y);
        path.cubicTo(p2.top.x, p2.top.y, p3.right.x, p3.right.y, p3.x, p3.y);
        path.cubicTo(p3.left.x, p3.left.y, p4.top.x, p4.top.y, p4.x, p4.y);
        path.cubicTo(p4.bottom.x, p4.bottom.y, p1.left.x, p1.left.y, p1.x, p1.y);

        canvas.drawPath(path, mPaint);


    }


    private void model0() {
        p1.x = 0;
        p1.left.x = -r * specialValue;
        p1.right.x = r * specialValue;
        p1.y = r;
        p1.left.y = r;
        p1.right.y = r;


        p2.y = 0;
        p2.top.y = -r * specialValue;
        p2.bottom.y = r * specialValue;
        p2.x = r;
        p2.top.x = r;
        p2.bottom.x = r;


        p3.x = 0;
        p3.left.x = -r * specialValue;
        p3.right.x = r * specialValue;
        p3.y = -r;
        p3.left.y = -r;
        p3.right.y = -r;

        p4.y = 0;
        p4.top.y = -r * specialValue;
        p4.bottom.y = r * specialValue;
        p4.x = -r;
        p4.top.x = -r;
        p4.bottom.x = -r;


    }

    private void model1(float timePoint) {

        //0~0.2
        model0();
        p2.x = r + r * timePoint * 5;
        p2.top.x = r + r * timePoint * 5;
        p2.bottom.x = r + r * timePoint * 5;

    }


    private void model2(float timePoint) {

        //0.2~0.5
        model1(0.2f);
        float moveX = (r / 2) * timePoint * 2;
        float moveY = timePoint * 2 * 10;


        p1.x = p1.x + moveX;
        p1.left.x = p1.left.x + moveX;
        p1.right.x = p1.right.x + moveX;

        p3.x = p3.x + moveX;
        p3.left.x = p3.left.x + moveX;
        p3.right.x = p3.right.x + moveX;


        p2.top.y = p2.top.y - moveY;
        p2.bottom.y = p2.bottom.y + moveY;
        p4.top.y = p4.top.y - moveY;
        p4.bottom.y = p4.bottom.y + moveY;


    }

    private void model3(float timePoint) {

        //0.5~0.8
        model2(0.5f);

        float moveX = r / 2 * timePoint * 1.25f;


        p1.x = p1.x + moveX;
        p1.left.x = p1.left.x + moveX;
        p1.right.x = p1.right.x + moveX;


        p3.x = p3.x + moveX;
        p3.left.x = p3.left.x + moveX;
        p3.right.x = p3.right.x + moveX;


//        p2.x = p2.x - moveX;
//        p2.top.x = p2.top.x - moveX;
//        p2.bottom.x = p2.bottom.x - moveX;


        p2.top.y = p2.top.y + 10;
        p2.bottom.y = p2.bottom.y - 10;
        p4.top.y = p4.top.y + 10;
        p4.bottom.y = p4.bottom.y - 10;
    }

    private void model4(float timePoint) {

        //0.8~1
        model3(0.8f);

        float moveX = r * timePoint;


        p1.x = p1.x - moveX;
        p1.left.x = p1.left.x - moveX;
        p1.right.x = p1.right.x - moveX;


        p3.x = p3.x - moveX;
        p3.left.x = p3.left.x - moveX;
        p3.right.x = p3.right.x - moveX;


        p2.x = p2.x - moveX;
        p2.top.x = p2.top.x - moveX;
        p2.bottom.x = p2.bottom.x - moveX;


//        p2.top.y = p2.top.y + 10;
//        p2.bottom.y = p2.bottom.y - 10;
//        p4.top.y = p4.top.y + 10;
//        p4.bottom.y = p4.bottom.y - 10;
    }


    private void move(float moveX) {


        p1.x = p1.x + moveX;
        p1.left.x = p1.left.x + moveX;
        p1.right.x = p1.right.x + moveX;


        p2.x = p2.x + moveX;
        p2.top.x = p2.top.x + moveX;
        p2.bottom.x = p2.bottom.x + moveX;


        p3.x = p3.x + moveX;
        p3.left.x = p3.left.x + moveX;
        p3.right.x = p3.right.x + moveX;


        p4.x = p4.x + moveX;
        p4.top.x = p4.top.x + moveX;
        p4.bottom.x = p4.bottom.x + moveX;


//        p1.x = p1.x + (moveX-p1.x);
//        p1.left.x = p1.left.x + (moveX-p1.left.x);
//        p1.right.x = p1.right.x + moveX;
//
//
//        p2.x = p2.x + moveX;
//        p2.top.x = p2.top.x + moveX;
//        p2.bottom.x = p2.bottom.x + moveX;
//
//
//        p3.x = p3.x + moveX;
//        p3.left.x = p3.left.x + moveX;
//        p3.right.x = p3.right.x + moveX;
//
//
//        p4.x = p4.x + moveX;
//        p4.top.x = p4.top.x + moveX;
//        p4.bottom.x = p4.bottom.x + moveX;

    }

    private float timePoint;

    private class MoveAnimation extends Animation {

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            timePoint = interpolatedTime;
            //translateX = r + r * timePoint;
            invalidate();
        }
    }

    public void startAnimation() {
        path.reset();
        timePoint = 0;
        View7.MoveAnimation move = new View7.MoveAnimation();
        move.setDuration(animation);
        move.setInterpolator(new AccelerateDecelerateInterpolator());
        //move.setRepeatCount(Animation.INFINITE);
        //move.setRepeatMode(Animation.REVERSE);
        startAnimation(move);
    }


    class VPoint {
        public float x;
        public float y;
        public PointF top = new PointF();
        public PointF bottom = new PointF();

    }

    class HPoint {
        public float x;
        public float y;
        public PointF left = new PointF();
        public PointF right = new PointF();

    }


}
