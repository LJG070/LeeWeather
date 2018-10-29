package com.example.dlwls.myweather;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dlwls.myweather.share.ApplicationShare;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ApplicationShare app = (ApplicationShare)getApplication();
        app.setApiKey("e7be64f472973091a3e053487a148895");
        Button btnWeather = (Button)findViewById(R.id.btn_weather);
        btnWeather.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), MainActivity.class);
//                intent.putExtra("location", "Seoul");
//                intent.putExtra("apikey", "e7be64f472973091a3e053487a148895");

                startActivity(intent);
            }
        });
    }
}
