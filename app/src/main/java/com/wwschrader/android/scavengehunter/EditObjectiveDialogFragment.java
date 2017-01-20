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

import java.util.Locale;

/**
 * Created by Warren on 12/29/2016.
 * To allows users to edit objects selected from recycle view
 */

public class EditObjectiveDialogFragment extends DialogFragment {
    private EditText objectiveName, objectiveDescription, objectivePoints;
    private String uId;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        @SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.dialog_create_objective, null);

        objectiveName = (EditText) dialogView.findViewById(R.id.objectives_name_edit_text);
        objectiveDescription = (EditText) dialogView.findViewById(R.id.objectives_description_edit_text);
        objectivePoints = (EditText) dialogView.findViewById(R.id.objectives_points_edit_text);

        //set text based on chosen object from recycle view
        objectiveName.setText(getArguments().getString(getString(R.string.object_name_tag)));
        objectiveDescription.setText(getArguments().getString(getString(R.string.object_description_tag)));
        objectivePoints.setText(String.format(Locale.getDefault(), "%d", getArguments().getInt(getString(R.string.object_points_tag))));
        uId = getArguments().getString(getString(R.string.object_uid_tag));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(dialogView)
                .setTitle(R.string.edit_objectives_title)
                .setPositiveButton(R.string.create_objective_positive_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //update database
                        updateObjectiveToDatabase();
                        Toast.makeText(getContext(), R.string.object_updated_toast, Toast.LENGTH_LONG).show();
                    }
                })
                .setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteObjectiveInDatabase();
                        Toast.makeText(getContext(), "Objective deleted", Toast.LENGTH_LONG).show();
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

    private void deleteObjectiveInDatabase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(getString(R.string.firebase_path_objectives)).child(NavigationActivity.playerHuntUid).child(uId).removeValue();
    }

    private void updateObjectiveToDatabase() {
        HuntObjectives huntObjectives = new HuntObjectives(
                objectiveName.getText().toString(),
                objectiveDescription.getText().toString(),
                Integer.parseInt(objectivePoints.getText().toString()));

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child(getString(R.string.firebase_path_objectives)).child(NavigationActivity.playerHuntUid).child(uId).setValue(huntObjectives);
    }


}
