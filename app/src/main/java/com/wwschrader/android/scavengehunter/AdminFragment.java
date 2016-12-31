package com.wwschrader.android.scavengehunter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wwschrader.android.scavengehunter.objects.HuntUser;

/**
 * Created by Warren on 12/8/2016.
 * Screen that the creator of the hunt can choose to create teams, create tasks, start & end the hunt.
 */

public class AdminFragment extends Fragment {
    Button joinHuntBtn, leaveHuntBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_admin, container, false);

        joinHuntBtn = (Button) rootView.findViewById(R.id.admin_join_hunt_btn);
        leaveHuntBtn = (Button) rootView.findViewById(R.id.admin_leave_hunt_btn);

        joinHuntBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                joinHunt();
            }
        });

        leaveHuntBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                leaveHunt();
            }
        });

        return rootView;
    }

    private void leaveHunt() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference();
        if (user != null) {
            firebaseDatabase.child(getString(R.string.firebase_path_users)).child(user.getUid()).removeValue();
            Toast.makeText(getContext(), R.string.admin_left_hunt_toast, Toast.LENGTH_LONG).show();
        }
    }

    private void joinHunt() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            HuntUser huntUser = new HuntUser(user.getDisplayName(), user.getEmail(), NavigationActivity.huntUid);
            DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference();
            firebaseDatabase.child(getString(R.string.firebase_path_users)).child(user.getUid()).setValue(huntUser);
            Toast.makeText(getContext(), R.string.admin_join_hunt_toast, Toast.LENGTH_LONG).show();
        }
    }
}
