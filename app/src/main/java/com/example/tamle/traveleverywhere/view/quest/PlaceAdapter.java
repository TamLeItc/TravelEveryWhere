package com.example.tamle.traveleverywhere.view.quest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tamle.traveleverywhere.R;
import com.example.tamle.traveleverywhere.dto.Place;

import java.util.ArrayList;

/**
 * Created by tamle on 17/03/2018.
 */

public class PlaceAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Place> mArrListSearchPlace;

    public PlaceAdapter(Context mContext, ArrayList<Place> mArrListSearchPlace) {
        this.mContext = mContext;
        this.mArrListSearchPlace = mArrListSearchPlace;
    }

    @Override
    public int getCount() {
        return this.mArrListSearchPlace.size();
    }

    @Override
    public Object getItem(int position) {
        return this.mArrListSearchPlace.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if(convertView == null){

            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_search_place, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.mTxvMainTextPlace = (TextView) convertView.findViewById(R.id.txv_main_text_place);
            viewHolder.mTxvSecondTextPlace = convertView.findViewById(R.id.txv_second_text_place);

            convertView.setTag(viewHolder);

        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Place searchPlace = this.mArrListSearchPlace.get(position);
        viewHolder.mTxvMainTextPlace.setText(searchPlace.getMainTextPlace());
        viewHolder.mTxvSecondTextPlace.setText(searchPlace.getSecondTextPlace());

        return convertView;

    }

    public class ViewHolder{

        private TextView mTxvMainTextPlace;
        private TextView mTxvSecondTextPlace;

    }
}
