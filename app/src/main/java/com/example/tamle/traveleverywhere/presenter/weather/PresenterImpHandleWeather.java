package com.example.tamle.traveleverywhere.presenter.weather;

/**
 * Created by tamle on 09/04/2018.
 */

public interface PresenterImpHandleWeather {

    void OnLoadDataWeatherSuccessful(String data);

    void OnLoadDataWeatherFailed(String error);

}
