package com.edu.vn.orderfoodapp.apdapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import java.util.ArrayList;

public class CartViewPagerAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> fragments;
    ArrayList<String> titleTab;
    public CartViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<Fragment>();
        titleTab = new ArrayList<String>();
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(fragments.size() > 0){
            return fragments.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        if(fragments.size()>0){
            return fragments.size();
        }
        return 0;
    }

    public void addFragment(Fragment fragment, String tabTitle){
        this.fragments.add(fragment);
        this.titleTab.add(tabTitle);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleTab.get(position);
    }
}
