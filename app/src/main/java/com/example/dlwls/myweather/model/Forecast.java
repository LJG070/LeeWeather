package com.example.dlwls.myweather.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Forecast {
    public final Long dt;
    public final String dtTxt;
    public final Main main;
    public final Weather weather;

    public Forecast(JSONObject json) throws JSONException {
      this.dt = json.getLong("dt");
      this.dtTxt = json.getString("dt_txt");
      this.main = new Main(json.getJSONObject("main"));
      this.weather = new Weather(json.getJSONArray("weather"));
    }

    public class Main {
        public final String temp;
        public final String humidity;

        public Main(JSONObject main) throws JSONException{
            this.temp = main.getString("temp");
            this.humidity = main.getString("humidity");
        }
    }

    public class Weather {
        public final String main;
        public final String description;
        public final String icon;

        public Weather(JSONArray weather) throws JSONException{
            this.main = weather.getJSONObject(0).getString("main");
            this.description = weather.getJSONObject(0).getString("description");
            this.icon = weather.getJSONObject(0).getString("icon");
        }
    }
}
