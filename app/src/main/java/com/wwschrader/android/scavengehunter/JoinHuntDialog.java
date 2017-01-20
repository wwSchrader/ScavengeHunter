package com.wwschrader.android.scavengehunter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.wwschrader.android.scavengehunter.objects.HuntGame;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Warren on 1/18/2017.
 * Fragment allows a player to join an existing hunt
 */

public class JoinHuntDialog extends DialogFragment {
    private EditText huntName, huntPassword;
    private TextView huntFoundMessage;
    private HuntGame mHuntGame;
    private Boolean huntIsFound;
    private DataSnapshot mDataSnapshot;
    private String huntKey;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        @SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.dialog_join_hunt, null);

        checkForExistingHunt();

        huntName = (EditText) dialogView.findViewById(R.id.join_hunt_name);
        huntFoundMessage = (TextView) dialogView.findViewById(R.id.hunt_game_found_message);
        huntPassword = (EditText) dialogView.findViewById(R.id.join_hunt_password);

        huntName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (mDataSnapshot != null){
                    checkForMatches(mDataSnapshot);
                    if (huntIsFound){
                        huntFoundMessage.setText(R.string.join_hunt_found_msg);
                    } else {
                        huntFoundMessage.setText(R.string.join_hunt_no_matching);
                    }
                }

            }
        });


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(dialogView)
                .setTitle(R.string.join_hunt_title)
                .setPositiveButton(R.string.join_hunt_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Do nothing here because we override this button later to change the close behaviour.
                        //However, we still need this because on older versions of Android unless we
                        //pass a handler the button doesn't get instantiated
                    }
                })
                .setNegativeButton(R.string.join_hunt_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //just close dialog
                    }
                });

        final AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //check to see if there was any update to Hunt
                checkForMatches(mDataSnapshot);

                if (huntIsFound){
                    //check for matching password
                    if (mHuntGame.getHuntPassword().equals(huntPassword.getText().toString())){
                        //add player to hunt game in firebase
                        DatabaseReference huntPlayerDatabase = FirebaseDatabase.getInstance().getReference("hunts/" + huntKey + "/huntPlayers/");
                        Map<String, Object> childUpdate = new HashMap<>();
                        childUpdate.put(NavigationActivity.userUid, true);
                        huntPlayerDatabase.updateChildren(childUpdate);

                        //add hunt game to player in firebase
                        //add player to hunt game in firebase
                        DatabaseReference playerDatabase = FirebaseDatabase.getInstance().getReference("users/" + NavigationActivity.userUid);
                        Map<String, Object> playerUpdate = new HashMap<>();
                        playerUpdate.put("participatingHunt", huntKey);
                        playerDatabase.updateChildren(playerUpdate);

                        Toast.makeText(getContext(), R.string.join_hunt_msg, Toast.LENGTH_LONG).show();

                        dialog.dismiss();
                    } else {
                        Toast.makeText(getContext(), R.string.join_hunt_incorrect_pswd, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext(), R.string.join_hunt_enter_name_msg, Toast.LENGTH_LONG).show();
                }
            }
        });

        return dialog;
    }

    private void checkForExistingHunt() {
        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference("hunts");
        Query existingHuntQuery = firebaseDatabase.orderByChild("huntName");
        existingHuntQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mDataSnapshot = dataSnapshot;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void checkForMatches(DataSnapshot dataSnapshot) {

        for (DataSnapshot hunts: dataSnapshot.getChildren()){
            HuntGame huntGame = hunts.getValue(HuntGame.class);
            if (huntGame.getHuntName().equals(huntName.getText().toString())){
                //if a match is found, update HuntGame object
                mHuntGame = huntGame;
                huntIsFound = true;
                huntKey = hunts.getKey();
                return;
            }
        }

        //if no HuntGame is found
        mHuntGame = null;
        huntIsFound = false;
        huntKey = null;
    }
}
