package com.menu;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/11/10.
 */

public class MyMenuAdapter extends BaseMenuAdapter {

    private Context context;
    private String[] strings = {"Tab1", "Tab2", "Tab3", "Tab4"};

    public MyMenuAdapter(Context context) {
        this.context = context;
    }

    public MyMenuAdapter(Context context, String[] strings) {
        this.strings = strings;
        this.context = context;
    }

    @Override
    public int getCount() {


        return strings.length;
    }

    @Override
    public View getTabView(int position, ViewGroup parent) {
        TextView textView = new TextView(context);
        textView.setText(strings[position]);

        return textView;
    }

    @Override
    public View getMenuView(int position, ViewGroup parent) {
        TextView textView = new TextView(context);
        textView.setText(strings[position]);


        return textView;
    }
}
