package com.example.dlwls.myweather;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.text.UnicodeFilter;
import android.icu.text.UnicodeSet;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.os.TestLooperManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dlwls.myweather.adapter.WeatherAdapter;
import com.example.dlwls.myweather.model.Forecast;
import com.example.dlwls.myweather.model.WeatherForecast;
import com.example.dlwls.myweather.service.ImageViewTask;
import com.example.dlwls.myweather.service.WeatherApi;
import com.example.dlwls.myweather.share.ApplicationShare;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
    public TextView tvView;
    public LinearLayout weatherList;
    public ListView listView;
    public WeatherAdapter adpater;
    Spinner spinner;
    Button searchBtn;
    private final static String TAG = "SimpleWeather";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("SimpleWeather", "MainActivity#onCreate called");
        final String[] data = getResources().getStringArray(R.array.city_list);

        ApplicationShare app = (ApplicationShare)getApplication();
        String apiKey = app.getApiKey();
        Log.d(TAG, "API KEY = " + apiKey);

        //weatherList = (LinearLayout)findViewById(R.id.weather_list);
        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, data);
        spinner.setAdapter(adapter);
        searchBtn = (Button)findViewById(R.id.btn_search);
        searchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                WeatherAsyncTask weatherAsyncTask = new WeatherAsyncTask(getApplicationContext());
                weatherAsyncTask.execute(spinner.getSelectedItem().toString());
            }
        });
    }

    class WeatherAsyncTask extends AsyncTask<String, Void, WeatherForecast>{
        private final Context context;

        ProgressDialog loading = new ProgressDialog(MainActivity.this);

        public WeatherAsyncTask(Context context){
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            loading.setMessage("날씨 데이터를 불러오는 중입니다");
            loading.show();
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected WeatherForecast doInBackground(String... locations) {
            //HttpURL Connection
            try{
                return WeatherApi.getWeather(this.context, locations[0]);
            } catch(Exception e){
                return null;
            }
        }

        @Override
        protected void onPostExecute(WeatherForecast weatherForecast) {

            super.onPostExecute(weatherForecast);
//            for(Forecast forecast : weatherForecast.forecastList){
//                View item = View.inflate(MainActivity.this, R.layout.weather_item, null);
//                TextView tvDate = (TextView)item.findViewById(R.id.tvDate);
//                TextView tvTemp = (TextView)item.findViewById(R.id.tvTemp);
//                TextView tvDesc = (TextView)item.findViewById(R.id.tvDesc);
//                ImageView imageView = (ImageView)item.findViewById(R.id.imgIcon);
//                imageView.setTag(forecast.weather.icon);
//                StringTokenizer st= new StringTokenizer(forecast.dtTxt," ");
//                String date = st.nextToken();
//                String[] dates = date.split("-");
//
//                tvDate.setText(date +  st.nextToken().substring(0,2));
//                tvTemp.setText(forecast.main.temp + "\u2103");
//                tvDesc.setText(forecast.weather.description);
//                Log.d("SimpleWeather", forecast.weather.icon);
//                //image => 별도의 스레드가 필요함
//                ImageViewTask imageViewTask = new ImageViewTask(getApplicationContext());
//                imageViewTask.execute(imageView);
//
//                weatherList.addView(item);
//        }
            SystemClock.sleep(3000);

            loading.dismiss();
                adpater = new WeatherAdapter(MainActivity.this, weatherForecast.forecastList);
                listView = (ListView)findViewById(R.id.weather_list);
                listView.setAdapter(adpater);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}
