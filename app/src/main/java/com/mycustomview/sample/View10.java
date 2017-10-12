package com.mycustomview.sample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.mycustomview.R;

/**
 * Created by TR 105 on 2017/9/20.
 */

public class View10 extends View {

    private Bitmap mBitmap;             // 箭头图片
    private Matrix mMatrix;             // 矩阵,用于对图片进行一些操作

    public View10(Context context) {
        super(context);
        init(context);
    }

    public View10(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }
    private Paint mShadowPaint;
    private Matrix mShadowGradientMatrix;
    private LinearGradient mShadowGradientShader;
    private void init(Context context) {

        mBitmap = BitmapFactory.decodeResource(getResources(),
                R.mipmap.show);
        mMatrix = new Matrix();
        float[] src = {0, 0,//
                mBitmap.getWidth(), 0,//
                mBitmap.getWidth(), mBitmap.getHeight(),//
                0, mBitmap.getHeight()};
        float[] dst = {0, 0,//
                mBitmap.getWidth()/2, 100,//
                mBitmap.getWidth()/2, mBitmap.getHeight() - 100,//
                0, mBitmap.getHeight()};
        mMatrix.setPolyToPoly(src, 0, dst, 0, src.length >> 1);




        mShadowPaint = new Paint();
        mShadowPaint.setStyle(Paint.Style.FILL);
        mShadowGradientShader = new LinearGradient(0, 0, 0.5f, 0,
                Color.BLACK, Color.TRANSPARENT, Shader.TileMode.CLAMP);
        mShadowPaint.setShader(mShadowGradientShader);

        mShadowGradientMatrix = new Matrix();
        mShadowGradientMatrix.setScale(mBitmap.getWidth(), 1);
        mShadowGradientShader.setLocalMatrix(mShadowGradientMatrix);
        mShadowPaint.setAlpha((int) (0.9*255));

    }


    private int mViewWidth, mViewHeight;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;

    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawBitmap(mBitmap, mMatrix, mShadowPaint);

        canvas.restore();
    }


}
