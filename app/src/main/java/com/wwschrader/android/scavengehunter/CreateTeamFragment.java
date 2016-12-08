package com.wwschrader.android.scavengehunter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Warren on 12/6/2016.
 * Fragment for naming a team if a user selects to create one from the MainFragment.
 */

public class CreateTeamFragment extends Fragment{
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_createteam, container);
        mContext = getContext();

        Button saveHuntBtn = (Button) rootView.findViewById(R.id.save_hunt_btn);
        saveHuntBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveHuntBtnPress();
            }
        });
        return rootView;
    }

    private void saveHuntBtnPress() {
        Intent intent = new Intent(mContext, AdminActivity.class);
        startActivity(intent);
    }
}
