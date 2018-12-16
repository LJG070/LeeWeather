package com.example.dlwls.myweather;

import android.app.LauncherActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Icon;
import android.icu.text.UnicodeFilter;
import android.icu.text.UnicodeSet;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.os.TestLooperManager;
import android.support.v4.app.NotificationCompat;
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
import android.widget.Toast;

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
    public List items;
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
            SystemClock.sleep(3000);

            loading.dismiss();
                adpater = new WeatherAdapter(MainActivity.this, weatherForecast.forecastList);
                listView = (ListView)findViewById(R.id.weather_list);
                listView.setAdapter(adpater);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Forecast forecast = (Forecast)adapterView.getAdapter().getItem(i);
                    }
                });

                Forecast forecast = (Forecast)listView.getAdapter().getItem(0);

            Notification.Builder notifiactionBuilder =
                    new Notification.Builder(MainActivity.this)
                            .setSmallIcon(R.drawable.ic_launcher_background)
                            .setContentTitle("시간 : " + forecast.dtTxt)
                            .setContentText("기온 : " + forecast.main.temp);

            NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(0, notifiactionBuilder.build());
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}
