package com.example.dlwls.myweather.adapter;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dlwls.myweather.R;
import com.example.dlwls.myweather.model.Forecast;
import com.example.dlwls.myweather.service.ImageViewTask;
import com.example.dlwls.myweather.util.ConvertDate;

import java.util.ArrayList;
import java.util.List;

public class WeatherAdapter extends BaseAdapter {
   LayoutInflater inflater;
   List<Forecast> items;
   List<ImageView> images;
   Context context;

   public WeatherAdapter(Context context, List<Forecast> items){
       this.context = context;
       this.items = items;
       this.inflater = LayoutInflater.from(context);
       this.images = new ArrayList<ImageView>();
   }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       View layout = view;
       Items weatherItems = null;

       if(layout == null) {
           layout = inflater.inflate(R.layout.weather_item, null);
           weatherItems = new Items();

           weatherItems.tvDate = (TextView) layout.findViewById(R.id.tvDate);
           weatherItems.tvTemp = (TextView) layout.findViewById(R.id.tvTemp);
           weatherItems.tvDesc = (TextView) layout.findViewById(R.id.tvDesc);
           weatherItems.imageView = (ImageView)layout.findViewById(R.id.imgIcon);
//           weatherItems.imageView = (ImageView) layout.findViewById(R.id.imgIcon);
//           weatherItems.imageView.setTag(this.items.get(i).weather.icon);
//           ImageViewTask imageViewTask = new ImageViewTask(this.context);
//           imageViewTask.execute(weatherItems.imageView);
//           if(imageViewTask.getStatus() == AsyncTask.Status.FINISHED){
//
//           }
           layout.setTag(weatherItems);
       } else {
           weatherItems = (Items)layout.getTag();
       }

        weatherItems.tvDate.setText(ConvertDate.CONVERT_DATE(this.items.get(i).dt));
        weatherItems.tvTemp.setText(this.items.get(i).main.temp + "\u2103");
        weatherItems.tvDesc.setText(this.items.get(i).weather.description);
        weatherItems.imageView.setTag(this.items.get(i).weather.icon);

        //execute Thread for getting image;
        if(this.images.size() <= i) {
            Log.d("onAdapter", "images Size : " + this.images.size());
            ImageViewTask imageViewTask = new ImageViewTask(context);
            imageViewTask.execute(weatherItems.imageView);
            if(imageViewTask.getStatus() == AsyncTask.Status.FINISHED){
                this.images.add(weatherItems.imageView);
            }
        } else {
            weatherItems.imageView = images.get(i);
        }
       return layout;
    }

    class Items {
        TextView tvDate;
        TextView tvTemp;
        TextView tvDesc;
        ImageView imageView;
    }
}
