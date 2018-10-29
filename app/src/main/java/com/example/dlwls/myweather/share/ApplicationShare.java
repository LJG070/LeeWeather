package com.example.dlwls.myweather.share;

import android.app.Application;
import android.util.Log;

public class ApplicationShare extends Application{
    private static final String TAG = "MYAPP-APPLICATION";

    private String apiKey = null;

    @Override
    public void onCreate() {
        super.onCreate();
        apiKey = null;
        Log.d(TAG, "----Application's onCreate() in---");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "----Application's onTerminate() out---");
    }

    public static String getTAG() {
        return TAG;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
