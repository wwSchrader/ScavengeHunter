package com.wwschrader.android.scavengehunter.adapters;

import android.annotation.SuppressLint;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;
import com.wwschrader.android.scavengehunter.objects.HuntObjectives;
import com.wwschrader.android.scavengehunter.viewholders.ObjectiveRecyclerViewHolder;

/**
 * Created by Warren on 12/22/2016.
 * FirebaseRecyclerAdapter for objectives
 */

public class ObjectivesRecyclerViewAdapter extends FirebaseRecyclerAdapter<HuntObjectives, ObjectiveRecyclerViewHolder> {

    public ObjectivesRecyclerViewAdapter(Class<HuntObjectives> modelClass, int modelLayout, Class<ObjectiveRecyclerViewHolder> viewHolderClass, Query ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void populateViewHolder(ObjectiveRecyclerViewHolder viewHolder, HuntObjectives model, int position) {
        viewHolder.objectiveNameTextView.setText(model.getObjectiveName());
        viewHolder.objectiveDescriptionTextView.setText(model.getObjectiveDescription());
        viewHolder.objectivePointsTextView.setText(Integer.toString(model.getPoints()));
    }

    @Override
    public ObjectiveRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(parent, viewType);
    }
}
