package com.example.waqasur_rehman.myapplication;

/**
 * Created by WAQAS UR-REHMAN on 16/02/2016.
 *
 *
 * this class hold all the setters and gettters that are need to get the  data returned from the database in JSON format
 *
 */
public class itempurchased {
    public String title ,venue;
    public int price;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    public void setVenue( String venue){

        this.venue = venue;
    }

    public String getVenue(){

        return venue;
    }



}
