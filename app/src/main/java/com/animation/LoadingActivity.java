package com.animation;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.mycustomview.R;

/**
 * Created by Administrator on 2017/11/10.
 */

public class LoadingActivity extends AppCompatActivity {

    private Button stop;
    private boolean SHOW = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_activity);

        final LoadingView loadingView = findViewById(R.id.loading);

        stop = findViewById(R.id.stop);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SHOW) {
                    loadingView.startLoadingView();
                    SHOW = false;
                } else {
                    loadingView.stopLoadingView();
                    SHOW = true;
                }

            }
        });

    }
}
