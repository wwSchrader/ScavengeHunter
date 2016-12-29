package com.wwschrader.android.scavengehunter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by Warren on 12/29/2016.
 * To allows users to edit objects selected from recycle view
 */

public class EditObjectiveDialogFragment extends DialogFragment {
    private EditText objectiveName, objectiveDescription, objectivePoints;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        @SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.dialog_create_objective, null);

        objectiveName = (EditText) dialogView.findViewById(R.id.objectives_name_edit_text);
        objectiveDescription = (EditText) dialogView.findViewById(R.id.objectives_description_edit_text);
        objectivePoints = (EditText) dialogView.findViewById(R.id.objectives_points_edit_text);

        //set text based on chosen object from recycle view
        objectiveName.setText(getArguments().getString("objectName"));
        objectiveDescription.setText(getArguments().getString("objectDescription"));
        objectivePoints.setText(String.format(Locale.getDefault(), "%d", getArguments().getInt("objectPoints")));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(dialogView)
                .setTitle("Edit Objective")
                .setPositiveButton(R.string.create_objective_positive_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //update database
                        updateObjectiveToDatabase();
                        Toast.makeText(getContext(), "Objective Updated", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton(R.string.create_objective_negative_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //just go back to previous fragment
                    }
                });
        return builder.create();
    }

    private void updateObjectiveToDatabase() {

    }


}
