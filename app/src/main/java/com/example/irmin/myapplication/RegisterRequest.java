package com.example.irmin.myapplication;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    final static private String URL = "http://irmin95.cafe24.com/UserRegister.php";
    private Map<String, String> parameters;

    public RegisterRequest(String userID, String userPassword,String category, String userName, String userTel ,String userArea, String userAddress, String userImg, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
        parameters.put("category", category);
        parameters.put("userName", userName);
        parameters.put("userTel", userTel);
        parameters.put("userArea", userArea);
        parameters.put("userAddress", userAddress);
        parameters.put("userImg", userImg);

    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
