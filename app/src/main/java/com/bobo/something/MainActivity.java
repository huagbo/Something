package com.bobo.something;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.bobo.something.base.BaseActivity;

import butterknife.BindView;


/**
 * @author huangbo
 */
public class MainActivity extends BaseActivity {


    @BindView(R.id.btn_choose_address)
    Button btnChooseAddress;

    public static void show(Context context) {
        Intent it = new Intent(context, MainActivity.class);
        context.startActivity(it);
    }


    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        addNavTitle("首页");
    }



}
