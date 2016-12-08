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
 * Created by Warren on 12/2/2016.
 */

public class MainFragment extends Fragment {
    Context mContext;
    Button createHuntBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        createHuntBtn = (Button) rootView.findViewById(R.id.create_hunt_btn);
        createHuntBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createHunt();
            }
        });

        return rootView;
    }

    //user selects to create a hunt
    public void createHunt(){
        Intent intent = new Intent(mContext, CreateTeamActivity.class);
        startActivity(intent);
    }
}
