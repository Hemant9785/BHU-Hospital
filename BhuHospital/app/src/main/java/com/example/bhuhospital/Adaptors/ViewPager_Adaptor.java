package com.example.bhuhospital.Adaptors;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class ViewPager_Adaptor extends FragmentPagerAdapter {

    private ArrayList<Fragment> Fragments = new ArrayList<>();
    private ArrayList<String> titles = new ArrayList<>();
    public ViewPager_Adaptor(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return Fragments.get(position);
    }

    @Override
    public int getCount() {
        return Fragments.size();
    }

    public void addFragment(Fragment f,String title)
    {
        Fragments.add(f);
        titles.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    public String getTitle(int position)
    {
        return titles.get(position);
    }
}
