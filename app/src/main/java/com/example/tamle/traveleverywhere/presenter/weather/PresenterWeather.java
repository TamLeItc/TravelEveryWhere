package com.example.tamle.traveleverywhere.presenter.weather;

import com.example.tamle.traveleverywhere.dto.Weather;
import com.example.tamle.traveleverywhere.model.ModelWeather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by tamle on 09/04/2018.
 */

public class PresenterWeather implements PresenterImpHandleWeather{

    private ViewHandleWeather mViewHandleWeather;
    private String mLocation;

    private String mDataWeather;

    private ModelWeather mModelWeather;

    public PresenterWeather(ViewHandleWeather viewHandleWeather, String location){
        this.mViewHandleWeather = viewHandleWeather;
        this.mLocation = location;

        this.mModelWeather = new ModelWeather(this, location);
    }

    public void loadData(){
        this.mModelWeather.loadData(this.mLocation);
    }

    @Override
    public void OnLoadDataWeatherSuccessful(String data) {
        this.mDataWeather = data;
        this.mViewHandleWeather.OnHandleDataSuccessful();
    }

    @Override
    public void OnLoadDataWeatherFailed(String error) {
        this.mViewHandleWeather.OnHandleDataFailed(error);
    }

    public String getCurrentTime(){

        String currentTime = "";
        try {
            JSONObject jsonObject = new JSONObject(this.mDataWeather);
            //Get current time
            JSONObject currently = jsonObject.getJSONObject("currently");
            currentTime = currently.getString("time");

        } catch (JSONException e) {
            this.mViewHandleWeather.OnHandleDataFailed(e.toString());
        }

        return currentTime;
    }

    public ArrayList<Weather> getArrListWeatherHourlyArr(){

        ArrayList<Weather> weatherHourlyArr = new ArrayList<>();

        //Load data weather by hourly
        try {

            JSONObject jsonObject = new JSONObject(this.mDataWeather);
            JSONObject jsonObjectHourly = jsonObject.getJSONObject("hourly");
            JSONArray jsonHourlyArr = jsonObjectHourly.getJSONArray("data");


            for(int i = 0; i < jsonHourlyArr.length() && i <= 25; i++){

                JSONObject item = jsonHourlyArr.getJSONObject(i);
                Weather weather = new Weather();

                //Time
                String timeText = item.getString("time");
                weather.setLongTime(timeText);
                long timeLong = Long.valueOf(timeText);
                String format = "hh:a";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
                String dataFormat = simpleDateFormat.format(timeLong * 1000);
                String strTime[] = dataFormat.split(":");
                weather.setTime(strTime[0] + " " + strTime[1]);
                int hour = Integer.valueOf(strTime[0]);
                String redoute = strTime[1];

                //Temperature
                int temp = getCelsiusByString(item.getString("temperature"));
                String tempText = String.valueOf(temp) + "°C";
                weather.setTemp(tempText);

                //Status
                weather.setStatus(item.getString("summary"));

                //Feel like
                int feelLike = getCelsiusByString(item.getString("apparentTemperature"));
                String feelLikeText = "Feels like " + feelLike + "°C";
                weather.setFeelsLike(feelLikeText);

                //Dew point
                Double dewPoint = Double.parseDouble(item.getString("dewPoint"));
                weather.setDewPoint(String.valueOf(dewPoint.intValue()));

                //Humidity
                Double humidity = Double.parseDouble(item.getString("humidity")) * 100;
                weather.setHumidity(humidity.intValue()+ "%");


                //WindSpeed
                Double windSpeed = Double.parseDouble(item.getString("windSpeed"));
                String windSpeedText = windSpeed + "m/s";
                weather.setWindSpeed(windSpeedText);

                //Clouds Cover
                Double cloudCover = Double.parseDouble(item.getString("cloudCover")) * 100;
                weather.setCloudCover(cloudCover.intValue() + "%");

                //Pressure
                String pressure = getCelsiusByString(item.getString("pressure")) + "mb";
                weather.setPressure(pressure);

                //UV Index
                String uv = item.getString("uvIndex");
                weather.setUVIndex(uv);

                //Set icon
                String icon = item.getString("icon");
                weather.setIcon(getIconImage(icon));
                weather.setIconLarge(getIconMain(icon));

                weatherHourlyArr.add(weather);
            }

        } catch (JSONException e) {

            this.mViewHandleWeather.OnHandleDataFailed(e.toString());
            e.printStackTrace();
        }

        return weatherHourlyArr;
    }


    public ArrayList<Weather> getArrListWeatherDailyArr(){

        ArrayList<Weather> weatherHourlyArr = new ArrayList<>();

        //Load data weather by hourly
        try {

            JSONObject jsonObject = new JSONObject(this.mDataWeather);
            JSONObject jsonObjectDaily = jsonObject.getJSONObject("daily");
            JSONArray jsonDailyArr = jsonObjectDaily.getJSONArray("data");


            for(int i = 2; i < jsonDailyArr.length() && i <= 25; i++){
                JSONObject item = jsonDailyArr.getJSONObject(i);
                Weather weather = new Weather();

                //Time
                String timeText = item.getString("time");
                weather.setLongTime(timeText);
                long timeLong = Long.valueOf(timeText);
                String format = "hh:a";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
                String dataFormat = simpleDateFormat.format(timeLong * 1000);
                String strTime[] = dataFormat.split(":");
                weather.setTime(strTime[0] + " " + strTime[1]);
                int hour = Integer.valueOf(strTime[0]);
                String redoute = strTime[1];

                //Temperature
                int tempMin = getCelsiusByString(item.getString("temperatureMin"));
                String tempMinText = String.valueOf(tempMin) + "°C";
                weather.setTempMin(tempMinText);
                int tempMax = getCelsiusByString(item.getString("temperatureMax"));
                String tempMaxText = String.valueOf(tempMax) + "°C";
                weather.setTempMax(tempMaxText);

                //Status
                weather.setStatus(item.getString("summary"));

                //Dew point
                Double dewPoint = Double.parseDouble(item.getString("dewPoint"));
                weather.setDewPoint(String.valueOf(dewPoint.intValue()));

                //Humidity
                Double humidity = Double.parseDouble(item.getString("humidity")) * 100;
                weather.setHumidity(humidity.intValue() + "%");


                //WindSpeed
                Double windSpeed = Double.parseDouble(item.getString("windSpeed"));
                String windSpeedText = windSpeed + "m/s";
                weather.setWindSpeed(windSpeedText);

                //Clouds Cover
                Double cloudCover = Double.parseDouble(item.getString("cloudCover")) * 100;
                weather.setCloudCover(cloudCover.intValue() + "%");

                //Pressure
                String pressure = getCelsiusByString(item.getString("pressure")) + "mb";
                weather.setPressure(pressure);

                //UV Index
                String uv = item.getString("uvIndex");
                weather.setUVIndex(uv);

                //Set icon
                String icon = item.getString("icon");
                weather.setIcon(getIconImage(icon));
                weather.setIconLarge(getIconMain(icon));


                weatherHourlyArr.add(weather);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return weatherHourlyArr;
    }

    //Phân tích text, trả về 1 icon để lấy hình ảnh từ "http://www.iconsdb.com"
    private String getIcon(String text, String redoute){
        switch (text){
            case "clear-day":
                return "sun";
            case "clear_night":
                return "mon-4";
            case "partly-cloudy-day":
                return "partly-cloudy-day";
            case "partly-cloudy-night":
                return "partly-cloudy-night";
            case "cloudy":
                return "clouds";
            case "rain":
                return "rain";
            case "sleet":
                return "sleet";
            case "snow":
                return "snow";
            case "wind":
                return  "wind-rose";
            case "fog":
                if(redoute.equals("AM")){
                    return "fog-day";
                }
                else{
                    return "fog_night";
                }

        }
        return "";
    }

    //Phân tích text, trả về 1 icon để lấy hình ảnh từ "openweathermap"
    private String getIconImage(String text){
        switch (text){
            case "clear-day":
                return "01d";
            case "clear-night":
                return "01n";
            case "partly-cloudy-day":
                return "02d";
            case "partly-cloudy-night":
                return "02n";
            case "cloudy":
                return "03d";
            case "rain":
                return "09d";
            case "sleet":
                return "09d";
            case "snow":
                return "09d";
            case "wind":
                return  "03d";
            case "fog":
                return "50d";

        }
        return "";
    }

    //Truyền vào một text. Phân tích trả về tên một icon để lấy icon đó về từ "https://cdn2.iconfinder.com"
    public String getIconMain(String text){
        switch (text){
            case "clear-day":
                return "sun";
            case "clear_night":
                return "mon";
            case "partly-cloudy-day":
                return "partly_cloudy";
            case "partly-cloudy-night":
                return "moon_cloudy";
            case "cloudy":
                return "clouds";
            case "rain":
                return "rain";
            case "sleet":
                return "rain_snow";
            case "snow":
                return "snow";
            case "wind":
                return  "windy";
            case "fog":
                return "fog";

        }
        return "";
    }

    private int getCelsiusByString(String sTempF){
        Double tempF = Double.parseDouble(sTempF);
        Double tempC = (tempF - 32) / 1.8;
        return  tempC.intValue();
    }

}
