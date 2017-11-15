package com.love;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;

import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mycustomview.R;

import java.util.Random;

public class LoveLayout extends RelativeLayout {
    private Drawable mRed, mYellow, mBlue;
    private Drawable[] mDrawables;
    private Interpolator[] mInterpolators;
    private int mDrawableHeight, mDrawableWidth;
    private int mWidth, mHeight;
    private LayoutParams params;
    private Random mRandom = new Random();

    public LoveLayout(Context context) {
        this(context, null);
    }

    public LoveLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoveLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        initDrawable();
        initInterpolator();
        // 初始化params
        params = new LayoutParams(mDrawableWidth, mDrawableHeight);
        // 父容器水平居中
        params.addRule(CENTER_HORIZONTAL, TRUE);
        // 父容器的底部
        params.addRule(ALIGN_PARENT_BOTTOM, TRUE);
    }

    /**
     * 初始化几种插补器
     */
    private void initInterpolator() {
        mInterpolators = new Interpolator[4];
        mInterpolators[0] = new LinearInterpolator();// 线性
        mInterpolators[1] = new AccelerateDecelerateInterpolator();// 先加速后减速
        mInterpolators[2] = new AccelerateInterpolator();// 加速
        mInterpolators[3] = new DecelerateInterpolator();// 减速
    }

    private void initDrawable() {
        mRed = getResources().getDrawable(R.drawable.ic_launcher_background);
        mYellow = getResources().getDrawable(R.drawable.ic_launcher_background);
        mBlue = getResources().getDrawable(R.drawable.ic_launcher_background);

        mDrawables = new Drawable[3];
        mDrawables[0] = mRed;
        mDrawables[1] = mYellow;
        mDrawables[2] = mBlue;

        // 得到图片的实际宽高
        mDrawableWidth = mRed.getIntrinsicWidth();
        mDrawableHeight = mRed.getIntrinsicHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }


    /**
     * 添加点赞图片
     **/
    public void addLove() {
        final ImageView loveIv = new ImageView(getContext());
        loveIv.setImageDrawable(mDrawables[mRandom.nextInt(mDrawables.length)]);
        loveIv.setLayoutParams(params);
        addView(loveIv);

        // 最终的属性动画集合
        AnimatorSet finalSet = getAnimatorSet(loveIv);
        finalSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                getBezierValueAnimator(loveIv).start();
            }
        });
        finalSet.start();


    }

    /**
     * 构造三个属性动画
     */
    private AnimatorSet getAnimatorSet(ImageView loveIv) {
        // 1.alpha动画
        ObjectAnimator alpha = ObjectAnimator
                .ofFloat(loveIv, "alpha", 0.3f, 1f);

        // 2.缩放动画
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(loveIv, "scaleX", 0.2f,
                1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(loveIv, "scaleY", 0.2f,
                1f);
        // 刚刚进入动画集合
        AnimatorSet enter = new AnimatorSet();
        enter.setDuration(500);
        enter.playTogether(alpha, scaleX, scaleY);
        enter.setTarget(loveIv);
        return enter;
    }


    /**
     * 贝塞尔曲线动画(核心，不断的修改ImageView的坐标ponintF(x,y) )
     */
    private ValueAnimator getBezierValueAnimator(final ImageView loveIv) {
        // 曲线的两个顶点
        PointF pointF2 = getPonitF(1);
        PointF pointF1 = getPonitF(2);
        // 起点位置
        PointF pointF0 = new PointF((mWidth - mDrawableWidth) / 2, mHeight
                - mDrawableHeight);
        // 结束的位置
        PointF pointF3 = new PointF(mRandom.nextInt(mWidth), 0);
        // 估值器Evaluator,来控制view的行驶路径（不断的修改point.x,point.y）
        BezierEvaluator evaluator = new BezierEvaluator(pointF1, pointF2);
        // 属性动画不仅仅改变View的属性，还可以改变自定义的属性
        ValueAnimator animator = ValueAnimator.ofObject(evaluator, pointF0,
                pointF3);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 不断改变ImageView的x,y的值
                PointF pointF = (PointF) animation.getAnimatedValue();
                loveIv.setX(pointF.x);
                loveIv.setY(pointF.y);
                loveIv.setAlpha(1 - animation.getAnimatedFraction() + 0.1f);// 得到百分比
            }
        });
        animator.setTarget(loveIv);
        animator.setInterpolator(mInterpolators[getRandom(0,3)]);
        animator.setDuration(3000);
        return animator;
    }

    private PointF getPonitF(int i) {
        PointF pointF = new PointF();


        switch (i) {
            case 1: {


                int x = getRandom(0, mWidth);
                int y = getRandom(mHeight / 2, mHeight);
                pointF.set(x, y);


                break;
            }
            case 2: {
                int x = getRandom(0, mWidth);
                int y = getRandom(0, mHeight / 2);
                pointF.set(x, y);

                break;
            }
        }


        return pointF;
    }

    public static int getRandom(int min, int max) {
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;

    }
}

