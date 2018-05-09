package com.example.irmin.myapplication;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    Calendar mCalendar = Calendar.getInstance();
    int hour, min ;

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        TextView tv1 = (TextView) getActivity().findViewById(R.id.start_Time);

        // Display the 12 hour format time in app interface
        if(minute < 10) {
            tv1.setText(hourOfDay + ":0" + minute);
        }else{
            tv1.setText(hourOfDay + ":" + minute);
        }
        Toast.makeText(getActivity(), hourOfDay + "시 " + minute+ "분 " + "으로 설정되었습니다", Toast.LENGTH_LONG).show();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        hour = mCalendar.get(Calendar.HOUR_OF_DAY);
        min = mCalendar.get(Calendar.MINUTE);

        TimePickerDialog mTimePickerDialog = new TimePickerDialog(
                getContext(), this,  hour, min, true);
        return mTimePickerDialog;
    }

}
