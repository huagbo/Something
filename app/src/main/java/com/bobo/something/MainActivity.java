package com.bobo.something;

import android.os.Bundle;

import com.bobo.something.base.BaseActivity;


/**
 *
 * @author huangbo
 */

public class MainActivity extends BaseActivity {

    @Override
    protected int layoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        addNavTitle("首页");

    }
}
