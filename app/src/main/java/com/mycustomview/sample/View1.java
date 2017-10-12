package com.mycustomview.sample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.mycustomview.R;

/**
 * Created by TR 105 on 2017/9/20.
 */

public class View1 extends View {

    private Bitmap bitmap;
    public View1(Context context) {
        super(context);
        bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background);
    }

    public View1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setTextSize(30);
        paint.setStyle(Paint.Style.STROKE);//设置画笔的样式为空心
        canvas.drawText("This is onDraw", 0, 30, paint);
        canvas.drawLine(0, 60, 100, 60, paint);
        //canvas.drawRect(0,90,100,190,paint);


        /**
         * 绘制矩形
         */
//        Rect r=new Rect(0,90,100,190);
//        canvas.drawRect(r, paint);

        /**
         * 绘制圆角矩形
         */
//        RectF r = new RectF(0, 90, 100, 190);
//        canvas.drawRoundRect(r,10,10,paint);
        /**
         * 绘制圆形
         */
//        RectF r = new RectF(0, 90, 100, 190);
//        canvas.drawCircle(50, 270, 50, paint);
        /**
         * 绘制Bitmap
         */

        canvas.drawBitmap(bitmap,0,350,paint);
    }
}
