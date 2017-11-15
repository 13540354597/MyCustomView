package com.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.ViewAnimator;

import com.mycustomview.R;

import java.util.zip.Inflater;

/**
 * Created by Administrator on 2017/11/10.
 */

public class LoadingView extends LinearLayout {

    private int mTranslationDistance = 0;

    public LoadingView(Context context) {
        this(context, null);


    }

    private boolean ANIMATION = false;
    private View view;

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTranslationDistance = dip2px(50);
        view=inflate(getContext(), R.layout.loading_view, this);
        loading = findViewById(R.id.iv_image);
        vLine = findViewById(R.id.v_line);

    }

    View loading;
    View vLine;



    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }

    private AnimatorSet animSetDown;

    private void downAnimation() {

        if (ANIMATION) {

            loading.clearAnimation();
            vLine.clearAnimation();
            return;
        }
        Log.e("============", "downAnimation");

        ObjectAnimator moveIn = ObjectAnimator.ofFloat(loading, "translationY", mTranslationDistance, 0);
        moveIn.setInterpolator(new AccelerateInterpolator());


        ObjectAnimator rotate = ObjectAnimator.ofFloat(vLine, "scaleX", 0.2f, 1f);
        ObjectAnimator fadeInOut = ObjectAnimator.ofFloat(loading, "rotation", 0, 180);


        animSetDown = new AnimatorSet();
        animSetDown.play(rotate).with(fadeInOut).with(moveIn);
        animSetDown.setDuration(500);


        animSetDown.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                //animSet.start();
                upAnimation();
            }
        });

        animSetDown.start();
    }

    private AnimatorSet animSetUp;

    private void upAnimation() {

        if (ANIMATION) {

            loading.clearAnimation();
            vLine.clearAnimation();
            return;
        }

        Log.e("============", "upAnimation");


        ObjectAnimator moveIn = ObjectAnimator.ofFloat(loading, "translationY", 0, mTranslationDistance);
        moveIn.setInterpolator(new AccelerateInterpolator());


        ObjectAnimator rotate = ObjectAnimator.ofFloat(vLine, "scaleX", 1f, 0.2f);
        ObjectAnimator fadeInOut = ObjectAnimator.ofFloat(loading, "rotation", 180, 360);


        animSetUp = new AnimatorSet();
        animSetUp.play(rotate).with(fadeInOut).with(moveIn);
        animSetUp.setDuration(500);


        animSetUp.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                //animSet.start();
                downAnimation();
            }
        });

        animSetUp.start();


    }

    public void stopLoadingView() {

        ANIMATION = true;
        view.setVisibility(GONE);
//        ViewGroup parent = (ViewGroup) getParent();
//        if (parent != null) {
//            parent.removeView(this);// 从父布局移除
//            removeAllViews();// 移除自己所有的View
//        }
    }

    public void startLoadingView() {

        ANIMATION = false;

        view.setVisibility(VISIBLE
        );
        downAnimation();
    }


}
