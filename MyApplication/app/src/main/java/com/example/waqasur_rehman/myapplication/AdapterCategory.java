package com.example.waqasur_rehman.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;


public class AdapterCategory extends BaseAdapter implements Filterable{
    private LayoutInflater inflater; // instance of layout inflater
    private CategoryResult categoryResult ;// instance of Activity
    ArrayList <Item> items ; // list of item

    CustomeFileter filter; // instance of filter

    ArrayList<Item> filteritems; // list of items that have been filter




    ImageLoader imageLoader= AppController.getmInstance().getmImageLoader(); // instance of image loader class from android volley

    public AdapterCategory(CategoryResult activity, ArrayList<Item> items ) { // constructor
        this.categoryResult = activity;
        this.items = items;
        this.filteritems=items;

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

    public void resetData(){


    }

    /*  set text and images to all the  TextView and Image by referencing there XML IDs  and adding them to convertview*/
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater==null){ // slight change from activity.getsystemservice to activity.getActivity().getSystemService due to use of fragment instead of activity
            inflater=(LayoutInflater) categoryResult.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView ==null){
            convertView=inflater.inflate(R.layout.custom_layout,null);
        }
        if(imageLoader==null)
            imageLoader=AppController.getmInstance().getmImageLoader();
        NetworkImageView imageView= (NetworkImageView) convertView.findViewById(R.id.image_selected);
        TextView title= (TextView) convertView.findViewById(R.id.title);
        TextView year= (TextView) convertView.findViewById(R.id.releaseYear);
        TextView Genre= (TextView) convertView.findViewById(R.id.genre);
        TextView  id = (TextView)convertView.findViewById(R.id.ID);
        //getting data for row
        Item item=items.get(position);
        imageView.setImageUrl(item.getImage(), imageLoader);

        //title
        title.setText(item.getTitle());
        //rate
        id.setText(String.valueOf(item.getID()));
        year.setText(String.valueOf(item.getYear()));
        Genre.setText(String.valueOf(item.getGenre()));

        return convertView;
    }

    /* this class for filtering through the data that been loaded from the base, it creates an
    * additional customefilter class which filters through the list of item by duplicating the original list items  and after filtering it addes to filters item list
    * which is then displayed via the getView method*/
    @Override
    public Filter getFilter() {
        if(filter == null){

            filteritems.clear();// clear the  list of filteritems before custome filter is called, so that the value arent duplicated
            filter= new CustomeFileter();
        }

        return filter;

    }

    class CustomeFileter extends Filter{




        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults result = new FilterResults();

            if(constraint!=null && constraint.length() >0){
                constraint = constraint.toString().toLowerCase();
                ArrayList<Item> filters = new ArrayList<Item>();  // create an array list which holds filters items

                //get item

                for(int i=0; i<filteritems.size(); i++){

                    Item item = new Item();
                    // filters.clear();

                    if(filteritems.get(i).title.toLowerCase().contains(constraint) || filteritems.get(i).Genre.toLowerCase().contains(constraint)){ // filter the list by title and Genre

                        item = filteritems.get(i);

                        filters.add(item);
                    }
                }
                result.count= filters.size();
                result.values= filters;

            }else { // if the search box is empty display the duplicate item list which holds all the original values

                result.count= filteritems.size();
                result.values= filteritems;
            }

            return result;
        }




        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            items= (ArrayList<Item>) results.values;
            notifyDataSetChanged();


        }
    }


}
