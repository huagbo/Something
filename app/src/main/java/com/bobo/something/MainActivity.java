package com.bobo.something;

import android.content.Context;
import android.content.Intent;
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


    public static void show(Context context){
        Intent it= new Intent(context,MainActivity.class);
        context.startActivity(it);
    }

    @BindView(R.id.btn_choose_address)
    Button btnChooseAddress;

    @Override
    protected void init(Bundle savedInstanceState) {
        addNavTitle("首页");

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @OnClick(R.id.btn_choose_address)
    public void onViewClicked() {
    }
}
