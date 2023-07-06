package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.mbms.MbmsErrors;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SevenNextDayActivity extends AppCompatActivity {
    ImageView ivBack;
    ListView lvDayTemp;
    CustomAdapter adapter;
    ArrayList<Weather> arrayWeather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seven_next_day);
        Init();
        GetSevenDayData();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void Init() {
        ivBack = findViewById(R.id.iv_seven_next_day_back);
        lvDayTemp = findViewById(R.id.lv_seven_next_day);
        arrayWeather = new ArrayList<Weather>();
        adapter = new CustomAdapter(SevenNextDayActivity.this, arrayWeather);
        lvDayTemp.setAdapter(adapter);
    }

    private void GetSevenDayData() {
        String url = "https://api.openweathermap.org/data/2.5/forecast?q=hanoi&appid=8a805044f918af343c79d8917647238d&units=metric";
        RequestQueue requestQueue = Volley.newRequestQueue(SevenNextDayActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArrayList = jsonObject.getJSONArray("list");
                    for (int i = 0; i <= jsonArrayList.length(); i++) {
                        JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);
                        String day = jsonObjectList.getString("dt");
                        long l = Long.valueOf(day);
                        Date date = new Date(l*1000);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd");
                        String Day = simpleDateFormat.format(date);

                        JSONObject jsonObjectTemp = jsonObjectList.getJSONObject("temp");
                        String max = jsonObjectTemp.getString("max");
                        String min = jsonObjectTemp.getString("min");

                        Double a = Double.valueOf(max);
                        Double b = Double.valueOf(min);
                        String maxTemp = String.valueOf(a.intValue());
                        String minTemp = String.valueOf(b.intValue());

                        JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
                        JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                        String status = jsonObjectWeather.getString("description");
                        String icon = jsonObjectWeather.getString("icon");

                        arrayWeather.add(new Weather(day, status, icon, maxTemp, minTemp));
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

}