package com.example.tamle.traveleverywhere.view.quest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tamle.traveleverywhere.R;
import com.example.tamle.traveleverywhere.dto.Place;
import com.example.tamle.traveleverywhere.model.ModelQuest;
import com.example.tamle.traveleverywhere.presenter.quest.PresenterQuest;
import com.example.tamle.traveleverywhere.presenter.quest.ViewHandleQuest;
import com.example.tamle.traveleverywhere.view.place.PlaceActivity;

import java.util.ArrayList;

/**
 * Created by tamle on 08/04/2018.
 */

public class QuestActivity extends AppCompatActivity implements ViewHandleQuest, View.OnClickListener {

    private PresenterQuest mPresenterQuest;

    private EditText mEdtSearch;
    private ListView mLsvPlace;
    private ImageView mImvClear;
    private Button mBtnCancel;

    private PlaceAdapter mPlaceAdapter;
    private ArrayList<Place> mArrListPlace;

    private int mCountKeyPress = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest);

        unitViews();
        handleData();

        this.mPresenterQuest = new PresenterQuest(this);
        handleDataFromIntent();

    }

    private void unitViews(){
        this.mEdtSearch = findViewById(R.id.edt_search);
        this.mLsvPlace = findViewById(R.id.lsv_places);
        this.mImvClear = findViewById(R.id.imv_clear);
        this.mBtnCancel = findViewById(R.id.btn_cancel);

        this.mImvClear.setOnClickListener(this);
        this.mBtnCancel.setOnClickListener(this);
        this.mEdtSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(mCountKeyPress == 1){
                    mCountKeyPress = 2;
                }
                else if(mCountKeyPress == 2){
                    handelQuest(mEdtSearch.getText().toString());
                    mCountKeyPress = 1;
                }


                return false;
            }
        });

        this.mLsvPlace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(QuestActivity.this, PlaceActivity.class);
                intent.putExtra("place_id", mArrListPlace.get(position).getId());
                startActivity(intent);
            }
        });

    }

    private void handleData(){

        this.mArrListPlace = new ArrayList<>();
        this.mPlaceAdapter = new PlaceAdapter(this, this.mArrListPlace);

        this.mLsvPlace.setAdapter(this.mPlaceAdapter);

    }

    private void handleDataFromIntent(){

        Intent intent = getIntent();
        String quest = intent.getStringExtra("quest");

        this.mEdtSearch.append(quest);
        handelQuest(quest);
    }

    private void handelQuest(String quest){

         this.mPresenterQuest.handleQuest(quest);
    }

    @Override
    public void OnHandleDataSuccessful() {

        this.mArrListPlace.clear();

        ArrayList<Place> arrListPlace = this.mPresenterQuest.getArrListPlace();
        for(Place place : arrListPlace){
            this.mArrListPlace.add(place);
        }

        this.mPlaceAdapter.notifyDataSetChanged();
    }

    @Override
    public void OnHandleDataFailed(String error) {
        Log.d("QuestActivity", error);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imv_clear:
                this.mEdtSearch.setText("");
                this.mArrListPlace.clear();
                this.mPlaceAdapter.notifyDataSetChanged();
                break;
            case R.id.btn_cancel:
                finish();
                break;
        }
    }
}
