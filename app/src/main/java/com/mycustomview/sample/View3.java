package com.mycustomview.sample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.mycustomview.R;

/**
 * Created by TR 105 on 2017/9/20.
 */

public class View3 extends View {

    Paint mPaint = new Paint();

    private int mWidth = 0;
    private int mHeight = 0;

    public View3(Context context) {
        super(context);
        // 创建画笔
        mPaint.setColor(Color.BLACK);           // 画笔颜色 - 黑色
        mPaint.setStyle(Paint.Style.STROKE);    // 填充模式 - 描边
        mPaint.setStrokeWidth(1);
    }

    public View3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w;
        mHeight = h;

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //drawSanJiao(canvas);
        //drawJuXing(canvas);
        //drawCircleAndJuXing(canvas);

        drawLineAndCircle(canvas);
    }

    private void drawLineAndCircle(Canvas canvas) {
        canvas.translate(mWidth / 2, mHeight / 2);  // 移动坐标系到屏幕中心
        canvas.scale(1, -1);                         // <-- 注意 翻转y坐标轴

        Path path = new Path();
        path.lineTo(100, 100);

        RectF oval = new RectF(0, 0, 300, 300);

        path.addArc(oval, 0, 270);
        //path.arcTo(oval,0,270,true);             // <-- 和上面一句作用等价

        canvas.drawPath(path, mPaint);
    }

    private void drawCircleAndJuXing(Canvas canvas) {
        canvas.translate(mWidth / 2, mHeight / 2);  // 移动坐标系到屏幕中心
        canvas.scale(1, -1);                         // <-- 注意 翻转y坐标轴

        Path path = new Path();
        Path src = new Path();

        path.addRect(-200, -200, 200, 200, Path.Direction.CW);
        src.addCircle(0, 0, 100, Path.Direction.CW);
        // src.addPath(path,0,200);
        path.addPath(src, 0, 200);

        mPaint.setColor(Color.BLACK);           // 绘制合并后的路径
        canvas.drawPath(path, mPaint);
    }

    private void drawJuXing(Canvas canvas) {
        /**
         * 绘制矩形
         */
        canvas.translate(mWidth / 2, mHeight / 2);  // 移动坐标系到屏幕中心

        Path path = new Path();

        path.addRect(-200, -200, 200, 200, Path.Direction.CW);

        canvas.drawPath(path, mPaint);
    }

    private void drawSanJiao(Canvas canvas) {
        /**
         * 绘制3角形
         */
        canvas.translate(mWidth / 2, mHeight / 2);  // 移动坐标系到屏幕中心(宽高数据在onSizeChanged中获取)
        Path path = new Path();
        path.lineTo(100, 100);                      // lineTo
        // path.moveTo(100,50);//移动下一次操作的起点位置,不影响之前操作
        path.lineTo(100, 0);
        path.close();
        //path.setLastPoint(100,50);//设置之前操作的最后一个点位置影响之前操作
        //path.lineTo(400, 400);
        canvas.drawPath(path, mPaint);              // 绘制Path
    }
}
