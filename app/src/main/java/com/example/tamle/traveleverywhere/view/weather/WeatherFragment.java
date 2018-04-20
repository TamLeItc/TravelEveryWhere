package com.example.tamle.traveleverywhere.view.weather;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tamle.traveleverywhere.R;
import com.example.tamle.traveleverywhere.dto.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by tamle on 29/03/2018.
 */

public class WeatherFragment extends Fragment {

    private View mViewContent;

    private TextView mTxvCityName, mTxvTime, mTxvTemp, mTxvStatus, mTxvHumidity, mTxvCloudCover, mTxvWindSpeed, mTxvUV, mTxvDewPoint,
            mTxvPressure, mTxvFeelsLike;
    private RecyclerView mRecyclerViewWeatherHourly, mRecyclerViewWeatherDaily;

    private WeatherHourlyAdapter mWeatherHourlyAdapter;
    private WeatherDailyAdapter mWeatherDailyAdapter;

    private ArrayList<Weather> mArrListWeatherHourly = new ArrayList<>();
    private ArrayList<Weather> mArrListWeatherDaily = new ArrayList<>();

    private String mCityName, mCurrentTime;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        this.mViewContent = inflater.inflate(R.layout.fragment_weather, container, false);
        return mViewContent;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        unitViews();

        getDataFromActivity();

        handleData();

    }

    private void unitViews(){
        this.mTxvCityName = (TextView) mViewContent.findViewById(R.id.txv_cityName);
        this.mTxvTime = (TextView) mViewContent.findViewById(R.id.txv_time);
        this.mTxvTemp = (TextView) mViewContent.findViewById(R.id.txv_temp);
        this.mTxvStatus = (TextView) mViewContent.findViewById(R.id.txv_status);
        this.mTxvHumidity = (TextView) mViewContent.findViewById(R.id.txv_humidity);
        this.mTxvCloudCover = (TextView) mViewContent.findViewById(R.id.txv_cloudCover);
        this.mTxvWindSpeed = (TextView) mViewContent.findViewById(R.id.txv_windSpeed);
        this.mTxvUV = (TextView) mViewContent.findViewById(R.id.txv_uv);
        this.mTxvDewPoint = (TextView) mViewContent.findViewById(R.id.txv_dewPoint);
        this.mTxvPressure = (TextView) mViewContent.findViewById(R.id.txv_pressure);
        this.mTxvFeelsLike = (TextView) mViewContent.findViewById(R.id.txv_feelsLike);
        this.mRecyclerViewWeatherHourly = (RecyclerView) mViewContent.findViewById(R.id.recycler_view_weatherHourly);
        this.mRecyclerViewWeatherDaily = (RecyclerView) mViewContent.findViewById(R.id.recycler_view_weatherDaily);
    }

    private void getDataFromActivity(){
        Bundle bundle = getArguments();
        this.mCityName = bundle.getString("city_name");
    }

    private void handleData(){
        this.mArrListWeatherDaily = WeatherActivity.PRESENTER_WEATHER.getArrListWeatherDailyArr();
        this.mArrListWeatherHourly = WeatherActivity.PRESENTER_WEATHER.getArrListWeatherHourlyArr();

        setCurrentTimeAndCityName();
        loadWeatherDataCurrently();
        loadWeatherDataHourly();
        loadWeatherDataDaily();
    }

    private void setCurrentTimeAndCityName(){
        this.mCurrentTime = WeatherActivity.PRESENTER_WEATHER.getCurrentTime();

        //City name
        this.mTxvCityName.setText(this.mCityName);
    }

    //Load thời tiết hiện tại lên màn hình
    private void loadWeatherDataCurrently() {

        Weather weather = this.mArrListWeatherHourly.get(2);

        //Time
        long timeLong = Long.valueOf(this.mCurrentTime);
        String format = "EE MM-dd hh:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String dataFormat = simpleDateFormat.format(timeLong * 1000);
        this.mTxvTime.setText(dataFormat);

        //Temperature
        this.mTxvTemp.setText(weather.getTemp());

        //Status
        this.mTxvStatus.setText(weather.getStatus());

        //Feel like
        this.mTxvFeelsLike.setText(weather.getFeelsLike());

        //Dew point
        Double dewPoint = Double.parseDouble(weather.getDewPoint());
        this.mTxvDewPoint.setText(String.valueOf(dewPoint.intValue()));

        //Humidity
        this.mTxvHumidity.setText(weather.getHumidity());

        //WindSpeed
        this.mTxvWindSpeed.setText(weather.getWindSpeed());

        //Clouds Cover
        this.mTxvCloudCover.setText(weather.getCloudCover());

        //Pressure
        this.mTxvPressure.setText(weather.getPressure());

        //UV Index
        this.mTxvUV.setText(weather.getUVIndex());

    }

    //Load thông tin thời tiết từng giờ lên màn hình
    private void loadWeatherDataHourly(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        this.mRecyclerViewWeatherHourly.setLayoutManager(linearLayoutManager);

        //Sao chép từ mảng WEATHER_HOURLY_ARR lấy ra 24 giờ tiếp theo tính thừ thời điểm hiện tại
        ArrayList<Weather> weathers = new ArrayList<>();
        for(int i = 1; i < this.mArrListWeatherHourly.size() && i <= 24; i++){
            weathers.add(this.mArrListWeatherHourly.get(i));
        }

        this.mWeatherHourlyAdapter = new WeatherHourlyAdapter(getActivity(), weathers);
        this.mRecyclerViewWeatherHourly.setAdapter(mWeatherHourlyAdapter);

    }

    //Load thông tin thời tiết từng ngày lên màn hình
    private void loadWeatherDataDaily(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        this.mRecyclerViewWeatherDaily.setLayoutManager(linearLayoutManager);

        this.mWeatherDailyAdapter = new WeatherDailyAdapter(getActivity(), this.mArrListWeatherDaily);
        this.mRecyclerViewWeatherDaily.setAdapter(mWeatherDailyAdapter);

    }
}
