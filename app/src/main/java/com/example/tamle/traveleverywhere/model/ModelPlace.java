package com.example.tamle.traveleverywhere.model;

import android.os.AsyncTask;

import com.example.tamle.traveleverywhere.presenter.place.PresenterImpHandlePlace;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by tamle on 09/04/2018.
 */

public class ModelPlace {

    private PresenterImpHandlePlace mPresenterImpHandlePlace;
    private String mLocation = "";
    private String mDataDCityDetails;

    public ModelPlace(PresenterImpHandlePlace presenterImpHandlePlace){
        this.mPresenterImpHandlePlace = presenterImpHandlePlace;
    }



    public void loadData(String placeId){

        String urlCityDetails = "https://maps.googleapis.com/maps/api/place/details/json?placeid=" +
                placeId + "&key=AIzaSyDBLLJuXzJ1Jf7rQYSY1IjQUMCtjaVb31Y";

        new ReadDataCityDetails().execute(urlCityDetails);

    }

    public class ReadDataCityDetails extends AsyncTask<String, Void, String>{

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

                return content.toString();


            } catch (MalformedURLException e) {

                mPresenterImpHandlePlace.OnLoadDataPlaceFailed(e.toString());
                e.printStackTrace();
            } catch (IOException e) {

                mPresenterImpHandlePlace.OnLoadDataPlaceFailed(e.toString());
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);

            mDataDCityDetails = s;


            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONObject jsonObjectResult = jsonObject.getJSONObject("result");
                JSONObject jsonObjectGeometry = jsonObjectResult.getJSONObject("geometry");
                JSONObject jsonObjectLocation = jsonObjectGeometry.getJSONObject("location");

                mLocation = jsonObjectLocation.getString("lat") + "," + jsonObjectLocation.getString("lng");

                String urlDataRelatedPlaces = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                        "location=" + mLocation +"&rankby=distance&types=" +
                        "restaurant&key=AIzaSyDBLLJuXzJ1Jf7rQYSY1IjQUMCtjaVb31Y";

                new ReadDataRelatedPlaces().execute(urlDataRelatedPlaces);

            } catch (JSONException e) {
                mPresenterImpHandlePlace.OnLoadDataPlaceFailed(e.toString());
                e.printStackTrace();
            }


        }
    }

    public class ReadDataRelatedPlaces extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {

            StringBuilder content = new StringBuilder();
            try {

                URL url = new URL(params[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line = "";
                while ((line = bufferedReader.readLine()) != null){
                    content.append(line);
                }

                return content.toString();


            } catch (MalformedURLException e) {
                mPresenterImpHandlePlace.OnLoadDataPlaceFailed(e.toString());
                e.printStackTrace();
            } catch (IOException e) {
                mPresenterImpHandlePlace.OnLoadDataPlaceFailed(e.toString());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mPresenterImpHandlePlace.OnLoadDataPlaceSuccessful(mLocation, mDataDCityDetails, s);

        }
    }

}
