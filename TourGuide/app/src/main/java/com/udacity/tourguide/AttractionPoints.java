package com.udacity.tourguide;

import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;

/**
 * Created by nloc on 8/2/2016.
 */
public class AttractionPoints {

    private static final int RESTAURANT = 1;
    private static final int HOTEL = 2;
    private static final int SHOPPING = 3;
    private static final int SIGHT_SEEING = 4;


    int category;
    String name;
    String addressName;
    double longitude;
    double latitude;

    public Location getLocation() {
        Location l = new Location(name);
        l.setLatitude(latitude);
        l.setLongitude(longitude);
        return l;
    }

    public int getCategory(){
        return category;
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
