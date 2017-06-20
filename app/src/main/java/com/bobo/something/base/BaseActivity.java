package com.bobo.something.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

/**
 * Created by huangbo on 2017/6/20.
 */

public abstract class BaseActivity extends FragmentActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

    }

    protected abstract void init(Bundle savedInstanceState);

    protected void loadFragment(BaseFragment fg) {

    }
}
