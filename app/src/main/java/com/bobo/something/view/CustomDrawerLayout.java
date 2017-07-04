package com.bobo.something.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.View;

import com.bobo.something.R;


/**
 * @author huangbo
 */

public class CustomDrawerLayout extends DrawerLayout implements DrawerLayout.DrawerListener {

    public CustomDrawerLayout(Context context) {
        super(context);
    }

    public CustomDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        setScrimColor(Color.parseColor("#1A000000"));
        setDrawerShadow(R.color.transparent, GravityCompat.END);
        setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        addDrawerListener(this);
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {
        setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    public boolean isOpen() {
        return getChildCount() == 0 ? false : isDrawerOpen(getChildAt(getChildCount() - 1));
    }

    public void open() {
        if (getChildCount() != 0)
            openDrawer(getChildAt(getChildCount() - 1));
    }
}
