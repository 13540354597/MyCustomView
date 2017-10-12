package com.mycustomview.sample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.mycustomview.R;

import java.util.Random;

/**
 * Created by TR 105 on 2017/9/20.
 */

public class View2 extends View {

    private Bitmap bitmap;

    private float rx = 0;

    private MyThread myThread;

    private Paint paint = new Paint();

    private RectF rectF = new RectF(0, 60, 100, 160);
    private float sweepAngle = 0;

    public View2(Context context) {
        super(context);

    }

    public View2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);


    }


    @Override
    protected void onDraw(Canvas canvas) {

        paint.setTextSize(30);
        paint.setStyle(Paint.Style.STROKE);//设置画笔的样式为空心
        canvas.drawText("This is onDraw", rx, 30, paint);

        canvas.drawArc(rectF, 0, sweepAngle, true, paint);
        if (myThread == null) {
            myThread = new MyThread();
            myThread.start();
        }
    }

    class MyThread extends Thread {
        Random random = new Random();

        @Override
        public void run() {

            while (true) {
                rx += 3;


                if (rx > getWidth()) {
                    rx = 0 - paint.measureText("This is onDraw");
                }
                sweepAngle++;
                if (sweepAngle > 360) {
                    sweepAngle = 0;
                }
                int r = random.nextInt(256);
                int g = random.nextInt(256);
                int b = random.nextInt(256);
                paint.setARGB(255, r, g, b);
                postInvalidate();
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        }
    }
}
