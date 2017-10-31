package com.sliding;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

/**
 * Created by TR 105 on 2017/10/29.
 */

public class SlidingView extends HorizontalScrollView {

    private Context context;

    private int marginWidth = 50;

    private int mMenuWidth = 0;

    private int contentWidth = 0;


    public SlidingView(Context context) {
        this(context, null);
    }

    public SlidingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        mMenuWidth = ScreenUtils.getScreenWidth(context) - marginWidth;
        contentWidth = ScreenUtils.getScreenWidth(context);
    }

    //XML解析完成后设置布局
    ViewGroup menuView;
    ViewGroup contentView;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();


        ViewGroup viewGroup = (ViewGroup) getChildAt(0);

        if (viewGroup.getChildCount() != 2) {
            throw new RuntimeException("请注意SlidingView的子View的数量");
        }
        /**
         * 菜单
         */
        menuView = (ViewGroup) viewGroup.getChildAt(0);
        ViewGroup.LayoutParams menuParams = menuView.getLayoutParams();
        menuParams.width = mMenuWidth;
        menuView.setLayoutParams(menuParams);

        /**
         * Content
         */
        contentView = (ViewGroup) viewGroup.getChildAt(1);
        ViewGroup.LayoutParams contentParams = contentView.getLayoutParams();
        contentParams.width = contentWidth;
        contentView.setLayoutParams(contentParams);


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        // 2. 初始化进来是关闭
        scrollTo(mMenuWidth, 0);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP: {

                int scrollWidth = getScrollX();

                if (scrollWidth > mMenuWidth / 2) {
                    //关闭

                    smoothScrollTo(mMenuWidth, 0);
                } else {
                    //打开
                    smoothScrollTo(0, 0);
                }


                return true;
            }

        }


        return super.onTouchEvent(ev);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        Log.e("l", "" + l);


        float cs = 1f * l / mMenuWidth;

        float contentScale = (float) (0.7f + 0.3 * cs);//1-0

        ViewCompat.setPivotX(contentView, 0);

        ViewCompat.setPivotY(contentView, contentView.getHeight() / 2);
        ViewCompat.setScaleX(contentView, contentScale);
        ViewCompat.setScaleY(contentView, contentScale);


        float alpha = (float) (0.5 + 0.5 * (1 - cs));


        ViewCompat.setAlpha(menuView, alpha);
        float leftScale = 0.7f + (1-cs)*0.3f;
        ViewCompat.setScaleX(menuView,leftScale);
        ViewCompat.setScaleY(menuView, leftScale);

        ViewCompat.setTranslationX(menuView, 0.22f*l);

    }
}
