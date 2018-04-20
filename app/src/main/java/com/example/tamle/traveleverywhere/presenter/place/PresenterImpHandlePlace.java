package com.example.tamle.traveleverywhere.presenter.place;

/**
 * Created by tamle on 09/04/2018.
 */

public interface PresenterImpHandlePlace {

    void OnLoadDataPlaceSuccessful(String location, String dataCityDetail, String dataRelatedPlaces);

    void OnLoadDataPlaceFailed(String error);

}
