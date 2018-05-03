package com.example.irmin.myapplication;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.irmin.myapplication.R;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EventRegisterActivity extends AppCompatActivity{


    private AlertDialog dialog;
    FragmentManager fragmentManager = getFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_register);

        final TextView loginId = (TextView) findViewById(R.id.loginId);

        SharedPreferences pref = getSharedPreferences("sharedID",MODE_PRIVATE);
        String user_Id = pref.getString("sharedID","");
        loginId.setText(user_Id);

        final EditText event_title = (EditText) findViewById(R.id.event_title);
        final EditText event_content = (EditText) findViewById(R.id.event_content);
        final TextView start_Time = (TextView) findViewById(R.id.start_Time);
        final TextView close_Time = (TextView) findViewById(R.id.close_Time);
        final EditText amountNum = (EditText) findViewById(R.id.amount);
        final EditText eventImgText = (EditText) findViewById(R.id.eventImgText);


        final CheckBox checkAmount = (CheckBox) findViewById(R.id.checkAmount);


        amountNum.setVisibility(android.view.View.INVISIBLE);

        checkAmount.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkAmount.isChecked() == true) {
                    amountNum.setVisibility(android.view.View.VISIBLE);
                } else {
                    amountNum.setVisibility(android.view.View.INVISIBLE);
                }
            }
        });


        Button registerButton = (Button) findViewById(R.id.eventRegisterButton);
        registerButton.setOnClickListener(new View.OnClickListener()

        {

            @Override
            public void onClick(View view) {

                String userID = loginId.getText().toString();
                String eventTitle = event_title.getText().toString();
                String eventContent = event_content.getText().toString();
                String startTime = start_Time.getText().toString();
                String closeTime = close_Time.getText().toString();
                String amount = amountNum.getText().toString();
                String eventImg = eventImgText.getText().toString();


                if (eventTitle.equals("") || eventContent.equals("") || startTime.equals("") || closeTime.equals("") ) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EventRegisterActivity.this);
                    dialog = builder.setMessage("빈 칸 없이 입력해주세요.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(EventRegisterActivity.this);
                                dialog = builder.setMessage("이벤트 등록에 성공했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                finish();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(EventRegisterActivity.this);
                                dialog = builder.setMessage("이벤트 등록에 실패했습니다.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                com.example.irmin.myapplication.EventRegisterRequest eventRegisterRequest = new com.example.irmin.myapplication.EventRegisterRequest(userID, eventTitle, eventContent, startTime, closeTime, amount, eventImg, responseListener);
                RequestQueue queue = Volley.newRequestQueue(EventRegisterActivity.this);
                queue.add(eventRegisterRequest);


            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    public void Move_Start(View view) {
        TimePickerFragment mTimePickerFragment = new TimePickerFragment();
        mTimePickerFragment.show(getFragmentManager(), "TimePicker");
    }

    public void Move_End(View view) {
        TimePickerFragmentEnd mTimePickerFragmentEnd = new TimePickerFragmentEnd();
        mTimePickerFragmentEnd.show(getFragmentManager(), "TimePicker");
    }

}
