package com.example.dlwls.myweather.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;

public class ImageViewTask extends AsyncTask<ImageView, Void, Bitmap> {
    private final Context context;
    private ImageView imageView;

    public ImageViewTask(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(ImageView... values) {
        this.imageView = values[0];
        String icon = (String)this.imageView.getTag();
        String urlStr = "http://openweathermap.org/img/w/" + icon + ".png";
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(20000);
            conn.setDoInput(true);
            conn.connect();

            return BitmapFactory.decodeStream(conn.getInputStream());
        } catch (Exception e){
            Log.d("OnImageViewTask", "Error");
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
        super.onPostExecute(bitmap);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
