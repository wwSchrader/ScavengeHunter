package com.wwschrader.android.scavengehunter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Warren on 12/11/2016.
 * To display if user is hosting or has joined a hunt. Display buttons to host or join.
 */

public class HomeFragment extends Fragment {
    private Button createHuntButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        createHuntButton = (Button) rootView.findViewById(R.id.home_create_hunt_btn);
        createHuntButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CreateHuntActivity.class));
            }
        });

        return rootView;
    }
}