package com.example.dlwls.myweather.service;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.example.dlwls.myweather.R;
import com.example.dlwls.myweather.model.WeatherForecast;
import com.example.dlwls.myweather.share.ApplicationShare;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherApi {
    //http://api.openweathermap.org/data/2.5/forecast?KR&appid=e7be64f472973091a3e053487a148895&units=metric&q=seoul
    public static final String URL_STR = "%s?apikey=%s&units=metric&q=";
    public static WeatherForecast getWeather(Context context, String location) throws IOException, JSONException{
        ApplicationShare app = (ApplicationShare)context.getApplicationContext();
        String apikey = app.getApiKey();
        String urls = context.getApplicationContext().getResources().getString(R.string.url);
        URL url = new URL(String.format(URL_STR, urls,apikey) + (location == null ? "Seoul" : location));
        HttpURLConnection conn = null;
        StringBuilder sb = new StringBuilder();
        try{
            //Connection => create JSON => return WeatherForecast
            conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            int responsCode = conn.getResponseCode();
            if(responsCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = null;

                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                JSONObject json = new JSONObject(sb.toString());
                WeatherForecast weatherForecast = new WeatherForecast(json);
                conn.disconnect();
                return weatherForecast;
            } else {
                Log.d("OnWeatherApi", "URL=" + URL_STR + location);
            }
        } catch (Exception e){
            e.printStackTrace();
            Log.d("OnWeatherAPi", "Exception Activated");
        }
        return null;
    }
}
