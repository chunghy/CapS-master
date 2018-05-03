package com.example.irmin.myapplication;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class EventRegisterRequest extends StringRequest {

    final static private String URL = "http://irmin95.cafe24.com/EventRegister.php";
    private Map<String, String> parameters;

    public EventRegisterRequest(String userID, String eventTitle, String eventContent, String startTime ,String closeTime, String amount, String eventImg, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("eventTitle", eventTitle);
        parameters.put("eventContent", eventContent);
        parameters.put("startTime", startTime);
        parameters.put("closeTime", closeTime);
        parameters.put("amount", amount);
        parameters.put("eventImg", eventImg);

    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
