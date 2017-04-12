package edu.ayd.joyfukitchen.Adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/4/12.
 * 主页的ViewPager   Fragment适配器
 */

public class MyFragmentPageAdapter extends FragmentPagerAdapter {

    private List<Fragment> mList;

    public MyFragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }

    public MyFragmentPageAdapter(FragmentManager fm, List<Fragment> ls){
        super(fm);
        mList = ls;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
