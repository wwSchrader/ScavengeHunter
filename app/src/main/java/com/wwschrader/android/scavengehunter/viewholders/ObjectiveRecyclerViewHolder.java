package com.wwschrader.android.scavengehunter.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wwschrader.android.scavengehunter.R;

/**
 * Created by Warren on 12/20/2016.
 * View holder for objectives in recycler view
 */

public class ObjectiveRecyclerViewHolder extends RecyclerView.ViewHolder {
    public final TextView objectiveNameTextView;
    public final TextView objectiveDescriptionTextView;
    public final TextView objectivePointsTextView;
    private final View rootView;

    public ObjectiveRecyclerViewHolder(View itemView) {
        super(itemView);
        objectiveNameTextView = (TextView) itemView.findViewById(R.id.objective_name_textview);
        objectiveDescriptionTextView = (TextView) itemView.findViewById(R.id.objective_description_textview);
        objectivePointsTextView = (TextView) itemView.findViewById(R.id.objective_points_textview);
        rootView = itemView;
    }

    public View getRootView() {
        return rootView;
    }
}
