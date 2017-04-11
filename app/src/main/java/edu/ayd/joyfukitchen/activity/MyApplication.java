package edu.ayd.joyfukitchen.activity;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Administrator on 2017/4/11.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //加载字体框架
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/fzyxj.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
