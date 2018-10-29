package com.example.dlwls.myweather.model;

import android.util.Log;

import com.example.dlwls.myweather.util.ConvertDate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WeatherForecast {
    public final List<Forecast> forecastList = new ArrayList<Forecast>();

    public WeatherForecast(JSONObject list) throws JSONException{
        JSONArray array = list.getJSONArray("list");
        //array => list로 이동
        JSONObject start = array.getJSONObject(0);
        String idx = ConvertDate.CONVERT_DAY(start.getLong("dt"));
        for(int i = 0; i < array.length(); i++){
            JSONObject forecast = array.getJSONObject(i);
            if(forecastList.size() == 3) {break;}
            if(!ConvertDate.CONVERT_DAY(forecast.getLong("dt")).equals(idx) && ConvertDate.CONVERT_TIME(forecast.getLong("dt")).equals("03")){
                this.forecastList.add(new Forecast(forecast));
            }
            Log.d("TIEM!!!!",ConvertDate.CONVERT_TIME(forecast.getLong("dt")));
        }
    }
}
