package com.wwschrader.android.scavengehunter.adapters;

import android.annotation.SuppressLint;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.wwschrader.android.scavengehunter.NavigationActivity;
import com.wwschrader.android.scavengehunter.R;
import com.wwschrader.android.scavengehunter.objects.HuntObjectives;
import com.wwschrader.android.scavengehunter.viewholders.PlayerObjectiveRecyclerViewHolder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Warren on 12/22/2016.
 * FirebaseRecyclerAdapter for objectives
 */

public class PlayerObjectivesRecyclerViewAdapter extends FirebaseRecyclerAdapter<HuntObjectives, PlayerObjectiveRecyclerViewHolder> {

    public PlayerObjectivesRecyclerViewAdapter(Class<HuntObjectives> modelClass, Class<PlayerObjectiveRecyclerViewHolder> viewHolderClass, Query ref) {
        super(modelClass, R.layout.view_holder_player_objectives, viewHolderClass, ref);

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void populateViewHolder(final PlayerObjectiveRecyclerViewHolder viewHolder, final HuntObjectives model, final int position) {
        viewHolder.objectiveNameTextView.setText(model.getObjectiveName());
        viewHolder.objectiveDescriptionTextView.setText(model.getObjectiveDescription());
        viewHolder.objectivePointsTextView.setText(Integer.toString(model.getPoints()));

        //check to see if user accomplished objective and set checkbox accordingly
        Map<String, Boolean> accomplishedObjectives = model.getAccomplishedUsers();
        if (accomplishedObjectives !=null){
            if (accomplishedObjectives.get(NavigationActivity.userUid)){
                viewHolder.objectivesCheckBox.setChecked(true);
            } else {
                viewHolder.objectivesCheckBox.setChecked(false);
            }
        } else {
            viewHolder.objectivesCheckBox.setChecked(false);
        }


        final String objectiveKey = getRef(position).getKey();

        viewHolder.objectivesCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                Map<String, Object> childUpdates = new HashMap<>();

                if (!viewHolder.objectivesCheckBox.isChecked()){
                    //remove user from objective and objective from user object
                    childUpdates.put("/objectives/" + NavigationActivity.playerHuntUid + "/" + objectiveKey + "/accomplishedUsers/" + NavigationActivity.userUid, null);
                    childUpdates.put("/users/" + NavigationActivity.userUid + "/accomplishedObjectives/" + objectiveKey, null);
                    viewHolder.objectivesCheckBox.setChecked(false);


                } else {
                    //add user to objective and objective to user object
                    childUpdates.put("/objectives/" + NavigationActivity.playerHuntUid + "/" + objectiveKey + "/accomplishedUsers/" + NavigationActivity.userUid, true);
                    childUpdates.put("/users/" + NavigationActivity.userUid + "/accomplishedObjectives/" + objectiveKey, true);
                    viewHolder.objectivesCheckBox.setChecked(true);
                }
                mDatabase.updateChildren(childUpdates);
            }
        });
    }
}