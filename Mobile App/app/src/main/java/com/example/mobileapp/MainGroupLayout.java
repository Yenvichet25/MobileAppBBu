package com.example.mobileapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.mobileapp.adapter.FragmentAdapter;
import com.example.mobileapp.fragments.Contact;
import com.example.mobileapp.fragments.Favorities;
import com.example.mobileapp.fragments.Groups;
import com.google.android.material.tabs.TabLayout;

public class MainGroupLayout extends AppCompatActivity {
    private ViewPager viewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_group_layout);

        toolbar = (Toolbar) findViewById(R.id.toolbarId); // id for toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpagerId);
        SetupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tablayoutId);
        tabLayout.setupWithViewPager(viewPager);
        SetTabIcon();
    }
    private void SetupViewPager(ViewPager vPager){
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.CreateTab("Groups", new Groups());
        adapter.CreateTab("Favorites", new Favorities());
        adapter.CreateTab("Contact", new Contact());

        vPager.setAdapter(adapter);
    }

    private void SetTabIcon(){
        tabLayout.getTabAt(0).setIcon(R.drawable.contact);
        tabLayout.getTabAt(1).setIcon(R.drawable.help);
        tabLayout.getTabAt(2).setIcon(R.drawable.setting);
    }
}

