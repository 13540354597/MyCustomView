package com.menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mycustomview.R;

/**
 * Created by Administrator on 2017/11/10.
 */

public class MenuActivity extends AppCompatActivity {
    private MenuView menuView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.menu_activity);
        menuView = findViewById(R.id.mv_menu);
        menuView.setAdapter(new MyMenuAdapter(this));

    }
}
