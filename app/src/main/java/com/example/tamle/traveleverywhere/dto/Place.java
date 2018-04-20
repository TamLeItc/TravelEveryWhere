package com.example.tamle.traveleverywhere.dto;

/**
 * Created by tamle on 17/03/2018.
 */

public class Place {

    private String mMainTextPlace;
    private String mSecondTextPlace;
    private String mId;

    public Place(){}

    public Place(String mMainTextPlace, String mSecondTextPlace, String mId) {

        this.mMainTextPlace = mMainTextPlace;
        this.mSecondTextPlace = mSecondTextPlace;
        this.mId = mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    public String getMainTextPlace() {
        return mMainTextPlace;
    }

    public void setMainTextPlace(String mMainTextPlace) {
        this.mMainTextPlace = mMainTextPlace;
    }

    public String getSecondTextPlace() {
        return mSecondTextPlace;
    }

    public void setSecondTextPlace(String mSecondTextPlace) {
        this.mSecondTextPlace = mSecondTextPlace;
    }

    public String getId() {
        return mId;
    }




}
