package com.example.places.models;

import android.graphics.drawable.Drawable;

public class Place {

    private String placeName;
    private String imagePath;
    private String direccion;

    public Place(String placeName, String imagePath, String direccion) {
        this.placeName = placeName;
        this.imagePath = imagePath;
        this.direccion = direccion;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
