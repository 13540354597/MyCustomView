package com.mycustomview.zen;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.LoggingMXBean;

/**
 * Created by TR 105 on 2017/10/27.
 */

public class TagView extends ViewGroup {


    public TagView(Context context) {
        super(context);
    }

    public TagView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //宽度直接去获取父布局的
        int width = MeasureSpec.getSize(widthMeasureSpec);
        //高度通过子View来叠加
        int height = getPaddingTop() + getPaddingBottom();
        //获取当前一行中的最大高度
        int maxLineHeight = 0;
        int linWidth = getPaddingLeft() + getPaddingRight();

        /**
         * 控件的位置
         */

        int top = getPaddingTop();


        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            //测量子控件
            measureChild(child, widthMeasureSpec, heightMeasureSpec);

            ViewGroup.MarginLayoutParams childParams = (MarginLayoutParams) child.getLayoutParams();
            //当前自控的宽度
            int nowChildWidth = child.getMeasuredWidth() + childParams.leftMargin + childParams.rightMargin;
            //当前空间的高度
            int nowChildHeight = child.getMeasuredHeight() + childParams.topMargin + childParams.bottomMargin;

            if (linWidth + nowChildWidth > width) {
                //换行
                //让高度加上，上一行的高度
                height += maxLineHeight;
                maxLineHeight = nowChildHeight;
                linWidth = getPaddingLeft() + getPaddingRight();

                //设置控件的位置
                top = height - getPaddingBottom();
                int nowTop = top + childParams.topMargin;
                int nowBottom = nowTop + child.getMeasuredHeight();
                int nowLeft = linWidth + childParams.leftMargin - getPaddingRight();
                int nowRight = nowLeft + child.getMeasuredWidth();


                child.setTag(new Location(nowLeft, nowTop, nowRight, nowBottom));
                linWidth = nowChildWidth + getPaddingLeft() + getPaddingRight();

            } else {

                //设置控件的位置
                int nowTop = top + childParams.topMargin;
                int nowBottom = nowTop + child.getMeasuredHeight();
                int nowLeft = linWidth + childParams.leftMargin - getPaddingRight();
                int nowRight = nowLeft + child.getMeasuredWidth();
                child.setTag(new Location(nowLeft, nowTop, nowRight, nowBottom));


                linWidth += nowChildWidth;
                maxLineHeight = Math.max(nowChildHeight, maxLineHeight);


            }


        }


        height += maxLineHeight;
        setMeasuredDimension(width, height);


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE)
                continue;
            Location location = (Location) child.getTag();
            child.layout(location.left, location.top, location.right, location.bottom);
        }
    }


    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return super.generateLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }


    public class Location {

        private int left;
        private int top;
        private int right;
        private int bottom;

        public Location(int left, int top, int right, int bottom) {
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
        }

        public int getLeft() {
            return left;
        }

        public void setLeft(int left) {
            this.left = left;
        }

        public int getTop() {
            return top;
        }

        public void setTop(int top) {
            this.top = top;
        }

        public int getRight() {
            return right;
        }

        public void setRight(int right) {
            this.right = right;
        }

        public int getBottom() {
            return bottom;
        }

        public void setBottom(int bottom) {
            this.bottom = bottom;
        }
    }
}
