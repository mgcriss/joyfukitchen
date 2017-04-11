package edu.ayd.joyfukitchen.activity;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.widget.Button;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import edu.ayd.joyfukitchen.view.FontSizeAutoAdjustTextView;


public class MainActivity extends BaseActivity {

    private FontSizeAutoAdjustTextView ftv_1;
    private Button btn1;
    private Button btn2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_weight);
        // create our manager instance after the content view is set (在设置内容视图后创建管理器实例)
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // enable status bar tint (启用状态栏色调)
        tintManager.setStatusBarTintEnabled(true);
        // enable navigation bar tint (启用导航栏色调)
        tintManager.setNavigationBarTintEnabled(false);

        tintManager.setNavigationBarAlpha(50);
        // set a custom tint color for all system bars (为所有系统栏设置自定义色调)
        tintManager.setTintColor(Color.parseColor("#5CC3FE"));

        setupWindowAnimations();

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        Slide slide = (Slide) TransitionInflater.from(this).inflateTransition(R.transition.activity_slide);
        getWindow().setExitTransition(slide);
    }




}
