package com.wwschrader.android.scavengehunter.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.wwschrader.android.scavengehunter.AdminFragment;
import com.wwschrader.android.scavengehunter.AdminTeamManagementFragment;
import com.wwschrader.android.scavengehunter.R;

/**
 * Created by Warren on 12/8/2016.
 * For creating scrollable tabs.
 */

public class CollectionPagerAdapter extends FragmentStatePagerAdapter {
    private final Context mContext;

    public CollectionPagerAdapter(FragmentManager fragmentManager, Context context){
        super(fragmentManager);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new AdminFragment();
            case 1:
                return new AdminTeamManagementFragment();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return mContext.getString(R.string.page_title_admin_dash);
            case 1:
                return mContext.getString(R.string.admin_team_management_page_title);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
