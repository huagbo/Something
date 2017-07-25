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
    ViewHolder holder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(this, R.layout.activity_base, null);
        holder = new ViewHolder(view);
        setContentView(view);
        int layoutResId = layoutId();
        if (layoutResId != 0) {
            View contentChild = inflate(this, layoutResId, null);
            holder.contentView.addView(contentChild);
        }
        ButterKnife.bind(this);
        init(savedInstanceState);

    }

    protected abstract int layoutId();


    protected void init(Bundle savedInstanceState) {

    }

    protected void showLoading(boolean cancelAble) {
        holder.loadingView.setVisibility(View.VISIBLE);
        if (!cancelAble) {
            return;
        }
        holder.loadingView.setOnKeyListener(new OnKeyListener() {
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
        holder.loadingView.setVisibility(View.GONE);
        if (onHideLoadingListener != null) {
            onHideLoadingListener.stopHttpRequest();
        }
    }

    protected void addNavTitle(String name) {
        addNavTitle(name, null);
    }

    protected void addNavTitle(String name, boolean hideBack) {
        addNavTitle(name, false, null);
    }
    protected void addNavTitle(String name, OnClickListener backListener) {
        addNavTitle(name, false, backListener);
    }

    protected void addNavTitle(String name, boolean hideBack, OnClickListener backListener) {
        holder.titleName.setText(name);
        holder.ivBack.setVisibility(hideBack ? View.GONE : View.VISIBLE);
        if (backListener != null) {
            holder.ivBack.setOnClickListener(backListener);
        } else {
            holder.ivBack.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    protected void setCustomTitle(View view) {
        holder.titleGroup.removeAllViews();
        holder.titleGroup.addView(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    String fragName = null;

    protected void loadFragment(Fragment fg) {
        fragName = fg.getClass().getSimpleName();
        this.getSupportFragmentManager().beginTransaction().replace(R.id.contentView, fg, fragName).commit();
        holder.contentView.requestLayout();
    }

    protected Fragment getFragment() {
        return this.getSupportFragmentManager().findFragmentByTag(fragName);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    static class ViewHolder {
        @BindView(R.id.iv_back)
        ImageView ivBack;
        @BindView(R.id.title_name)
        TextView titleName;
        @BindView(R.id.title_group)
        RelativeLayout titleGroup;
        @BindView(R.id.contentView)
        FrameLayout contentView;
        @BindView(R.id.loadingView)
        RelativeLayout loadingView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
