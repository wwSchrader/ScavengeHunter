package com.wwschrader.android.scavengehunter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wwschrader.android.scavengehunter.objects.HuntGame;

/**
 * Created by Warren on 12/13/2016.
 * Create hunt to sync with firebase
 */

public class CreateHuntActivity extends AppCompatActivity implements TimeDateDialogFragment.TimeDateDialogListener{
    private EditText huntNameEditText, huntPasswordEditText;
    private Button setStartTimeBtn, setEndTimeBtn, createHuntSubmit;
    private String startDateButtonTag;
    private String endDateButtonTag;
    private String huntName;
    private String huntPassword;
    private long startTime, endTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_hunt);

        createHuntSubmit = (Button) findViewById(R.id.create_hunt_submit);
        createHuntSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitHuntToFirebase();
            }
        });

        huntNameEditText = (EditText) findViewById(R.id.hunt_name_edit_text);
        huntNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                huntName = huntNameEditText.getText().toString();
                checkAllValuesFilled();
            }
        });

        huntPasswordEditText = (EditText) findViewById(R.id.hunt_password_edit_text);
        huntPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                huntPassword = huntPasswordEditText.getText().toString();
                checkAllValuesFilled();
            }
        });

        setStartTimeBtn = (Button) findViewById(R.id.start_time_btn);
        startDateButtonTag = (String) setStartTimeBtn.getTag();
        setEndTimeBtn = (Button) findViewById(R.id.end_time_btn);
        endDateButtonTag = (String) setEndTimeBtn.getTag();

        setStartTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimeDateDialog((String) setStartTimeBtn.getTag());
            }
        });

        setEndTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimeDateDialog((String) setEndTimeBtn.getTag());
            }
        });
    }

    private void submitHuntToFirebase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        //get uid to identify ownership of hunt
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            //push hunt object to firebase then close activity
            HuntGame huntGame = new HuntGame(huntName, huntPassword, startTime, endTime, user.getUid());
            String huntKey = databaseReference.child("hunts").push().getKey();
            databaseReference.child("hunts").child(huntKey).setValue(huntGame);
            //add huntKey to user object
            databaseReference.child("users").child(user.getUid()).child("adminHunt").setValue(huntKey);

            finish();
        }

    }

    private void startTimeDateDialog(String buttonTag){
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.button_pressed_tag_key), buttonTag);
        DialogFragment timeDateDialogFragment = new TimeDateDialogFragment();
        timeDateDialogFragment.setArguments(bundle);
        timeDateDialogFragment.show(getSupportFragmentManager(), null);
    }


    @Override
    public void onDialogPositiveClick(int year, int month, int day, int hour, int minute, String selectedButtonTag) {

        //add 1 to display proper calendar month
        month += 1;

        long calendarInMil = Utilities.convertCalendarStringToLong(year + "-" + month + "-" + day
                + "-" + hour + "-" + minute);

        if (selectedButtonTag.equals(startDateButtonTag)){
            setStartTimeBtn.setText(month + "/" + day + "/" + year + " " + hour + ":" + minute);
            startTime = calendarInMil;
        } else if (selectedButtonTag.equals(endDateButtonTag)){
            setEndTimeBtn.setText(month + "/" + day + "/" + year + " " + hour + ":" + minute);
            endTime = calendarInMil;
        }
        checkAllValuesFilled();
    }


    private void checkAllValuesFilled() {
        //If all values are filled
        if (huntName != null && !huntName.isEmpty() && huntPassword != null && !huntPassword.isEmpty() && startTime != 0 && endTime != 0){
            //make sure ending time isn't before beginning time
            if (endTime - startTime >= 0){
                //set button to create hunt as visible
                createHuntSubmit.setVisibility(Button.VISIBLE);
                return;
            }
        }
        //if any test fails, make button disappear
        createHuntSubmit.setVisibility(Button.GONE);
    }
}
