package edu.ayd.joyfukitchen.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.RadioGroup;

import java.util.ArrayList;

import edu.ayd.joyfukitchen.Adapter.MyFragmentPageAdapter;
import edu.ayd.joyfukitchen.Fragment.ReciderFragment;
import edu.ayd.joyfukitchen.Fragment.UserFragment;
import edu.ayd.joyfukitchen.Fragment.WeightFragment;


public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    private ViewPager vp_index;
    private MyFragmentPageAdapter myFragmentPageAdapter;
    private RadioGroup rg_bottom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        super.setStatusBarTrans();




        init();
        vp_index.setAdapter(myFragmentPageAdapter);
        rg_bottom.check(R.id.rb_weight);
        setPageChangeListener();
        setCheckedChangeListener();

    }


    /**
     * 设置radioGroup切换选中事件
     * 点击下方按钮切换页面
     */
    private void setCheckedChangeListener() {
        rg_bottom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                Log.i(TAG, "onCheckedChanged: i= " + i);
                int position = 0;
                switch (i) {
                    case R.id.rb_weight:
                        position = 0;
                        break;
                    case R.id.rb_recider:
                        position = 1;
                        break;
                    case R.id.rb_user:
                        position = 2;
                        break;
                    default:
                        ;
                }
                vp_index.setCurrentItem(position);
            }
        });
    }


    /**
     * 给viewPager设置页面切换事件
     * 滑动页面切换下方按钮选中(背景色等)
     * *
     */
    private void setPageChangeListener() {
        vp_index.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int id = 0;
                switch (position) {
                    case 0:
                        id = R.id.rb_weight;
                        break;
                    case 1:
                        id = R.id.rb_recider;
                        break;
                    case 2:
                        id = R.id.rb_user;
                        break;
                    default:
                        ;
                }
                Log.i(TAG, "onPageSelected: id = " + id + " ...........position = " + position);
                rg_bottom.check(id);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    /**
     * 初始化控件,数据等
     * */
    private void init() {
        vp_index = (ViewPager) findViewById(R.id.vp_index);
        rg_bottom = (RadioGroup) findViewById(R.id.rg_bottom);


        Fragment weightFragment = new WeightFragment(this);
        ReciderFragment reciderFragment = new ReciderFragment(this);
        UserFragment userFragment = new UserFragment(this);

        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(weightFragment);
        fragments.add(reciderFragment);
        fragments.add(userFragment);

        myFragmentPageAdapter = new MyFragmentPageAdapter(getSupportFragmentManager(), fragments);

    }


}
