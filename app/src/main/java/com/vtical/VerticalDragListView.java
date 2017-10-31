package com.vtical;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;

/**
 * Created by TR 105 on 2017/10/30.
 */

public class VerticalDragListView extends FrameLayout {


    private ViewDragHelper viewDragHelper;
    private int gridViewHeight;
    private View listView;

    public VerticalDragListView(@NonNull Context context) {
        this(context, null);
    }

    public VerticalDragListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalDragListView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        viewDragHelper = ViewDragHelper.create(this, callback);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();


        if (getChildCount() != 2) {
            throw new RuntimeException("子View的数量出错");
        }

        listView = getChildAt(1);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (changed) {
            gridViewHeight = getChildAt(0).getMeasuredHeight();
        }

    }

    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {


        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return listView == child;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {

            if (top > gridViewHeight) {
                return gridViewHeight;
            } else if (top < 0) {
                return 0;
            }


            return top;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            //松开手指判断是打开还是关闭
            if (releasedChild == listView) {
                if (listView.getTop() > gridViewHeight / 2) {
                    viewDragHelper.settleCapturedViewAt(0, gridViewHeight);

                    mMenuIsOpen = true;
                } else if (listView.getTop() < gridViewHeight / 2) {
                    viewDragHelper.settleCapturedViewAt(0, 0);
                    mMenuIsOpen = false;
                }
                invalidate();
            }
            // Log.e("======", "onViewReleased");
        }
    };

    /**
     * 响应滚动
     */
    @Override
    public void computeScroll() {
        //滚动完成之后相应onViewReleased

        //如果不添加这个方法那么松开手之后就不会回弹
        if (viewDragHelper.continueSettling(true)) {
            invalidate();
        }

        //Log.e("======", "computeScroll");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }


    float downY = 0.0f;

    private Boolean mMenuIsOpen = false;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {


        if (mMenuIsOpen) {
            return true;
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = ev.getY();
                // 让 DragHelper 拿一个完整的事件
                viewDragHelper.processTouchEvent(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = ev.getY();

                 Log.e("======", ""+canChildScrollUp());
                if ((moveY - downY) > 0 && !canChildScrollUp()) {
                    // 向下滑动 && 滚动到了顶部，拦截不让ListView做处理
                    return true;
                }
                break;
        }
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN: {
//                downY = ev.getX();
//
//                //不添加这句无法拿到DOWN事件
//                viewDragHelper.processTouchEvent(ev);
//
//                Log.e("======", "ACTION_DOWN");
//                break;
//
//            }
//
//            case MotionEvent.ACTION_MOVE: {
//
//                Log.e("======", "ACTION_MOVE");
//                if ((ev.getX() - downY) > 0&&!canChildScrollUp()) {
//                    //像下拉
//
//                    return true;
//                } else if ((ev.getX() - downY) < 0) {
//                    //像上拉
//
//                    //return true;
//
//                }
//
//
//                break;
//            }
//        }


        return super.onInterceptTouchEvent(ev);
    }

    /**
     * @return Whether it is possible for the child view of this layout to
     * scroll up. Override this if the child view is a custom view.
     * 判断View是否滚动到了最顶部,还能不能向上滚
     */
    public boolean canChildScrollUp() {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (listView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) listView;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(listView, -1) || listView.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(listView, -1);
        }
    }
}
