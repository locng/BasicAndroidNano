package com.udacity.tourguide;

import android.location.Location;

/**
 * Created by nloc on 8/2/2016.
 */
public class AttractionPoint {

    public static final int RESTAURANT = 1;
    public static final int HOTEL = 2;
    public static final int SHOPPING = 3;
    public static final int SIGHT_SEEING = 4;

    int category;
    String name;
    String addressName;
    double longitude;
    double latitude;
    int ImageResId = 0;
    int locationResId;

    public AttractionPoint(int category, String name, String addressName, double longitude,
                           double latitude, int locationResId, int ImageResId) {
        this.category = category;
        this.name = name;
        this.addressName = addressName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.locationResId = locationResId;
        this.ImageResId = ImageResId;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int cat) {
        this.category = cat;
    }

    public String getCategoryAsString(int category) {
        if (category == RESTAURANT) {
            return "Restaurant";
        } else if (category == HOTEL) {
            return "Hotel";
        } else if (category == SHOPPING) {
            return "Shopping";
        } else {
            return "Sight-seeing";
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setLocation(Location lot) {
        longitude = lot.getLongitude();
        latitude = lot.getLatitude();
    }

    public int getLocationResId() {
        return locationResId;
    }

    public int getImageResId() {
        return ImageResId;
    }

    public boolean hasCoverPhoto() {
        return ImageResId != 0;
    }

    public String toString() {
        return "Name: " + name + "Address: " + addressName + "";
    }

}
