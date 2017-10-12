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
 * <p>
 * <p>
 * 使用贝塞尔曲线化一个纯园
 *
 *
 */

public class View6 extends View {
    private float specialValue = 0.551915024494f;
    private int r = 50;
    private Paint mPaint;

    public View6(Context context) {
        super(context);

        init();

    }


    public View6(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(r, r);

        Path path = new Path();
        path.moveTo(0, r);
        path.cubicTo(specialValue * r, r, r, specialValue * r, r, 0);
        path.cubicTo(r, -specialValue * r, specialValue * r, -r, 0, -r);
        path.cubicTo(-specialValue * r, -r, -r, -specialValue * r, -r, 0);
        path.cubicTo(-r, specialValue * r, -specialValue * r, r, 0, r);
        canvas.drawPath(path, mPaint);
    }
}
