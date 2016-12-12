package com.wwschrader.android.scavengehunter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/**
 * Created by Warren on 12/11/2016.
 * To host account related settings and options
 */

public class AccountFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);

        //add listener to delete account preference to call deleteUserAccount()
        Preference deleteAccountPreference = findPreference(this.getString(R.string.preference_delete_account_key));
        Preference.OnPreferenceClickListener onClickListener = new Preference.OnPreferenceClickListener(){
            @Override
            public boolean onPreferenceClick(Preference preference) {
                deleteUserAccount();
                return true;
            }
        };
        deleteAccountPreference.setOnPreferenceClickListener(onClickListener);
    }

    private void deleteUserAccount(){
        final Activity activity = getActivity();

        AuthUI.getInstance()
                .delete(activity)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            //Deletion succeeded
                            startActivity(new Intent(activity, MainActivity.class));
                            activity.finish();
                        } else {
                            //Deletion failed
                            Toast.makeText(getActivity(), "Error in deleting account. Please contact us for assistance.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
