package com.example.mobileapp.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentAdapter extends FragmentPagerAdapter {
    // data member
    private List<String> lstTtile = new ArrayList<String>();
    private List<Fragment> lstTab = new ArrayList<Fragment>();
    // constructor
    public FragmentAdapter( FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        return lstTab.get(position);
    }
    @Override
    public int getCount() {
        return lstTab.size();
    }
    public void CreateTab(String title,Fragment tabs){
        lstTtile.add(title);
        lstTab.add(tabs);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return lstTtile.get(position);
    }
}
