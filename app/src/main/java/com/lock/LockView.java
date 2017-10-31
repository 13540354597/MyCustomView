package com.lock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TR 105 on 2017/10/30.
 */

public class LockView extends View {


    private List<List<Point>> pointList = new ArrayList<>();

    private List<Point> sPointList = new ArrayList<>();
    private Paint circlePaint;

    private int minR = 0;
    private int maxR = 0;

    public LockView(Context context) {
        this(context, null);
    }

    public LockView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPaint();


    }

    private void initPaint() {
        circlePaint = new Paint();
        circlePaint.setColor(Color.GRAY);
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(5);
    }

    private int lockWidth = 0;

    private int cha = 0;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        addPoint(widthMeasureSpec, heightMeasureSpec);


    }

    private void addPoint(int widthMeasureSpec, int heightMeasureSpec) {
        lockWidth = Math.min(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));

        cha = (MeasureSpec.getSize(heightMeasureSpec) - MeasureSpec.getSize(widthMeasureSpec)) / 2;
        int x = lockWidth / 3 / 2;
        minR = x / 8;
        maxR = x / 2;
        pointList.clear();
        List<Point> line1 = new ArrayList<>();
        Point point1 = new Point(Point.NORMAL, x, x + cha, 1);
        Point point2 = new Point(Point.NORMAL, x * 3, x + cha, 2);
        Point point3 = new Point(Point.NORMAL, x * 5, x + cha, 3);
        line1.add(point1);
        line1.add(point2);
        line1.add(point3);
        pointList.add(line1);

        List<Point> line2 = new ArrayList<>();
        Point point4 = new Point(Point.NORMAL, x, x * 3 + cha, 4);
        Point point5 = new Point(Point.NORMAL, x * 3, x * 3 + cha, 5);
        Point point6 = new Point(Point.NORMAL, x * 5, x * 3 + cha, 6);
        line2.add(point4);
        line2.add(point5);
        line2.add(point6);
        pointList.add(line2);


        List<Point> line3 = new ArrayList<>();
        Point point7 = new Point(Point.NORMAL, x, x * 5 + cha, 7);
        Point point8 = new Point(Point.NORMAL, x * 3, x * 5 + cha, 8);
        Point point9 = new Point(Point.NORMAL, x * 5, x * 5 + cha, 9);
        line3.add(point7);
        line3.add(point8);
        line3.add(point9);
        pointList.add(line3);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                float x = pointList.get(i).get(j).getX();
                float y = pointList.get(i).get(j).getY();

                if (pointList.get(i).get(j).getState() == Point.FOCUS) {
                    circlePaint.setColor(Color.BLUE);
                }


                canvas.drawCircle(x, y, maxR, circlePaint);
                circlePaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(x, y, minR, circlePaint);
                circlePaint.setStyle(Paint.Style.STROKE);
                circlePaint.setColor(Color.GRAY);
            }
        }


        drawLine(canvas);


    }

    private void drawLine(Canvas canvas) {

        for (int i = 0; i < sPointList.size(); i++) {

            try {
                float mx = sPointList.get(i).getX();
                float my = sPointList.get(i).getY();

                float tx = sPointList.get(i + 1).getX();
                float ty = sPointList.get(i + 1).getY();
                canvas.drawLine(mx, my, tx, ty, circlePaint);

            } catch (Exception e) {

            }


        }
        try {
            float lx = sPointList.get(sPointList.size() - 1).getX();
            float ly = sPointList.get(sPointList.size() - 1).getY();
            canvas.drawLine(lx, ly, moveX, moveY, circlePaint);
        } catch (Exception e) {

        }


    }

    float moveX = 0;
    float moveY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN: {
                moveX = event.getX();
                moveY = event.getY();

                chekFocusPoint(moveX, moveY);


                break;

            }
            case MotionEvent.ACTION_MOVE: {
                moveX = event.getX();
                moveY = event.getY();

                chekFocusPoint(moveX, moveY);

                break;
            }
            case MotionEvent.ACTION_UP: {


                checkPassword();


                break;
            }


        }

        invalidate();
        return true;
    }

    private void checkPassword() {

        String password = "";
        for (int j = 0; j < sPointList.size(); j++) {
            password += sPointList.get(j).getNumber();
        }
        Log.e("======", "密码：" + password);
        sPointList.clear();
        initNormalPoint();
    }

    private void initNormalPoint() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                pointList.get(i).get(j).setState(Point.NORMAL);
            }
        }
    }

    private Boolean inCircle = false;

    private void chekFocusPoint(float x, float y) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                float mx = pointList.get(i).get(j).getX();
                float my = pointList.get(i).get(j).getY();


                if (checkInRound(mx, my, maxR, x, y)) {
                    //如果这个点在园内那么记录这个点
                    pointList.get(i).get(j).setState(Point.FOCUS);


                    if (!sPointList.contains(pointList.get(i).get(j))) {
                        //判断当前点是否已经存在，入股已经存在那么就不在添加选中点的集合
                        sPointList.add(pointList.get(i).get(j));
                    }

                }

            }
        }
    }

    private static class Point {

        public static int NORMAL = 0;
        public static int FOCUS = 1;
        public static int EER = 2;


        private int state = NORMAL;


        private float x;
        private float y;
        private int number;

        public Point(int state, float x, float y, int number) {
            this.state = state;
            this.x = x;
            this.y = y;
            this.number = number;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }

    /**
     * @param sx
     * @param sy
     * @param r
     * @param x  需要判断的点的X
     * @param y  需要判断的点的y
     * @return
     */
    public static boolean checkInRound(float sx, float sy, float r, float x,
                                       float y) {
        // x的平方 + y的平方 开根号 < 半径
        return Math.sqrt((sx - x) * (sx - x) + (sy - y) * (sy - y)) < r;
    }

}
