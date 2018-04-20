package com.example.tamle.traveleverywhere.presenter.main;

import com.example.tamle.traveleverywhere.dto.ComplexPlace;

import java.util.ArrayList;

/**
 * Created by tamle on 07/04/2018.
 */

public interface ViewHandleMain {

    void OnCreateListSuccessful(ArrayList<ComplexPlace> arrayListDomestic, ArrayList<ComplexPlace> arrayListWorld);

    void OncreteListFailed();

}
