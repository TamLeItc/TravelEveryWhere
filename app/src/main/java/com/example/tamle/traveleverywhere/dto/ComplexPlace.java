package com.example.tamle.traveleverywhere.dto;

/**
 * Created by tamle on 26/02/2018.
 */

public class ComplexPlace {
    private int mImage;
    private String mPlaceName;
    private String mSlogan;
    private String mId;

    public ComplexPlace(){}

    public ComplexPlace(String id, int mImage, String mPlaceName, String mSlogan) {
        this.mId = id;
        this.mImage = mImage;
        this.mPlaceName = mPlaceName;
        this.mSlogan = mSlogan;
    }

    public int getImage() {
        return mImage;
    }

    public void setImage(int mImage) {
        this.mImage = mImage;
    }

    public String getPlaceName() {
        return mPlaceName;
    }

    public void setPlaceName(String mPlaceName) {
        this.mPlaceName = mPlaceName;
    }

    public String getSlogan() {
        return mSlogan;
    }

    public void setSlogan(String mSlogan) {
        this.mSlogan = mSlogan;
    }

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }
}
