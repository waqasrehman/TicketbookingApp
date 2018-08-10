package com.example.waqasur_rehman.myapplication;

/**
 * Created by WAQAS UR-REHMAN on 16/02/2016.
 *
 *
 * this class hold all the setters and gettters that are need to get the  data returned from the database in JSON format
 *
 */
import java.util.ArrayList;


public class Item {
    public String title,image, description ,venue, Genre;
    public int price , ID, year;
    private double rate;
    private ArrayList<String> genre;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public void setID(int ID){
        this.ID = ID;

    }
    public int getID(){

        return ID;
    }
    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void setYear(int year){

        this.year= year;
    }

    public int getYear(){

        return year;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDescription (String description){

        this.description = description;
    }
    public String getDescription (){
        return description;

    }

    public void setVenue( String venue){

        this.venue = venue;
    }

    public String getVenue(){

        return venue;
    }

public void setGenre(String genre){

    this.Genre= genre;
}

    public String getGenre(){

        return Genre;
    }

}
