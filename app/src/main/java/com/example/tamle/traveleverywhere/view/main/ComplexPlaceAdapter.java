package com.example.tamle.traveleverywhere.view.main;

import com.example.tamle.traveleverywhere.R;
import com.example.tamle.traveleverywhere.dto.ComplexPlace;
import com.example.tamle.traveleverywhere.view.place.PlaceActivity;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by tamle on 31/03/2018.
 */

public class ComplexPlaceAdapter implements ListAdapter {

    private ArrayList<ComplexPlace> mArrListComplexPlace;
    private Context mContext;

    private LayoutInflater mInflater;

    public ComplexPlaceAdapter(Context context, ArrayList<ComplexPlace> mArrListComplexPlace) {
        this.mArrListComplexPlace = mArrListComplexPlace;
        this.mContext = context;

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        if (this.mArrListComplexPlace != null && !this.mArrListComplexPlace.isEmpty()) {
            return this.mArrListComplexPlace.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return this.mArrListComplexPlace.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        ComplexPlace complexComplexPlace = this.mArrListComplexPlace.get(position);

        if (convertView == null) {
            convertView = this.mInflater.inflate(R.layout.item_complex_place, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.mImvImage = convertView.findViewById(R.id.imv_image);
            viewHolder.mTxvPlaceName = convertView.findViewById(R.id.txv_place_name);
            viewHolder.mTxvSlogan = convertView.findViewById(R.id.txv_slogan);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mImvImage.setImageResource(complexComplexPlace.getImage());
        viewHolder.mTxvSlogan.setText(complexComplexPlace.getSlogan());
        viewHolder.mTxvPlaceName.setText(complexComplexPlace.getPlaceName());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PlaceActivity.class);
                intent.putExtra("place_id", mArrListComplexPlace.get(position).getId());
                mContext.startActivity(intent);
            }
        });

        return convertView;

    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return this.mArrListComplexPlace.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    public class ViewHolder {

        private ImageView mImvImage;
        private TextView mTxvPlaceName;
        private TextView mTxvSlogan;
    }

}
