package com.example.tamle.traveleverywhere.view.weather;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.tamle.traveleverywhere.R;
import com.example.tamle.traveleverywhere.presenter.weather.PresenterWeather;
import com.example.tamle.traveleverywhere.presenter.weather.ViewHandleWeather;
import com.example.tamle.traveleverywhere.view.load.LoadFragment;

/**
 * Created by tamle on 10/04/2018.
 */

public class WeatherActivity extends AppCompatActivity implements ViewHandleWeather {

    public static PresenterWeather PRESENTER_WEATHER;

    private String mLocation;
    private String mCityName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        addLoadFragment();
        handleDataFromIntent();
    }

    private void addLoadFragment(){

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frl_weather, new LoadFragment());
        fragmentTransaction.commit();
    }

    private void handleDataFromIntent(){
        Intent intent = getIntent();
        this.mLocation = intent.getStringExtra("location");
        this.mCityName = intent.getStringExtra("city_name");

        this.PRESENTER_WEATHER = new PresenterWeather(this, this.mLocation);
        WeatherActivity.PRESENTER_WEATHER.loadData();
    }

    @Override
    public void OnHandleDataSuccessful() {

        WeatherFragment weatherFragment = new WeatherFragment();
        Bundle bundle = new Bundle();
        bundle.putString("city_name", this.mCityName);
        weatherFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frl_weather, weatherFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void OnHandleDataFailed(String error) {
        Log.d("WeatherActivity", error);
        Toast.makeText(this, "Có lỗi trong quá trình tải dữ liệu", Toast.LENGTH_SHORT);
        finish();
    }
}
