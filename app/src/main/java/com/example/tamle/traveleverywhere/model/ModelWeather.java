package com.example.tamle.traveleverywhere.model;

import android.os.AsyncTask;

import com.example.tamle.traveleverywhere.presenter.weather.PresenterImpHandleWeather;
import com.example.tamle.traveleverywhere.presenter.weather.PresenterWeather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by tamle on 09/04/2018.
 */

public class ModelWeather {

    private PresenterImpHandleWeather mPresenterImpHandleWeather;

    public ModelWeather(PresenterWeather presenterWeather, String location){
        this.mPresenterImpHandleWeather = presenterWeather;

        loadData(location);
    }

    public void loadData(String location){
        String urlGetWeather = "https://api.darksky.net/forecast/46773b75da8367a5cce8dc2f844ff26a/" + location;
        new ReadDataWeather().execute(urlGetWeather);
    }

    public class ReadDataWeather extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {

            StringBuilder content = new StringBuilder();
            try {

                URL url = new URL(params[0]);

                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line = "";
                while((line = bufferedReader.readLine()) != null){
                    content.append(line);
                }

            } catch (MalformedURLException e) {
                mPresenterImpHandleWeather.OnLoadDataWeatherFailed(e.toString());
                e.printStackTrace();
            } catch (IOException e) {
                mPresenterImpHandleWeather.OnLoadDataWeatherFailed(e.toString());
                e.printStackTrace();
            }

            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {

            mPresenterImpHandleWeather.OnLoadDataWeatherSuccessful(s);

            super.onPostExecute(s);
        }
    }

}