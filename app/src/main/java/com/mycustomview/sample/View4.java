package com.mycustomview.sample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.mycustomview.R;

/**
 * Created by TR 105 on 2017/9/20.
 */

public class View4 extends View {
    //设置蜘蛛网的半径
    private float r;
    private int count = 6;
    //计算没个角的弧度
    private float angle = (float) (Math.PI * 2 / count);
    private String textArr[] = {"金", "木", "水", "火", "土", "雷"};
    private double[] data = {100, 60, 60, 50, 10, 20};
    private float maxValue = 100;             //数据最大值

    public View4(Context context) {
        super(context);
        initPaint();
    }

    private int centerX;
    private int centerY;

    public View4(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initPaint();


    }

    private Paint linePaint;
    private Paint textPaint;
    private Paint pointPaint;

    private void initPaint() {
        /**
         * 线条
         */
        linePaint = new Paint();
        //设置抗锯齿
        linePaint.setAntiAlias(true);
        //设置颜色为灰色
        linePaint.setColor(Color.GRAY);
        //设置为线条
        linePaint.setStyle(Paint.Style.STROKE);
        /**
         * 文字
         */
        textPaint = new Paint();
        //设置抗锯齿
        textPaint.setAntiAlias(true);
        //设置颜色为灰色
        textPaint.setColor(Color.BLUE);
        //设置为线条
        textPaint.setStyle(Paint.Style.STROKE);
        /**
         * 数据点
         */
        pointPaint = new Paint();
        pointPaint.setAntiAlias(true);
        pointPaint.setColor(Color.BLUE);
        pointPaint.setStyle(Paint.Style.FILL_AND_STROKE);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        r = (Math.min(h, w) / 2 * 0.9f);
        centerX = w / 2;
        centerY = h / 2;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {


        drawBackground(canvas);
        drawLine(canvas);
        drawText(canvas);

        Path path = new Path();
        pointPaint.setAlpha(255);
        for (int i = 0; i < count; i++) {
            double percent = data[i] / maxValue;
            float x = (float) (centerX + r * Math.cos(angle * i) * percent);
            float y = (float) (centerY + r * Math.sin(angle * i) * percent);
            if (i == 0) {
                path.moveTo(x, centerY);
            } else {
                path.lineTo(x, y);
            }
            //绘制小圆点
            canvas.drawCircle(x, y, 4, pointPaint);
        }
        path.close();
        pointPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, pointPaint);
        pointPaint.setAlpha(127);
        //绘制填充区域
        pointPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawPath(path, pointPaint);


    }

    private void drawText(Canvas canvas) {
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float fontHeight = fontMetrics.descent - fontMetrics.ascent;
        for (int i = 0; i < count; i++) {
            float x = (float) (centerX + (r + fontHeight / 2) * Math.cos(angle * i));
            float y = (float) (centerY + (r + fontHeight / 2) * Math.sin(angle * i));
            if (angle * i >= 0 && angle * i <= Math.PI / 2) {//第4象限
                canvas.drawText(textArr[i], x, y, textPaint);
            } else if (angle * i >= 3 * Math.PI / 2 && angle * i <= Math.PI * 2) {//第3象限
                canvas.drawText(textArr[i], x, y, textPaint);
            } else if (angle * i > Math.PI / 2 && angle * i <= Math.PI) {//第2象限
                float dis = textPaint.measureText(textArr[i]);//文本长度
                canvas.drawText(textArr[i], x - dis, y, textPaint);
            } else if (angle * i >= Math.PI && angle * i < 3 * Math.PI / 2) {//第1象限
                float dis = textPaint.measureText(textArr[i]);//文本长度
                canvas.drawText(textArr[i], x - dis, y, textPaint);
            }
        }
    }

    private void drawLine(Canvas canvas) {
        Path linePath = new Path();
        for (int i = 0; i < count; i++) {
            linePath.reset();
            linePath.moveTo(centerX, centerY);
            float x = (float) (centerX + r * Math.cos(angle * i));
            float y = (float) (centerY + r * Math.sin(angle * i));
            linePath.lineTo(x, y);

            canvas.drawPath(linePath, linePaint);

        }
    }

    private void drawBackground(Canvas canvas) {
        Path linePath = new Path();
        for (int i = 1; i < count; i++) {
            linePath.reset();


            for (int j = 0; j < count; j++) {
                float x = (float) (centerX + r / (count - 1) * i * Math.cos(angle * j));
                float y = (float) (centerY + r / (count - 1) * i * Math.sin(angle * j));

                if (j == 0) {
                    linePath.moveTo(x, y);
                } else {
                    linePath.lineTo(x, y);
                }

            }

            linePath.close();
            canvas.drawPath(linePath, linePaint);

        }
    }
}
