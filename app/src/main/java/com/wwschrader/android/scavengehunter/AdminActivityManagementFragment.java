package com.wwschrader.android.scavengehunter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Warren on 12/9/2016.
 * Fragment for adding, modifying, or deleting hunt activities
 */

public class AdminActivityManagementFragment extends Fragment {
    FloatingActionButton addActivityActionButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_admin_activity_management, container, false);

        addActivityActionButton = (FloatingActionButton) rootView.findViewById(R.id.activities_add_fab);
        addActivityActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment createObjectiveFragment = new CreateObjectiveDialogFragment();
                createObjectiveFragment.show(getFragmentManager(), "CreateObjectiveDialogFragment");
            }
        });

        return rootView;
    }
}
