package com.wwschrader.android.scavengehunter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wwschrader.android.scavengehunter.objects.HuntGame;

/**
 * Created by Warren on 12/11/2016.
 * To display if user is hosting or has joined a hunt. Display buttons to host or join.
 */

public class HomeFragment extends Fragment {
    private Button createHuntButton, joinHuntButton, deleteHuntButton;
    private TextView huntStatusTextView;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabaseReference;
    private HuntGame mHuntGame;
    private String huntUniqueId;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        mContext = getContext();

        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        createHuntButton = (Button) rootView.findViewById(R.id.home_create_hunt_btn);
        createHuntButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CreateHuntActivity.class));
            }
        });

        joinHuntButton = (Button) rootView.findViewById(R.id.join_hunt_btn);
        joinHuntButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment joinHuntDialogFragment = new JoinHuntDialog();
                joinHuntDialogFragment.show(getFragmentManager(), "JoinHunDialogFragment");
            }
        });

        deleteHuntButton = (Button) rootView.findViewById(R.id.home_delete_hunt_btn);
        deleteHuntButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(huntUniqueId != null){
                    //remove hunt from firebase
                    mDatabaseReference.child("hunts").child(huntUniqueId).removeValue();
                    mDatabaseReference.child("users").child(NavigationActivity.userUid).child("adminHunt").removeValue();
                    Toast.makeText(mContext, "Hunt deleted!", Toast.LENGTH_LONG).show();
                    huntUniqueId = null;
                }
            }
        });
        huntStatusTextView = (TextView) rootView.findViewById(R.id.hunt_status_textview);

        if (NavigationActivity.adminHuntUid != null || NavigationActivity.playerHuntUid != null){
            checkDataBaseForAdminHunt();
        }

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(userObjectReceiver, new IntentFilter("user-object-updated"));
        return rootView;
    }

    @Override
    public void onDestroyView() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(userObjectReceiver);
        super.onDestroyView();
    }

    private void checkDataBaseForAdminHunt() {

        //look for any hunts matching uId. Change textview if found.
        ChildEventListener eventListener = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                checkForAdminHunt(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                checkForAdminHunt(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Event Listener", "loadHunt:onCancelled", databaseError.toException());
            }
        };

        if (NavigationActivity.adminHuntUid != null){
            mDatabaseReference.child("hunts").orderByChild(NavigationActivity.adminHuntUid).addChildEventListener(eventListener);
        } else if (NavigationActivity.playerHuntUid != null){
            mDatabaseReference.child("hunts").orderByChild(NavigationActivity.playerHuntUid).addChildEventListener(eventListener);
        }
    }

    private void checkForAdminHunt(DataSnapshot dataSnapshot) {

        mHuntGame = dataSnapshot.getValue(HuntGame.class);
        if (NavigationActivity.adminHuntUid != null && dataSnapshot.getKey().equals(NavigationActivity.adminHuntUid)){
            huntUniqueId = dataSnapshot.getKey();
            huntStatusTextView.setText(mHuntGame.getHuntName());
            createHuntButton.setVisibility(Button.GONE);
            joinHuntButton.setVisibility(Button.GONE);
            if (mHuntGame.getUserUid().equals(NavigationActivity.userUid)){
                deleteHuntButton.setVisibility(Button.VISIBLE);
            }

            return;
        } else if (NavigationActivity.playerHuntUid != null && dataSnapshot.getKey().equals(NavigationActivity.mHuntUser.getParticipatingHunt())){
            huntUniqueId = dataSnapshot.getKey();
            huntStatusTextView.setText(mHuntGame.getHuntName());
            createHuntButton.setVisibility(Button.GONE);
            joinHuntButton.setVisibility(Button.GONE);
            deleteHuntButton.setVisibility(Button.GONE);
            return;
        }

        huntStatusTextView.setText(R.string.home_no_hunt_textview);
        createHuntButton.setVisibility(Button.VISIBLE);
        joinHuntButton.setVisibility(Button.VISIBLE);
        deleteHuntButton.setVisibility(Button.GONE);
    }

    //receiver to check for hunt again if HuntUsers wasn't implemented in time in NavigationActivity
    private final BroadcastReceiver userObjectReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkDataBaseForAdminHunt();
        }
    };
}