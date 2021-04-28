package com.example.places;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class PlacesView extends RecyclerView.ViewHolder {

    private ConstraintLayout root;
    private TextView name;
    private Button viewOnMapBtn;
    private ImageView image;


    public PlacesView(ConstraintLayout root) {
        super(root);
        this.root = root;
        name = root.findViewById(R.id.placeNameTV);
        viewOnMapBtn = root.findViewById(R.id.viewMapBtn);
        image = root.findViewById(R.id.placeImageIV);
    }

    public ConstraintLayout getRoot() {
        return root;
    }

    public void setRoot(ConstraintLayout root) {
        this.root = root;
    }

    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }

    public Button getViewOnMapBtn() {
        return viewOnMapBtn;
    }

    public void setViewOnMapBtn(Button viewOnMapBtn) {
        this.viewOnMapBtn = viewOnMapBtn;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }
}
