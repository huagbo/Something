package com.bobo.something;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;

import com.bobo.something.base.BaseActivity;
import com.bobo.something.view.Flow;


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

        ViewGroup.LayoutParams layoutParams= new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        Flow flowLo= new Flow(this);
        flowLo.addView(new Button(this),layoutParams);
        flowLo.addView(new Button(this),layoutParams);
        flowLo.addView(new Button(this),layoutParams);
        flowLo.addView(new Button(this),layoutParams);
        flowLo.addView(new Button(this),layoutParams);

        contentView.addView(flowLo);





    }
}
