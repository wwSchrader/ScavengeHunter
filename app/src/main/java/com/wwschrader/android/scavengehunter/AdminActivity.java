package com.wwschrader.android.scavengehunter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.wwschrader.android.scavengehunter.adapters.CollectionPagerAdapter;

/**
 * Created by Warren on 12/8/2016.
 * For hosting the admin related fragments.
 */

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        CollectionPagerAdapter collectionPagerAdapter = new CollectionPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(collectionPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }
}
