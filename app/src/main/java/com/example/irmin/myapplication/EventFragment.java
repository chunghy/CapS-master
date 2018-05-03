package com.example.irmin.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class EventFragment extends Fragment {

    private static final String TAG_JSON = "webnautes";
    private static final String TAG_NUM = "eventNum";
    private static final String TAG_ID = "userID";
    private static final String TAG_TITLE = "eventTitle";
    private static final String TAG_CONTENT = "eventContent";
    private static final String TAG_START = "startTime";
    private static final String TAG_CLOSE = "closeTime";
    private static final String TAG_AMOUNT = "amount";
    private static final String TAG_IMG = "eventImg";

    ArrayList<HashMap<String, String>> eventList;
    ListView list;
    String myJSON;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        SharedPreferences pref;
        pref = this.getActivity().getSharedPreferences("sharedID", Context.MODE_PRIVATE);
        String user_Id = pref.getString("sharedID","");

        String userID = user_Id.toString();

        View view = inflater.inflate(R.layout.fragment_event, container, false);
        list = (ListView) view.findViewById(R.id.eventListView);
        eventList = new ArrayList<>();

        GetData task = new GetData();
        try {
            task.execute("http://irmin95.cafe24.com/EventList5.php?userID=" + URLEncoder.encode(userID,"UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(getContext(),
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            myJSON = result;

            // 시스템으로부터 현재시간(ms) 가져오기
            long now = System.currentTimeMillis();
            // Data 객체에 시간을 저장한다.
            Date date = new Date(now);
            // 각자 사용할 포맷을 정하고 문자열로 만든다.
            SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            String strNow = sdfNow.format(date);

            try {
                SimpleDateFormat origin_format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                Date date_s = origin_format.parse(TAG_START);
                Date date_c = origin_format.parse(TAG_CLOSE);
                Date date_now = origin_format.parse(strNow);

                int a = date_now.compareTo(date_s);
                int b = date_now.compareTo(date_c);
                if (a >= 0 && b < 0) {
                    //시작시간 <= 현재시간 < 마지막시간
                    showList();
                } else {

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String uri = params[0];

            try {

                URL url = new URL(uri);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();
                int responseStatusCode = httpURLConnection.getResponseCode();

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();
                return sb.toString().trim();
            } catch (Exception e) {
                errorString = e.toString();
                return null;
            }
        }
    }

    protected void showList() {

        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            JSONArray ja = jsonObj.getJSONArray(TAG_JSON);

            for (int i = 0; i < ja.length(); i++) {
                JSONObject c = ja.getJSONObject(i);

//                String num = c.optString(TAG_NUM);
                String id = c.optString(TAG_ID);
                String title = c.optString(TAG_TITLE);
                String content = c.optString(TAG_CONTENT);
                String start = c.optString(TAG_START);
                String close = c.optString(TAG_CLOSE);
//                String amount = c.optString(TAG_AMOUNT);
//                String img = c.optString(TAG_IMG);

                HashMap<String, String> list = new HashMap<>();

//                list.put(TAG_NUM, num);
                list.put(TAG_ID, id);
                list.put(TAG_TITLE, title);
                list.put(TAG_CONTENT, content);
                list.put(TAG_START, start);
                list.put(TAG_CLOSE, close);
//                list.put(TAG_AMOUNT, amount);
//                list.put(TAG_IMG, img);

                eventList.add(list);

            }

            ListAdapter adapter = new SimpleAdapter(
                    getActivity(), eventList, R.layout.event,
                    new String[]{TAG_ID, TAG_TITLE, TAG_CONTENT, TAG_START, TAG_CLOSE},
                    new int[]{R.id.userID, R.id.eventTitle, R.id.eventContent, R.id.startTime, R.id.closeTime}
            );


            list.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}

