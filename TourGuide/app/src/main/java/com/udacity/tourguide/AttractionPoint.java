package com.udacity.tourguide;

import android.content.Context;
import android.content.res.Resources;
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
    Context ctx;

    public AttractionPoint(Context context, int category, String name, String addressName, double longitude,
                           double latitude, int locationResId, int ImageResId) {
        ctx = context;
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
        Resources res = ctx.getResources();
        if (category == RESTAURANT) {
            return res.getString(R.string.cat_restaurant);
        } else if (category == HOTEL) {
            return res.getString(R.string.cat_hotel);
        } else if (category == SHOPPING) {
            return res.getString(R.string.cat_shop);
        } else {
            return res.getString(R.string.cat_sin);
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
