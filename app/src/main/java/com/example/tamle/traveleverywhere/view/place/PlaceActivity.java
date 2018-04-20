package com.example.tamle.traveleverywhere.view.place;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.tamle.traveleverywhere.R;
import com.example.tamle.traveleverywhere.presenter.place.PresenterPlace;
import com.example.tamle.traveleverywhere.presenter.place.ViewHandlePlace;
import com.example.tamle.traveleverywhere.view.load.LoadFragment;

/**
 * Created by tamle on 09/04/2018.
 */

public class PlaceActivity extends AppCompatActivity implements ViewHandlePlace {

    public static PresenterPlace PRESENTER_PLACE;
    private String mPlaceId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        addLoadFragment();
        handleDataFromIntent();
    }

    private void addLoadFragment(){

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frl_place, new LoadFragment());
        fragmentTransaction.commit();
    }

    public void handleDataFromIntent(){
        this.mPlaceId = getIntent().getStringExtra("place_id");

        this.PRESENTER_PLACE = new PresenterPlace(this, this.mPlaceId);
    }

    @Override
    public void OnHandleDataSuccessful() {

        PlaceFragment placeFragment = new PlaceFragment();
        Bundle bundle = new Bundle();
        bundle.putString("place_end_id", this.mPlaceId);
        placeFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frl_place, placeFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void OnHandleDataFailed(String error) {
        Log.d("PlaceActivity", error);
        Toast.makeText(this, "Có lỗi trong quá trình tải dữ liệu", Toast.LENGTH_SHORT);
        finish();
    }
}
