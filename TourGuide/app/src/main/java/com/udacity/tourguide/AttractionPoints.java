package com.udacity.tourguide;

import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;

/**
 * Created by nloc on 8/2/2016.
 */
public class AttractionPoints {

    public static final int RESTAURANT = 1;
    public static final int HOTEL = 2;
    public static final int SHOPPING = 3;
    public static final int SIGHT_SEEING = 4;


    int category;
    String name;
    String addressName;
    double longitude;
    double latitude;

    public AttractionPoints( int category, String name, String addressName, double longitude, double latitude){
        this.category = category;
        this.name = name;
        this.addressName = addressName;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Location getLocation() {
        Location l = new Location(name);
        l.setLatitude(latitude);
        l.setLongitude(longitude);
        return l;
    }

    public int getCategory(){
        return category;
    }

    public String getCategoryAsString(int category) {
        if (category == RESTAURANT){
            return "restaurant";
        } else if (category == HOTEL) {
            return "hotel";
        } else if (category == SHOPPING) {
            return "shopping";
        } else {
            return "sight-seeing";
        }
    }

    public String getName(){
        return name;
    }

    public String getAddressName(){
        return addressName;
    }

    public void setLocation(Location lot) {
        longitude = lot.getLongitude();
        latitude = lot.getLatitude();
    }

    public void setName(String name){
        this.name = name;
    }

    public void setCategory(int cat) {
        this.category = cat;
    }

}
