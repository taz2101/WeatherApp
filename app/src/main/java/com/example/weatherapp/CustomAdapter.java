package com.example.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;

    public CustomAdapter(Context context, ArrayList<Weather> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    ArrayList<Weather> arrayList;
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_item,null);

        Weather weather = arrayList.get(position);

        TextView tvDay = convertView.findViewById(R.id.tv_list_item_date);
        TextView tvStt = convertView.findViewById(R.id.tv_list_item_stt);
        TextView tvMaxTemp = convertView.findViewById(R.id.tv_list_item_max_temp);
        TextView tvMinTemp = convertView.findViewById(R.id.tv_list_item_min_temp);
        ImageView ivStt = convertView.findViewById(R.id.iv_seven_next_day_stt);

        tvDay.setText(weather.Day);
        tvStt.setText(weather.Status);
        tvMaxTemp.setText(weather.MaxTemp + "°C");
        tvMinTemp.setText(weather.MinTemp + "°C");

        Picasso.get().load("https://openweathermap.org/img/wn/"+weather.Image+".png").into(ivStt);
        return convertView;
    }
}
