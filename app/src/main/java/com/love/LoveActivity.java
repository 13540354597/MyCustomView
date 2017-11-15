package com.love;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.mycustomview.R;

/**
 * Created by TR 105 on 2017/11/15.
 */

public class LoveActivity extends AppCompatActivity {
    private Button button;

    private LoveLayout ll_love;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.love_activity);
        ll_love=findViewById(R.id.ll_love);

        button=findViewById(R.id.btn_add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_love.addLove();
            }
        });

    }
}
