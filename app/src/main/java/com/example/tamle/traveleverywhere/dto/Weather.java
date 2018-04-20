package com.example.tamle.traveleverywhere.dto;

/**
 * Created by tamle on 26/08/2017.
 */

public class Weather {

    private String mTime;
    private String mLongTime;
    private String mTemp;
    private String mTempMax;
    private String mTempMin;
    private String mIcon;
    private String mIconLarge;
    private String mStatus;
    private String mFeelsLike;
    private String mHumidity;
    private String mCloudCover;
    private String mWindSpeed;
    private String mUVIndex;
    private String mDewPoint;
    private String mPressure;

    public Weather(){}

    public Weather(String mTime, String longTime, String mTemp, String tempMax, String tempMin, String mIcon, String mIconMain,
                   String mStatus, String mFeelsLike, String mHumidity, String mCloudCover, String mwindSpeed, String mUIIndex,
                   String dewPoint, String pressure) {
        this.mTime = mTime;
        this.mTemp = mTemp;
        this.mIcon = mIcon;
        this.mIconLarge = mIconMain;
        this.mStatus = mStatus;
        this.mFeelsLike = mFeelsLike;
        this.mHumidity = mHumidity;
        this.mCloudCover = mCloudCover;
        this.mWindSpeed = mwindSpeed;
        this.mUVIndex = mUIIndex;
        this.mDewPoint = dewPoint;
        this.mPressure = pressure;
        this.mLongTime = longTime;
        this.mTempMax = tempMax;
        this.mTempMin = tempMin;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String mTime) {
        this.mTime = mTime;
    }

    public String getLongTime() {
        return mLongTime;
    }

    public void setLongTime(String mLongTime) {
        this.mLongTime = mLongTime;
    }

    public String getTemp() {
        return mTemp;
    }

    public void setTemp(String mTemp) {
        this.mTemp = mTemp;
    }

    public String getTempMax() {
        return mTempMax;
    }

    public void setTempMax(String mTempMax) {
        this.mTempMax = mTempMax;
    }

    public String getTempMin() {
        return mTempMin;
    }

    public void setTempMin(String mTempMin) {
        this.mTempMin = mTempMin;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String mIcon) {
        this.mIcon = mIcon;
    }

    public String getIconLarge() {
        return mIconLarge;
    }

    public void setIconLarge(String mIconMain) {
        this.mIconLarge = mIconMain;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public String getFeelsLike() {
        return mFeelsLike;
    }

    public void setFeelsLike(String mFeelsLike) {
        this.mFeelsLike = mFeelsLike;
    }

    public String getHumidity() {
        return mHumidity;
    }

    public void setHumidity(String mHumidity) {
        this.mHumidity = mHumidity;
    }

    public String getCloudCover() {
        return mCloudCover;
    }

    public void setCloudCover(String mCloudCover) {
        this.mCloudCover = mCloudCover;
    }

    public String getWindSpeed() {
        return mWindSpeed;
    }

    public void setWindSpeed(String mwindSpeed) {
        this.mWindSpeed = mwindSpeed;
    }

    public String getUVIndex() {
        return mUVIndex;
    }

    public void setUVIndex(String mUIIndex) {
        this.mUVIndex = mUIIndex;
    }

    public String getDewPoint() {
        return mDewPoint;
    }

    public void setDewPoint(String dewPoint) {
        this.mDewPoint = dewPoint;
    }

    public String getPressure() {
        return mPressure;
    }

    public void setPressure(String pressure) {
        this.mPressure = pressure;
    }
}
