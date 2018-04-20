package com.example.tamle.traveleverywhere.view.place;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tamle.traveleverywhere.R;
import com.example.tamle.traveleverywhere.dto.RelatedPlace;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by tamle on 04/03/2018.
 */

public class RelatedPlaceAdapter extends RecyclerView.Adapter<RelatedPlaceAdapter.ViewHoler>  {

    private ArrayList<RelatedPlace> mArrListRelatedPlace;
    private Context mContext;

    public RelatedPlaceAdapter(Context context, ArrayList<RelatedPlace> mArrListRelatedPlace) {
        this.mArrListRelatedPlace = mArrListRelatedPlace;
        this.mContext = context;
    }

    @Override
    public ViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_involve_place, parent, false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(ViewHoler holder, int position) {
        RelatedPlace RelatedPlace = this.mArrListRelatedPlace.get(position);

        holder.mTxvFoodPlaceName.setText(RelatedPlace.getName());
        holder.mTxvVicinity.setText(RelatedPlace.getVicinity());

        String url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="
                + RelatedPlace.getPhotoReference() + "&key=AIzaSyDBLLJuXzJ1Jf7rQYSY1IjQUMCtjaVb31Y";
        Picasso.with(mContext).load(url).into(holder.mImvFoodPlacePhoto);
    }

    @Override
    public int getItemCount() {
        return this.mArrListRelatedPlace.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder{

        private TextView mTxvFoodPlaceName, mTxvVicinity;
        private ImageView mImvFoodPlacePhoto;

        public ViewHoler(View itemView) {
            super(itemView);
            unitViews(itemView);
        }

        private void unitViews(View itemView){
            this.mTxvFoodPlaceName = (TextView) itemView.findViewById(R.id.txv_food_place_name);
            this.mTxvVicinity = (TextView) itemView.findViewById(R.id.txv_vicinity);
            this.mImvFoodPlacePhoto = (ImageView) itemView.findViewById(R.id.imv_food_place_photo);
        }
    }

}
