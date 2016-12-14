package com.wwschrader.android.scavengehunter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Warren on 12/13/2016.
 * Create hunt to sync with firebase
 */

public class CreateHuntActivity extends AppCompatActivity implements TimeDateDialogFragment.TimeDateDialogListener{
    private Button setStartTimeBtn, setEndTimeBtn;
    private String startDateButtonTag;
    private String endDateButtonTag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_hunt);

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

    private void startTimeDateDialog(String buttonTag){
        Bundle bundle = new Bundle();
        bundle.putString("button-pressed", buttonTag);
        DialogFragment timeDateDialogFragment = new TimeDateDialogFragment();
        timeDateDialogFragment.setArguments(bundle);
        timeDateDialogFragment.show(getSupportFragmentManager(), null);
    }


    @Override
    public void onDialogPositiveClick(int year, int month, int day, int hour, int minute, String selectedButtonTag) {
        //add 1 to display proper calendar month
        month += 1;

        if (selectedButtonTag.equals(startDateButtonTag)){
            setStartTimeBtn.setText(month + "/" + day + "/" + year + " " + hour + ":" + minute);
        } else if (selectedButtonTag.equals(endDateButtonTag)){
            setEndTimeBtn.setText(month + "/" + day + "/" + year + " " + hour + ":" + minute);
        }

    }
}
