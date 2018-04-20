package com.example.tamle.traveleverywhere.view.place;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.tamle.traveleverywhere.R;
import com.example.tamle.traveleverywhere.view.path.PathActivity;
import com.example.tamle.traveleverywhere.view.weather.WeatherActivity;

/**
 * Created by tamle on 09/04/2018.
 */

public class PlaceFragment extends Fragment implements View.OnClickListener {

    private View mView;

    private GridView mGridViewPhotoPlace;
    private RecyclerView mRecyclerViewRelatedPlace;
    private TextView mTxvName;
    private Button mBtnViewWeather;
    private Button mBtnViewPath;

    private String mPlaceId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_place, container, false);

        unitViews();
        getDataFromActivity();
        handleData();

        return mView;
    }

    private void unitViews(){
        this.mGridViewPhotoPlace = (GridView) mView.findViewById(R.id.grid_view_photo_place);
        this.mRecyclerViewRelatedPlace = (RecyclerView) mView.findViewById(R.id.recycler_view_related_place);
        this.mTxvName = (TextView) mView.findViewById(R.id.txv_name);
        this.mBtnViewPath = (Button) mView.findViewById(R.id.btn_view_path);
        this.mBtnViewWeather = mView.findViewById(R.id.btn_view_weather);

        this.mBtnViewPath.setOnClickListener(this);
        this.mBtnViewWeather.setOnClickListener(this);
    }

    private void getDataFromActivity(){
        Bundle bundle = getArguments();
        this.mPlaceId = bundle.getString("place_end_id");
    }

    private void handleData(){

        this.mTxvName.setText(PlaceActivity.PRESENTER_PLACE.getPlaceName());

        PhotoPlaceAdapter photoPlaceAdapter = new PhotoPlaceAdapter(getActivity(),
                PlaceActivity.PRESENTER_PLACE.getArrListImage());
        this.mGridViewPhotoPlace.setAdapter(photoPlaceAdapter);

        RelatedPlaceAdapter relatedPlaceAdapter = new RelatedPlaceAdapter(getActivity(),
                PlaceActivity.PRESENTER_PLACE.getArrListRelatedPlaces());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        this.mRecyclerViewRelatedPlace.setLayoutManager(linearLayoutManager);

        this.mRecyclerViewRelatedPlace.setAdapter(relatedPlaceAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_view_weather:
                Intent intent = new Intent(getActivity(), WeatherActivity.class);
                intent.putExtra("location", PlaceActivity.PRESENTER_PLACE.getLocation());
                intent.putExtra("city_name", this.mTxvName.getText());
                startActivity(intent);
                break;
            case R.id.btn_view_path:
                Intent intentToPathActivity = new Intent(getActivity(), PathActivity.class);
                intentToPathActivity.putExtra("place_end_id", this.mPlaceId);
                startActivity(intentToPathActivity);
                break;
        }
    }
}
