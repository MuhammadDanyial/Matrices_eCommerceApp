package com.MuhammadDanyialKhan.matrices_ecommerceapp;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

class SectionPageAdaptor extends FragmentPagerAdapter {

    private final List<Fragment> mfragmentList = new ArrayList<>();
    private  final List<String> mFragmentTitalList = new ArrayList<>();

    public void addFragment(Fragment fragment, String tital){
        mfragmentList.add(fragment);
        mFragmentTitalList.add(tital);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitalList.get(position);
    }

    public SectionPageAdaptor(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return mfragmentList.get(i);
    }

    @Override
    public int getCount() {
        return mfragmentList.size();
    }
}
