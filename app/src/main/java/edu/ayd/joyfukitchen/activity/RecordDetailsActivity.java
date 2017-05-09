package edu.ayd.joyfukitchen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/5/9.
 */

public class RecordDetailsActivity extends BaseActivity {

    private TextView fooddetails_tv_show_weight;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarTrans();
        //隐藏标题栏
        getSupportActionBar().hide();
        setContentView(R.layout.layout_recorddetails_activity);
        initView();

        Intent intent = getIntent();
        intent.getParcelableExtra()
    }

    /**初始化*/
    private void initView() {
        fooddetails_tv_show_weight = (TextView) findViewById(R.id.fooddetails_tv_show_weight);
    }
}
