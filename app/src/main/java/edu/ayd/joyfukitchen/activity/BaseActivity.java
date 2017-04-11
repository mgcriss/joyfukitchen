package edu.ayd.joyfukitchen.activity;

import android.app.Activity;
import android.content.Context;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Administrator on 2017/4/11.
 * 所有Activity的父类
 */

public class BaseActivity extends Activity {
    @Override
    protected void attachBaseContext(Context newBase) {
        //初始化字体框架
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}
