package com.menu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/11/10.
 */

public class MenuView extends LinearLayout {
    private int tabClick;
    private int mMenuContainerHeight;

    public MenuView(Context context) {
        this(context, null);
    }

    public MenuView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MenuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initLayout();

    }

    LinearLayout tab;
    FrameLayout menu;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("TAG", "onMeasure");
        int height = MeasureSpec.getSize(heightMeasureSpec);
        // if (mMenuContainerHeight == 0 && height > 0) {
        // 内容的高度应该不是全部  应该是整个 View的 75%
        mMenuContainerHeight = (int) (height * 75f / 100);
        ViewGroup.LayoutParams params = menu.getLayoutParams();
        params.height = mMenuContainerHeight;
        menu.setLayoutParams(params);
        // 进来的时候阴影不显示 ，内容也是不显示的（把它移上去）
        menu.setTranslationY(-mMenuContainerHeight);

        // }
    }

    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }

    private void initLayout() {
        setOrientation(VERTICAL);
        /**
         * 初始化Tab
         */
        tab = new LinearLayout(getContext());
        LinearLayout.LayoutParams tabParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 45);
        tab.setLayoutParams(tabParams);
        tab.setBackgroundColor(Color.GREEN);
        tab.setOrientation(HORIZONTAL);
        addView(tab);
        /**
         * 初始化菜单
         */
        menu = new FrameLayout(getContext());
        LinearLayout.LayoutParams menuParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // menuParams.weight = 9;
        menu.setLayoutParams(menuParams);
        menu.setBackgroundColor(Color.GRAY);
        addView(menu);
    }

    public void setAdapter(BaseMenuAdapter myMenuAdapter) {

        int count = myMenuAdapter.getCount();

        for (int i = 0; i < count; i++) {
            /**
             * 添加Tab
             */
            TextView view = (TextView) myMenuAdapter.getTabView(i, tab);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            params.weight = 1;
            view.setLayoutParams(params);
            view.setBackgroundColor(Color.WHITE);
            view.setGravity(Gravity.CENTER);


            tab.addView(view);

            /**
             * 添加Menu
             */
            TextView view2 = (TextView) myMenuAdapter.getMenuView(i, menu);
            LayoutParams params2 = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            view2.setLayoutParams(params2);
            view2.setBackgroundColor(Color.WHITE);
            view2.setGravity(Gravity.CENTER);
            //  view2.setVisibility(GONE);
            menu.addView(view2);


            setTabClick(view, view2, myMenuAdapter);


        }
        menu.setVisibility(GONE);
    }

    private boolean SHOW = false;

    public void setTabClick(TextView tab, final TextView menu, final BaseMenuAdapter myMenuAdapter) {

        tab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //  initMenu(myMenuAdapter);
                if (!SHOW) {
                    // menu.setVisibility(VISIBLE);
                    openMenu();
                    SHOW = true;
                } else {
                    closeMenu();
                    //menu.setVisibility(GONE);

                    SHOW = false;
                }


            }
        });


    }

//    private void initMenu(BaseMenuAdapter myMenuAdapter) {
//        int count = myMenuAdapter.getCount();
//
//        for (int i = 0; i < count; i++) {
//            /**
//             * 添加Tab
//             */
//            TextView view = (TextView) myMenuAdapter.getMenuView(i, menu);
//            view.setVisibility(GONE);
//        }
//
//    }


    private void openMenu() {
        menu.setVisibility(VISIBLE);
        ObjectAnimator animator = ObjectAnimator.ofFloat(menu, "translationY", -mMenuContainerHeight, 0);

        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(menu, "alpha", 0f, 1f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(300);
        animatorSet.play(animator).with(animatorAlpha);
        animatorSet.start();

    }

    private void closeMenu() {

        ObjectAnimator animator = ObjectAnimator.ofFloat(menu, "translationY", 0 - mMenuContainerHeight);

        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(menu, "alpha", 1f, 0f);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                menu.setVisibility(GONE);

            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(300);
        animatorSet.play(animator).with(animatorAlpha);
        animatorSet.start();
    }

}
