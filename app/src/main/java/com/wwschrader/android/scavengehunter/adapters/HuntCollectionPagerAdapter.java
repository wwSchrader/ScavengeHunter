package com.wwschrader.android.scavengehunter.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.wwschrader.android.scavengehunter.HuntHomeFragment;
import com.wwschrader.android.scavengehunter.HuntObjectivesFragment;

/**
 * Created by Warren on 12/31/2016.
 * Fragment to handle the hunt scroll tabs
 */

public class HuntCollectionPagerAdapter extends FragmentStatePagerAdapter {
    private final Context mContext;

    public HuntCollectionPagerAdapter(FragmentManager fragmentManager, Context context){
        super(fragmentManager);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HuntHomeFragment();
            case 1:
                return new HuntObjectivesFragment();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Home";
            case 1:
                return "Objectives";
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
