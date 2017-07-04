package com.bobo.something;

import android.os.Bundle;
import android.widget.Button;

import com.bobo.something.annotation.LayoutResUtil.LayoutId;
import com.bobo.something.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author huangbo
 */
@LayoutId(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @BindView(R.id.btn_choose_address)
    Button btnChooseAddress;

    @Override
    protected void init(Bundle savedInstanceState) {
        addNavTitle("首页");

    }

    @OnClick(R.id.btn_choose_address)
    public void onViewClicked() {
    }
}
