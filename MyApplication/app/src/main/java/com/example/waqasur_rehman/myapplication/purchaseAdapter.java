package com.example.waqasur_rehman.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class purchaseAdapter extends BaseAdapter {
    private LayoutInflater inflater; // instance of layout inflater
    private purchasehistory purchasehistory ;// instance of Activity
    ArrayList <itempurchased> items ; // list of item





    public purchaseAdapter(purchasehistory activity, ArrayList<itempurchased> items ) { // constructor
        this.purchasehistory = activity;
        this.items = items;

    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater==null){
            inflater=(LayoutInflater) purchasehistory.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView ==null){
            convertView=inflater.inflate(R.layout.purchase_layout,null);
        }

        TextView title= (TextView) convertView.findViewById(R.id.title_purchased);
        TextView venue= (TextView) convertView.findViewById(R.id.venue_purchased);
        TextView  price =(TextView)convertView.findViewById(R.id.price_purchased);

        itempurchased item = items.get(position);
        //
        title.setText(item.getTitle());
        venue.setText(item.getVenue());
        price.setText(String.valueOf(item.getPrice()));




        return convertView;
    }




}
