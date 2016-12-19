package com.wwschrader.android.scavengehunter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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

        deleteHuntButton = (Button) rootView.findViewById(R.id.home_delete_hunt_btn);
        deleteHuntButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(huntUniqueId != null){
                    //remove hunt from firebase
                    mDatabaseReference.child("hunts").child(huntUniqueId).removeValue();
                    Toast.makeText(mContext, "Hunt deleted!", Toast.LENGTH_LONG).show();

                    huntUniqueId = null;

                    //reset buttons to default state
                    checkDataBaseForHunt();
                }
            }
        });
        huntStatusTextView = (TextView) rootView.findViewById(R.id.hunt_status_textview);

        checkDataBaseForHunt();

        return rootView;
    }

    private void checkDataBaseForHunt() {

        //look for any hunts matching uId. Change textview if found.
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot hunts: dataSnapshot.getChildren()){
                    mHuntGame = hunts.getValue(HuntGame.class);
                    if (mHuntGame.getHuntName() != null){
                        huntUniqueId = hunts.getKey();
                        huntStatusTextView.setText(mHuntGame.getHuntName());
                        createHuntButton.setVisibility(Button.GONE);
                        joinHuntButton.setVisibility(Button.GONE);
                        deleteHuntButton.setVisibility(Button.VISIBLE);
                        return;
                    }
                }
                huntStatusTextView.setText(R.string.home_no_hunt_textview);
                createHuntButton.setVisibility(Button.VISIBLE);
                joinHuntButton.setVisibility(Button.VISIBLE);
                deleteHuntButton.setVisibility(Button.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Event Listener", "loadHunt:onCancelled", databaseError.toException());
                Toast.makeText(getActivity(), "Failed to retrieve hunt info.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mDatabaseReference.child("hunts").orderByChild("userUid").equalTo(mFirebaseUser.getUid()).addValueEventListener(eventListener);
    }
}