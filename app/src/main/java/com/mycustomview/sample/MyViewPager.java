package com.mycustomview.sample;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Scroller;

import com.mycustomview.R;

public class MyViewPager extends ViewGroup {

    private int[] image_id = {R.drawable.device_gateway, R.drawable.ic_launcher_background, R.drawable.device_gateway, R.drawable.ic_launcher_background};

    private GestureDetector mDetector;
    private Scroller mScroller;

    public MyViewPager(Context context) {
        super(context);
        initView();
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        for (int i = 0; i < image_id.length; i++) {
            ImageView iv = new ImageView(getContext());
            iv.setBackgroundResource(image_id[i]);
            this.addView(iv);
        }
        mDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                //scrollBy：相对滑动，相对我们当前的控件多少距离，就滑动多少距离  
                //distanceX是我们手滑动的距离，即我们的手相对控件滑动了多少，所以X轴滑动这个距离，Y轴滑动0  
                scrollBy((int) distanceX, 0);



//                if ( e2.getAction() == MotionEvent.ACTION_UP) {
//
//                }




                return super.onScroll(e1, e2, distanceX, distanceY);

            }


        });
        mScroller = new Scroller(getContext());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < image_id.length; i++) {
            this.getChildAt(i).layout(i * getWidth(), t, (i + 1) * getWidth(), b);
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);
        //触摸事件处理  
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:


                break;
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                //你滑动的距离加上屏幕的一半，除以屏幕宽度，如果你滑动距离超过了屏幕的一半，这个pos就加1
                int pos = (scrollX + getWidth() / 2) / getWidth();
                //滑到最后一张的时候，不能出边界
                if (pos >= image_id.length) {
                    pos = image_id.length - 1;
                    //pos=0;

                }
                //绝对滑动，直接滑到指定的x值
                //scrollTo(pos * getWidth(), 0);
                //自然滑动,从手滑到的地方开始，滑动距离是页面宽度减去滑到的距离，时间由路程的大小来决定
                mScroller.startScroll(scrollX, 0, pos * getWidth() - scrollX, 0, Math.abs(pos * getWidth()));

                Log.e("=======", Math.abs(pos * getWidth()) + "");
                invalidate();
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), 0);
            postInvalidate();
        }
    }
}  