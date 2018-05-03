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

        // 시스템으로부터 현재시간(ms) 가져오기
        long now = System.currentTimeMillis();
        // Data 객체에 시간을 저장한다.
        Date date = new Date(now);
        // 각자 사용할 포맷을 정하고 문자열로 만든다.
        SimpleDateFormat sdfNow = new SimpleDateFormat("MM/dd");
        String strNow = sdfNow.format(date);

        TextView tv2 = (TextView) getActivity().findViewById(R.id.close_Time);
        TextView date2 = (TextView) getActivity().findViewById(R.id.date2);

        date2.setText(strNow);

        if(minute < 10) {
            tv2.setText(hourOfDay + ":0" + minute);
        }else{
            tv2.setText(hourOfDay + ":" + minute );
        }

        Toast.makeText(getActivity(), hourOfDay + "시" + minute+ "분 " + "으로 설정되었습니다", Toast.LENGTH_LONG).show();
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