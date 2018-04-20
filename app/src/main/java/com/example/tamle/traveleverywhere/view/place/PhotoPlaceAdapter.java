package com.example.tamle.traveleverywhere.view.place;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.tamle.traveleverywhere.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by tamle on 03/03/2018.
 */

public class PhotoPlaceAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> mArrListPhotoPlace;

    public PhotoPlaceAdapter(Context mContext, ArrayList<String> mArrListPhotoPlace) {
        this.mContext = mContext;
        this.mArrListPhotoPlace = mArrListPhotoPlace;
    }

    @Override
    public int getCount() {
        return this.mArrListPhotoPlace.size();
    }

    @Override
    public Object getItem(int position) {
        return this.mArrListPhotoPlace.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_photo_place, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.imvPhotoPlace = convertView.findViewById(R.id.img_photo_place);

            convertView.setTag(viewHolder);

        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=" +
                this.mArrListPhotoPlace.get(position) + "&key=AIzaSyDBLLJuXzJ1Jf7rQYSY1IjQUMCtjaVb31Y";

        Picasso.with(mContext).load(url).into(viewHolder.imvPhotoPlace);

        return convertView;
    }

    public class ViewHolder{
        private ImageView imvPhotoPlace;
    }

}
