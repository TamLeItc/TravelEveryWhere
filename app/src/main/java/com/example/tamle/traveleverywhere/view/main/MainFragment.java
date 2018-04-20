package com.example.tamle.traveleverywhere.view.main;

import com.example.tamle.traveleverywhere.R;
import com.example.tamle.traveleverywhere.dto.ComplexPlace;
import com.example.tamle.traveleverywhere.presenter.main.ViewHandleMain;
import com.example.tamle.traveleverywhere.presenter.main.PresenterMain;
import com.example.tamle.traveleverywhere.view.quest.QuestActivity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by tamle on 18/03/2018.
 */

public class MainFragment extends Fragment implements ViewHandleMain {

    private View mViewContent;

    private EditText mEdtQuestion;
    private org.lucasr.twowayview.TwoWayView mTwvDomesticPlace;
    private org.lucasr.twowayview.TwoWayView mTxvWorldPlace;

    private final int REQUEST_CODE = 1230;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mViewContent = inflater.inflate(R.layout.fragment_main, container, false);

        return mViewContent;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        unitViews();

        createListComplexPlace();

    }

    private void unitViews(){
        this.mEdtQuestion = mViewContent.findViewById(R.id.edt_question);
        this.mTwvDomesticPlace = mViewContent.findViewById(R.id.twv_domestic_place);
        this.mTxvWorldPlace = mViewContent.findViewById(R.id.twv_world_place);

        this.mEdtQuestion.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                String infor = mEdtQuestion.getText().toString();
                String input = infor.trim();

                if(input.length() >= 1){

                    mEdtQuestion.setText("");

                    Intent intent = new Intent(getActivity(), QuestActivity.class);
                    intent.putExtra("quest", input);

                    startActivity(intent);

                }

                return false;
            }
        });
    }

    private void createListComplexPlace(){
        PresenterMain presenterMain = new PresenterMain(this);
        presenterMain.createListComplexPlace();
    }

    @Override
    public void OnCreateListSuccessful(ArrayList<ComplexPlace> arrayListDomestic, ArrayList<ComplexPlace> arrayListWorld) {

        ComplexPlaceAdapter domesticComplexPlaceAdapter = new ComplexPlaceAdapter(getActivity(), arrayListDomestic);
        this.mTwvDomesticPlace.setAdapter(domesticComplexPlaceAdapter);

        ComplexPlaceAdapter worldComplexPlaceAdapter = new ComplexPlaceAdapter(getActivity(), arrayListWorld);
        this.mTxvWorldPlace.setAdapter(worldComplexPlaceAdapter);
    }

    @Override
    public void OncreteListFailed() {
        Log.d("MainActivity", "Error");
    }
}
