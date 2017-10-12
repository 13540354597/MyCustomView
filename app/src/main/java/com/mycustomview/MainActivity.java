package com.mycustomview;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.mycustomview.sample.HeartView;
import com.mycustomview.sample.MagicCircle;
import com.mycustomview.sample.MatrixSetPolyToPolyTest;
import com.mycustomview.sample.MyViewPager;
import com.mycustomview.sample.RadarView;
import com.mycustomview.sample.Rotate3dAnimation;
import com.mycustomview.sample.SearchView;
import com.mycustomview.sample.SetPolyToPoly;
import com.mycustomview.sample.TaiJi;
import com.mycustomview.sample.View10;
import com.mycustomview.sample.View2;
import com.mycustomview.sample.View3;
import com.mycustomview.sample.View4;
import com.mycustomview.sample.View5;
import com.mycustomview.sample.View6;
import com.mycustomview.sample.View7;
import com.mycustomview.sample.View8;
import com.mycustomview.sample.View9;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initMagicCircle();

        //initTaiJi();
        //setContentView(new S(this));
        //initView7();


        // searchView.initAll();

        //SearchView();


        //initSetPoly();
        //init3DView();

        setContentView(new MyViewPager(this));
    }

    private void init3DView() {
        setContentView(R.layout.activity_main);
        ImageView view = (ImageView) findViewById(R.id.img);
        assert view != null;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 计算中心点（这里是使用view的中心作为旋转的中心点）
                final float centerX = v.getWidth() / 2.0f;
                final float centerY = v.getHeight() / 2.0f;

                //括号内参数分别为（上下文，开始角度，结束角度，x轴中心点，y轴中心点，深度，是否扭曲）
                final Rotate3dAnimation rotation = new Rotate3dAnimation(MainActivity.this, 0, 180, centerX, centerY, 0f, true);

                rotation.setDuration(3000);                         //设置动画时长
                rotation.setFillAfter(true);                        //保持旋转后效果
                rotation.setInterpolator(new LinearInterpolator());    //设置插值器
                v.startAnimation(rotation);
            }
        });
    }

    private void initSetPoly() {
        setContentView(R.layout.set_poly);

        final SetPolyToPoly poly = (SetPolyToPoly) findViewById(R.id.poly);

        RadioGroup group = (RadioGroup) findViewById(R.id.group);
        assert group != null;
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.point0:
                        poly.setTestPoint(0);
                        break;
                    case R.id.point1:
                        poly.setTestPoint(1);
                        break;
                    case R.id.point2:
                        poly.setTestPoint(2);
                        break;
                    case R.id.point3:
                        poly.setTestPoint(3);
                        break;
                    case R.id.point4:
                        poly.setTestPoint(4);
                        break;
                }
            }
        });
    }

    private void SearchView() {
//        setContentView(R.layout.activity_main);
//        SearchView searchView = findViewById(R.id.view7);
//        searchView.startAnimation();


    }

    private void initMagicCircle() {
        MagicCircle magicCircle = new MagicCircle(this);
        setContentView(magicCircle);
        magicCircle.startAnimation();
    }

    private void initTaiJi() {
        final TaiJi taiJi = new TaiJi(this);
        setContentView(taiJi);
        final Handler handler = new Handler() {
            private float degrees = 0;

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                taiJi.setRotate(degrees += 5);
                this.sendEmptyMessageDelayed(0, 80);
            }
        };

        handler.sendEmptyMessageDelayed(0, 20);
    }
}
