package com.mycustomview.sample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.mycustomview.R;

/**
 * Created by TR 105 on 2017/9/20.
 */

public class View8 extends View {

    private Paint mDeafultPaint;

    public View8(Context context) {
        super(context);
    }

    public View8(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mViewWidth=w;
        mViewHeight=h;

    }
private int mViewWidth,mViewHeight;
    @Override
    protected void onDraw(Canvas canvas) {
        mDeafultPaint = new Paint();
        mDeafultPaint.setColor(Color.BLUE);
        mDeafultPaint.setStyle(Paint.Style.STROKE);                   // 设置画布模式为填充

//        canvas.translate(mViewWidth / 2, mViewHeight / 2);          // 移动画布(坐标系)
//
//        Path path = new Path();                                     // 创建Path
//
//     //path.setFillType(Path.FillType.EVEN_ODD);                   // 设置Path填充模式为 奇偶规则
//        path.setFillType(Path.FillType.INVERSE_EVEN_ODD);            // 反奇偶规则
//
//        path.addRect(-200, -200, 200, 200, Path.Direction.CW);
//        //path.addCircle(-200,-200,200,Path.Direction.CW);// 给Path中添加一个矩形
//        canvas.drawPath(path,mDeafultPaint);



        canvas.translate(mViewWidth / 2, mViewHeight / 2);          // 平移坐标系

        Path path = new Path();                                     // 创建Path并添加了一个矩形
        path.addRect(-200, -200, 200, 200, Path.Direction.CW);

        Path dst = new Path();                                      // 创建用于存储截取后内容的 Path
        dst.rLineTo(0, 0);
        PathMeasure measure = new PathMeasure(path, false);         // 将 Path 与 PathMeasure 关联

// 截取一部分存入dst中，并使用 moveTo 保持截取得到的 Path 第一个点的位置不变
        measure.getSegment(0, 200, dst, true);

        canvas.drawPath(dst, mDeafultPaint);
    }
}
