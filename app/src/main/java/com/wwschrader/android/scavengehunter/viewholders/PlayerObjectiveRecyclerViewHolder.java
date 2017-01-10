package com.wwschrader.android.scavengehunter.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.wwschrader.android.scavengehunter.R;

/**
 * Created by Warren on 12/20/2016.
 * View holder for objectives in recycler view
 */

public class PlayerObjectiveRecyclerViewHolder extends RecyclerView.ViewHolder {
    public final TextView objectiveNameTextView;
    public final TextView objectiveDescriptionTextView;
    public final TextView objectivePointsTextView;
    public final CheckBox objectivesCheckBox;
    private final View rootView;

    public PlayerObjectiveRecyclerViewHolder(View itemView) {
        super(itemView);
        objectiveNameTextView = (TextView) itemView.findViewById(R.id.player_objective_name_textview);
        objectiveDescriptionTextView = (TextView) itemView.findViewById(R.id.player_objective_description_textview);
        objectivePointsTextView = (TextView) itemView.findViewById(R.id.player_objective_points_textview);
        objectivesCheckBox = (CheckBox) itemView.findViewById(R.id.player_objectives_check_box);

        rootView = itemView;
    }

    public View getRootView() {
        return rootView;
    }
}
