package com.example.tamle.traveleverywhere.presenter.place;

import android.util.Log;

import com.example.tamle.traveleverywhere.dto.Place;
import com.example.tamle.traveleverywhere.dto.RelatedPlace;
import com.example.tamle.traveleverywhere.model.ModelPlace;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by tamle on 09/04/2018.
 */

public class PresenterPlace implements PresenterImpHandlePlace {

    private ViewHandlePlace mViewHandlePlace;

    private String mDataCityDetail;
    private String mDataRelatedPlaces;
    private String mPlaceId;
    private String mLocation;

    private ModelPlace mModelPlace;

    public PresenterPlace(ViewHandlePlace viewHandlePlace, String placeId){
        this.mViewHandlePlace = viewHandlePlace;
        this.mPlaceId = placeId;
        this.mModelPlace = new ModelPlace(this);

        this.mModelPlace.loadData(this.mPlaceId);
    }

    public String getPlaceId() {
        return mPlaceId;
    }

    public String getLocation() {
        return mLocation;
    }

    @Override
    public void OnLoadDataPlaceSuccessful(String location, String dataCityDetail, String dataRelatedPlaces) {

        this.mLocation = location;
        this.mDataCityDetail = dataCityDetail;
        this.mDataRelatedPlaces = dataRelatedPlaces;

        this.mViewHandlePlace.OnHandleDataSuccessful();
    }

    @Override
    public void OnLoadDataPlaceFailed(String error) {
        this.mViewHandlePlace.OnHandleDataFailed(error.toString());
    }

    public String getPlaceName(){

        String placeName = "";
        try {
            JSONObject jsonObject = new JSONObject(this.mDataCityDetail);
            JSONObject jsonObjectResult = jsonObject.getJSONObject("result");

            JSONArray jsonArrayAddressComponent = jsonObjectResult.getJSONArray("address_components");

            //Get name of this place
            if(jsonArrayAddressComponent.length() >= 1){
                JSONObject jsonObjectCityName = jsonArrayAddressComponent.getJSONObject(0);

                placeName = jsonObjectCityName.getString("long_name");
            }

        } catch (JSONException e) {
            this.mViewHandlePlace.OnHandleDataFailed(e.toString());
            e.printStackTrace();
        }

        return placeName;
    }

    public ArrayList<String> getArrListImage (){

        ArrayList<String> arrListImage = new ArrayList<>();
        try {

            JSONObject jsonObject = new JSONObject(this.mDataCityDetail);
            JSONObject jsonObjectResult = jsonObject.getJSONObject("result");

            JSONArray jsonArrayAddressComponent = jsonObjectResult.getJSONArray("address_components");

            //Get photo of this place
            JSONArray jsonArrayPhotos = jsonObjectResult.getJSONArray("photos");
            for(int i = 0; i < jsonArrayPhotos.length() && i <= 8; i++){
                JSONObject jsonObjectPhoto = jsonArrayPhotos.getJSONObject(i);
                arrListImage.add(jsonObjectPhoto.getString("photo_reference"));
            }



        } catch (JSONException e) {

            this.mViewHandlePlace.OnHandleDataFailed(e.toString());

            e.printStackTrace();
        }

        return arrListImage;
    }

    public ArrayList<RelatedPlace> getArrListRelatedPlaces() {

        ArrayList<RelatedPlace> mArrListRelatedPlace = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(this.mDataRelatedPlaces);
            JSONArray jsonArray = jsonObject.getJSONArray("results");

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObjectPlace = jsonArray.getJSONObject(i);

                RelatedPlace relatedPlace = new RelatedPlace();

                String name = jsonObjectPlace.getString("name");
                String[] shortName = name.split(",");
                relatedPlace.setName(shortName[0]);

                relatedPlace.setVicinity(jsonObjectPlace.getString("vicinity"));

                try {
                    JSONArray jsonArrayPhotos = jsonObjectPlace.getJSONArray("photos");
                    JSONObject jsonObjectPhoto = jsonArrayPhotos.getJSONObject(0);
                    relatedPlace.setPhotoReference(jsonObjectPhoto.getString("photo_reference"));
                } catch (JSONException e) {
                    continue;
                }

                mArrListRelatedPlace.add(relatedPlace);
            }
        } catch (JSONException e) {

            this.mViewHandlePlace.OnHandleDataFailed(e.toString());
            e.printStackTrace();
        }

        return mArrListRelatedPlace;
    }
}
