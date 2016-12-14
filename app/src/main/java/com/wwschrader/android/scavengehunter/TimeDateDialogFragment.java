package com.wwschrader.android.scavengehunter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by Warren on 12/14/2016.
 * Dialog fragment to select both date and time for start and ending times for a hunt
 */

public class TimeDateDialogFragment extends DialogFragment {


    public interface TimeDateDialogListener{
        void onDialogPositiveClick(int year, int month, int day, int hour, int minute, String buttonTag);
    }

    private TimeDateDialogListener mTimeDateDialogListener;
    private DatePicker mDatePicker;
    private TimePicker mTimePicker;
    private int year, month, day, hour, minute;
    private String buttonTag;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mTimeDateDialogListener = (TimeDateDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement TimeDateDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //set current time as default values for picker
        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        //identifies either start or end time button was pressed
        buttonTag = getArguments().getString(getString(R.string.button_pressed_tag_key));

        View dialogView = View.inflate(getActivity(), R.layout.dialog_date_and_time_picker, null);

        mDatePicker = (DatePicker) dialogView.findViewById(R.id.datePicker);
        mDatePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                year = selectedYear;
                month = selectedMonth;
                day = selectedDay;
            }
        });
        mTimePicker = (TimePicker) dialogView.findViewById(R.id.timePicker);
        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hourOfDay, int selectedMinute) {
                hour = hourOfDay;
                minute = selectedMinute;
            }
        });

        //set default time for pickers
        mDatePicker.updateDate(year, month, day);
        if (Build.VERSION.SDK_INT >= 23){
            mTimePicker.setHour(hour);
            mTimePicker.setMinute(minute);
        } else {
            //noinspection deprecation
            mTimePicker.setCurrentHour(hour);
            //noinspection deprecation
            mTimePicker.setCurrentMinute(minute);
        }



        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialogView)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //send selected time and date back to activity
                        mTimeDateDialogListener.onDialogPositiveClick(year, month, day, hour, minute, buttonTag);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        return builder.create();
    }
}
