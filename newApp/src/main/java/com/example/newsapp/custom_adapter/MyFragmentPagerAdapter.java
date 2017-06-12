package com.example.newsapp.custom_adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.newsapp.appconfigs.MyLog;
import com.example.newsapp.fragment.SuperAwesomeCardFragment;

/**
 * Created by Administrator on 2017/6/8.
 */

public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {


    private String[] Categories;
    //SuperAwesomeCardFragment focusTopics, socia, economic, sports, technologys, internals, entertainmets;


    public MyFragmentPagerAdapter(FragmentManager fm, String[] categories) {
        super(fm);
        Categories = categories;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Categories[position];
    }

    @Override
    public int getCount() {
        return Categories.length;
    }

    @Override
    public Fragment getItem(int position) {
        MyLog.v("ddddddddddddddddddddddddddddd  " + position + "的时候");

        return SuperAwesomeCardFragment.newInstance(position);
    }
}
