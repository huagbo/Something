package com.bobo.something.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bobo.something.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.inflate;

/**
 * Created by huangbo on 2017/6/20.
 */

public abstract class BaseActivity extends FragmentActivity {
    @BindView(R.id.title_group)
    RelativeLayout titleGroup;
    @BindView(R.id.loadingView)
    RelativeLayout loadingGroup;
    @BindView(R.id.contentView)
    FrameLayout contentView;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.title_name)
    TextView titleName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        if (layoutResourceId() != 0) {
            inflate(this, layoutResourceId(), contentView);
        }
        ButterKnife.bind(this);
        init(savedInstanceState);

    }

    protected abstract int layoutResourceId();

    protected void init(Bundle savedInstanceState) {

    }

    protected void showLoading(boolean cancelAble) {
        loadingGroup.setVisibility(View.VISIBLE);
        if (!cancelAble) {
            return;
        }
        loadingGroup.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_BACK:
                        hideLoading();
                        break;
                }
                return false;
            }
        });
    }

    public interface OnHideLoadingListener {
        void stopHttpRequest();
    }

    OnHideLoadingListener onHideLoadingListener;

    protected void hideLoading() {
        loadingGroup.setVisibility(View.GONE);
        if (onHideLoadingListener != null) {
            onHideLoadingListener.stopHttpRequest();
        }
    }

    protected void addNavTitle(String name) {
        addNavTitle(name, null);
    }

    protected void addNavTitle(String name, OnClickListener backListener) {
        titleName.setText(name);
        if (backListener != null) {
            ivBack.setOnClickListener(backListener);
        } else {
            ivBack.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    protected void setCustomTitle(View view) {
        titleGroup.removeAllViews();
        titleGroup.addView(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    String fragName = null;

    protected void loadFragment(Fragment fg) {
        fragName = fg.getClass().getSimpleName();
        this.getSupportFragmentManager().beginTransaction().replace(R.id.contentView, fg, fragName).commit();
        contentView.requestLayout();
    }

    protected Fragment getFragment() {
        return this.getSupportFragmentManager().findFragmentByTag(fragName);
    }
}
