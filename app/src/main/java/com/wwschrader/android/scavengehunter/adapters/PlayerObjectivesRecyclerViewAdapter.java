package com.wwschrader.android.scavengehunter.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;
import com.wwschrader.android.scavengehunter.R;
import com.wwschrader.android.scavengehunter.objects.HuntObjectives;
import com.wwschrader.android.scavengehunter.viewholders.PlayerObjectiveRecyclerViewHolder;

/**
 * Created by Warren on 12/22/2016.
 * FirebaseRecyclerAdapter for objectives
 */

public class PlayerObjectivesRecyclerViewAdapter extends FirebaseRecyclerAdapter<HuntObjectives, PlayerObjectiveRecyclerViewHolder> {

    private Context mContext;

    public PlayerObjectivesRecyclerViewAdapter(Class<HuntObjectives> modelClass, int modelLayout, Class<PlayerObjectiveRecyclerViewHolder> viewHolderClass, Query ref, Context context) {
        super(modelClass, R.layout.view_holder_player_objectives, viewHolderClass, ref);
        mContext = context;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void populateViewHolder(final PlayerObjectiveRecyclerViewHolder viewHolder, final HuntObjectives model, final int position) {
        viewHolder.objectiveNameTextView.setText(model.getObjectiveName());
        viewHolder.objectiveDescriptionTextView.setText(model.getObjectiveDescription());
        viewHolder.objectivePointsTextView.setText(Integer.toString(model.getPoints()));

        viewHolder.objectivesCheckBox.setChecked(true);

        viewHolder.getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}