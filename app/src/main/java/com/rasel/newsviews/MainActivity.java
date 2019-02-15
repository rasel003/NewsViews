package com.rasel.newsviews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private SlidePagerAdapter slidePagerAdapter;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.slideViewPager);
        mTabLayout = findViewById(R.id.tab_layout);

        slidePagerAdapter = new SlidePagerAdapter(this);
        viewPager.setAdapter(slidePagerAdapter);

        mTabLayout.setupWithViewPager(viewPager, true);
    }
}
