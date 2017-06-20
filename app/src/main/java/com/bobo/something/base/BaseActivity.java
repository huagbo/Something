package com.bobo.something.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.ViewGroup;

import com.bobo.something.R;

/**
 * Created by huangbo on 2017/6/20.
 */

public abstract class BaseActivity extends FragmentActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        init(savedInstanceState);

    }
    protected ViewGroup titleGroup;
    protected ViewGroup rootGroup;
    protected int layoutResouceId;

    protected abstract void init(Bundle savedInstanceState);

    protected void loadFragment(BaseFragment fg) {

    }
}
