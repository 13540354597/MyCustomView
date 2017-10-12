package com.mycustomview.sample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.mycustomview.R;

/**
 * Created by TR 105 on 2017/9/20.
 */

public class View5 extends View {

    private Bitmap bitmap;
    int centerX;
    int centerY;

    public View5(Context context) {
        super(context);

    }

    public View5(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(centerX, centerY);
        Paint paint = new Paint();
        paint.setTextSize(30);
        paint.setStyle(Paint.Style.STROKE);//设置画笔的样式为空心

        Path path = new Path();

        /**
         *绘制曲线有很多方式 https://segmentfault.com/a/1190000000721127
         *
         *
         * 100，100是控制点
         * 200，0是绘制的直线
         */
        path.quadTo(100, 100, 200, 0);
        canvas.drawPath(path, paint);
    }
}
