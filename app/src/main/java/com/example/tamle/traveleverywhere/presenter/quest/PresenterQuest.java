package com.example.tamle.traveleverywhere.presenter.quest;

import com.example.tamle.traveleverywhere.dto.Place;
import com.example.tamle.traveleverywhere.model.ModelQuest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by tamle on 08/04/2018.
 */

public class PresenterQuest implements PresenterImpHandleQuest {

    private ViewHandleQuest mViewHandleQuest;
    private ModelQuest mModelQuest;

    private String data;

    public PresenterQuest(ViewHandleQuest viewHandleQuest){
        this.mViewHandleQuest = viewHandleQuest;
        this.mModelQuest = new ModelQuest(this);
    }

    public void handleQuest(String quest){
        this.mModelQuest.loadData(quest);
    }

    @Override
    public void OnLoadDataQuestSuccessful(String data) {

        this.data = data;
        mViewHandleQuest.OnHandleDataSuccessful();

    }

    @Override
    public void OnLoadDataQuestFailed(String error) {
        mViewHandleQuest.OnHandleDataFailed(error.toString());
    }

    public ArrayList<Place> getArrListPlace(){

        ArrayList<Place> arrListPlace = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(data);

            JSONArray jsonArrayPredictions = jsonObject.getJSONArray("predictions");
            for(int i = 0; i < jsonArrayPredictions.length(); i++){

                JSONObject item = jsonArrayPredictions.getJSONObject(i);

                Place place = new Place();

                String description = item.getString("description");
                String[] arrDes = description.split(",");
                if(arrDes.length == 1){
                    place.setMainTextPlace(arrDes[0]);
                    place.setSecondTextPlace(arrDes[0]);
                }
                if(arrDes.length == 2){
                    place.setMainTextPlace(arrDes[0]);
                    place.setSecondTextPlace(arrDes[1]);
                }
                else if(arrDes.length > 2){

                    String mainTextPlace = arrDes[0];
                    for(int j = 1; j < arrDes.length - 2; j++){
                        mainTextPlace += ", " + arrDes[j];
                    }
                    place.setMainTextPlace(mainTextPlace);

                    String secondTextPlace = arrDes[arrDes.length - 2] + ", " + arrDes[arrDes.length - 1];
                    place.setSecondTextPlace(secondTextPlace);
                }

                place.setId(item.getString("place_id"));

                arrListPlace.add(place);
            }
        } catch (JSONException e) {
            mViewHandleQuest.OnHandleDataFailed(e.toString());
            e.printStackTrace();
        }

        return arrListPlace;
    }

}
