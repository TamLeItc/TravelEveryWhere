package com.example.tamle.traveleverywhere.view.weather;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tamle.traveleverywhere.R;
import com.example.tamle.traveleverywhere.dto.Weather;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by tamle on 26/08/2017.
 */

public class WeatherHourlyAdapter extends RecyclerView.Adapter<WeatherHourlyAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Weather> mWeatherArr;

    public WeatherHourlyAdapter(Context context, ArrayList<Weather> weatherArr) {
        this.mContext = context;
        this.mWeatherArr = weatherArr;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_weather_hourly, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Weather weather = mWeatherArr.get(position);

        holder.txvTime.setText(weather.getTime());
        holder.txvTemp.setText(weather.getTemp());
        Picasso.with(mContext).load("http://openweathermap.org/img/w/" + weather.getIcon() + ".png").into(holder.imvImage);



    }

    @Override
    public int getItemCount() {
        return mWeatherArr.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txvTemp;
        public ImageView imvImage;
        public TextView txvTime;

        public ViewHolder(View itemView) {
            super(itemView);
            
            this.txvTemp = (TextView) itemView.findViewById(R.id.txv_tempHourly);
            this.txvTime = (TextView) itemView.findViewById(R.id.txv_timeHourly);
            this.imvImage = (ImageView) itemView.findViewById(R.id.imv_statusHourly);

        }
    }

}
