package com.example.places.models;

import android.graphics.drawable.Drawable;

public class Place {

    private String placeName;
    private String imagePath;

    public Place(String placeName, String imagePath) {
        this.placeName = placeName;
        this.imagePath = imagePath;
    }

    public Place() {
    }


    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setPlaceImage(String placeImage) {
        this.imagePath = imagePath;
    }
}
