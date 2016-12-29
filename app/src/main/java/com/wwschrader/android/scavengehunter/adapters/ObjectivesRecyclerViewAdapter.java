package com.wwschrader.android.scavengehunter.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;
import com.wwschrader.android.scavengehunter.EditObjectiveDialogFragment;
import com.wwschrader.android.scavengehunter.objects.HuntObjectives;
import com.wwschrader.android.scavengehunter.viewholders.ObjectiveRecyclerViewHolder;

/**
 * Created by Warren on 12/22/2016.
 * FirebaseRecyclerAdapter for objectives
 */

public class ObjectivesRecyclerViewAdapter extends FirebaseRecyclerAdapter<HuntObjectives, ObjectiveRecyclerViewHolder> {

    private Context mContext;

    public ObjectivesRecyclerViewAdapter(Class<HuntObjectives> modelClass, int modelLayout, Class<ObjectiveRecyclerViewHolder> viewHolderClass, Query ref, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        mContext = context;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void populateViewHolder(ObjectiveRecyclerViewHolder viewHolder, final HuntObjectives model, final int position) {
        viewHolder.objectiveNameTextView.setText(model.getObjectiveName());
        viewHolder.objectiveDescriptionTextView.setText(model.getObjectiveDescription());
        viewHolder.objectivePointsTextView.setText(Integer.toString(model.getPoints()));

        viewHolder.getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //grab info for objective to pass to dialog fragment
                Bundle bundle = new Bundle();
                bundle.putString("objectName", model.getObjectiveName());
                bundle.putString("objectDescription", model.getObjectiveDescription());
                bundle.putInt("objectPoints", model.getPoints());

                DialogFragment editObjectiveFragment = new EditObjectiveDialogFragment();
                editObjectiveFragment.setArguments(bundle);
                editObjectiveFragment.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "CreateObjectiveDialogFragmentEdit");
            }
        });
    }
}