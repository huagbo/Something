package com.bobo.something;

import android.app.Application;

import com.bobo.something.utils.AppUtil;

/**
 * Created by huangbo on 2017/6/20.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppUtil.ApplicationContext = this;
    }
}
