package com.menu;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/11/10.
 */

public abstract class BaseMenuAdapter {


    public abstract int getCount();

    /**
     * @param position 控价id
     * @param parent   父布局
     * @return
     */
    public abstract View getTabView(int position, ViewGroup parent);

    /**
     * @param position 控价id
     * @param parent   父布局
     * @return
     */
    public abstract View getMenuView(int position, ViewGroup parent);






}
