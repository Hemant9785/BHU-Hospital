package com.example.bhuhospital.Screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.bhuhospital.Adaptors.ViewPager_Adaptor;
import com.example.bhuhospital.R;
import com.example.bhuhospital.Screen.Fragments_LandingPage.MakeAppoinments_Fragment;
import com.example.bhuhospital.Screen.Fragments_LandingPage.Profile_Fragment;
import com.example.bhuhospital.Screen.Fragments_LandingPage.ViewAppoinments_Fragment;
import com.google.android.material.tabs.TabLayout;

public class LandingPage extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    FragmentManager fragmentManager;
    ViewPager_Adaptor pager_adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        fragmentManager = getSupportFragmentManager();
        viewId();

        pager_adaptor = new ViewPager_Adaptor(fragmentManager);
        pager_adaptor.addFragment(new Profile_Fragment(),"Profile Fragment");
        pager_adaptor.addFragment(new MakeAppoinments_Fragment(),"Make Appoinments");
        pager_adaptor.addFragment(new ViewAppoinments_Fragment(),"View Appoinments");
        viewPager.setAdapter(pager_adaptor);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void viewId() {
        tabLayout = findViewById(R.id.landingpage_tablayout);
        viewPager = findViewById(R.id.landingpage_viewpager);
    }
}