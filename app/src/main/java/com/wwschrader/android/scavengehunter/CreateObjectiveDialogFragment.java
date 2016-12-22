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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wwschrader.android.scavengehunter.objects.HuntObjectives;

/**
 * Created by Warren on 12/20/2016.
 * Dialog to add an objective to firebase db
 */

public class CreateObjectiveDialogFragment extends DialogFragment{
    private EditText objectiveName, objectiveDescription, objectivePoints;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        @SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.dialog_create_objective, null);

        objectiveName = (EditText) dialogView.findViewById(R.id.objectives_name_edit_text);
        objectiveDescription = (EditText) dialogView.findViewById(R.id.objectives_description_edit_text);
        objectivePoints = (EditText) dialogView.findViewById(R.id.objectives_points_edit_text);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(dialogView)
                .setTitle(R.string.create_objective_dialog_title)
                .setPositiveButton(R.string.create_objective_positive_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //add to database
                        addObjectiveToDatabase();
                        Toast.makeText(getContext(), R.string.create_objective_toast, Toast.LENGTH_LONG).show();
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

    //create HuntObjective object and push to firebase db
    private void addObjectiveToDatabase() {
        HuntObjectives huntObjectives = new HuntObjectives(
                objectiveName.getText().toString(),
                objectiveDescription.getText().toString(),
                Integer.parseInt(objectivePoints.getText().toString()));

        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseDatabase.child(getString(R.string.firebase_path_objectives)).child(NavigationActivity.huntUid).push().setValue(huntObjectives);
    }
}
