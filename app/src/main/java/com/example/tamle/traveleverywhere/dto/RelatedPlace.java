package com.example.tamle.traveleverywhere.dto;

/**
 * Created by tamle on 03/03/2018.
 */

public class RelatedPlace {

    private String mName;
    private String mPhotoReference;
    private String mVicinity;
    private int mRating;

    public RelatedPlace() {
    }

    public RelatedPlace(String mName, String mPhoto_reference, String mVicinity, int mRating) {
        this.mName = mName;
        this.mPhotoReference = mPhoto_reference;
        this.mVicinity = mVicinity;
        this.mRating = mRating;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getPhotoReference() {
        return mPhotoReference;
    }

    public void setPhotoReference(String mPhoto_reference) {
        this.mPhotoReference = mPhoto_reference;
    }

    public String getVicinity() {
        return mVicinity;
    }

    public void setVicinity(String mVicinity) {
        this.mVicinity = mVicinity;
    }

    public int getRating() {
        return mRating;
    }

    public void setmRating(int mRating) {
        this.mRating = mRating;
    }
}
