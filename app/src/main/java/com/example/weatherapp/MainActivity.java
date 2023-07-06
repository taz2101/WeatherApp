package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    TextView tvCity, tvTemp, tvStt, tvHumidity, tvCloud, tvWind, tvDate;
    Button bNextDay;
    ImageView ivStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Init();

        GetCurrentWeatherData();

        bNextDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SevenNextDayActivity.class);
                startActivity(intent);
            }
        });
    }

    public void GetCurrentWeatherData() {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = "https://api.openweathermap.org/data/2.5/weather?q=hanoi&appid=8a805044f918af343c79d8917647238d&units=metric";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String day = jsonObject.getString("dt");
                            long l = Long.valueOf(day);
                            Date date = new Date(l*1000);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd HH:mm:ss");
                            String Day = simpleDateFormat.format(date);
                            tvDate.setText(Day);
                            //Weather
                            JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                            JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0); //chỉ 1 phần tử
                            String status = jsonObjectWeather.getString("main");
                            String icon = jsonObjectWeather.getString("icon");
                            Picasso.get().load("https://openweathermap.org/img/wn/"+icon+".png").into(ivStatus);
                            tvStt.setText(status);
                            //Main
                            JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                            String temperature = jsonObjectMain.getString("temp");
                            String hum = jsonObjectMain.getString("humidity");

                            Double a = Double.valueOf(temperature);
                            String Temperature = String.valueOf(a.intValue());
                            tvTemp.setText(Temperature + "°C");
                            tvHumidity.setText(hum + "%");
                            //Wind
                            JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                            String wind = jsonObjectWind.getString("speed");
                            tvWind.setText(wind + "m/s");
                            //Cloud
                            JSONObject jsonObjectCloud = jsonObject.getJSONObject("clouds");
                            String cloud = jsonObjectCloud.getString("all");
                            tvCloud.setText(cloud + "%");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
        });
        requestQueue.add(stringRequest);
    }

    private void Init() {
        tvCity = findViewById(R.id.tv_main_activity_city);
        tvCloud = findViewById(R.id.tv_main_activity_cloud);
        tvDate = findViewById(R.id.tv_main_activity_date);
        tvHumidity = findViewById(R.id.tv_main_activity_humidity);
        tvWind = findViewById(R.id.tv_main_activity_wind);
        tvStt = findViewById(R.id.tv_main_activity_status);
        tvTemp = findViewById(R.id.tv_main_activity_temperature);
        ivStatus = findViewById(R.id.iv_main_activity_status);
        bNextDay = findViewById(R.id.b_main_activity_next_day);
    }
}