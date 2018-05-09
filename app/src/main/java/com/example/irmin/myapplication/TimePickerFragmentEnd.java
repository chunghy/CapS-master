package com.example.irmin.myapplication;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimePickerFragmentEnd extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    Calendar mCalendar = Calendar.getInstance();
    int hour, min ;

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        TextView tv2 = (TextView) getActivity().findViewById(R.id.close_Time);


        if(minute < 10) {
            tv2.setText(hourOfDay + ":0" + minute);
        }else{
            tv2.setText(hourOfDay + ":" + minute);
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