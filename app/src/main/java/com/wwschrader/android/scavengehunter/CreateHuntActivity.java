package com.wwschrader.android.scavengehunter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

/**
 * Created by Warren on 12/13/2016.
 * Create hunt to sync with firebase
 */

public class CreateHuntActivity extends AppCompatActivity{
    private Button setStartTimeBtn, setEndTimeBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_hunt);

        setStartTimeBtn = (Button) findViewById(R.id.start_time_btn);
        setEndTimeBtn = (Button) findViewById(R.id.end_time_btn);

    }
}
