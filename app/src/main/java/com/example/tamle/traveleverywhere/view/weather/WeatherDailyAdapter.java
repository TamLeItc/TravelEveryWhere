package com.example.tamle.traveleverywhere.view.weather;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tamle.traveleverywhere.R;
import com.example.tamle.traveleverywhere.dto.Weather;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by tamle on 29/08/2017.
 */

public class WeatherDailyAdapter extends RecyclerView.Adapter<WeatherDailyAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<Weather> mWeathers;

    public WeatherDailyAdapter(Context context, ArrayList<Weather> weathers) {
        this.mContext = context;
        this.mWeathers = weathers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_weather_daily, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Weather weather = mWeathers.get(position);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE dd-MM");
        long timeLong = Long.valueOf(weather.getLongTime());
        String dateFormat = simpleDateFormat.format(timeLong * 1000);
        String[] dates = dateFormat.split(" ");
        holder.txvDayOfWeek.setText(dates[0]);
        holder.txvDay.setText(dates[1]);

        holder.txvTempMin.setText(weather.getTempMin());
        holder.txvTempMax.setText(weather.getTempMax());

        holder.txvHumilityAndWindSpeed.setText(weather.getHumidity() + " | " + weather.getWindSpeed());

        Picasso.with(mContext).load("http://openweathermap.org/img/w/" + weather.getIcon() + ".png").into(holder.imvDailyIcon);

    }

    @Override
    public int getItemCount() {
        return mWeathers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txvDayOfWeek;
        public TextView txvDay;
        public TextView txvTempMin;
        public TextView txvTempMax;
        public TextView txvHumilityAndWindSpeed;
        public ImageView imvDailyIcon;

        public ViewHolder(View itemView) {
            super(itemView);

            this.txvDayOfWeek = (TextView) itemView.findViewById(R.id.txv_dayOfWeek);
            this.txvDay = (TextView) itemView.findViewById(R.id.txv_day);
            this.txvTempMin = (TextView) itemView.findViewById(R.id.txv_tempMin);
            this.txvTempMax = (TextView) itemView.findViewById(R.id.txv_tempMax);
            this.txvHumilityAndWindSpeed = (TextView) itemView.findViewById(R.id.txv_humidityAndWindSpeed);
            this.imvDailyIcon = (ImageView) itemView.findViewById(R.id.imv_dailyIcon);

        }
    }
}
