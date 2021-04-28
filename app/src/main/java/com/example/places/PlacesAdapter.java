package com.example.places;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.places.models.Place;

import java.util.ArrayList;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesView> {

    private ArrayList<Place> places;

    public PlacesAdapter(){
        places = new ArrayList<>();

       // places.add(new Place("jaime", "/storage/emulated/0/DCIM/Camera/IMG_20210428_104244.jpg"));
    }


    public void addPlace(Place place){
        places.add(place);
        this.notifyDataSetChanged();
    }

    @Override
    public PlacesView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //esto cambia el xml a view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.placerow, null);
        ConstraintLayout rowRoot = (ConstraintLayout) row;
        PlacesView placesView = new PlacesView(rowRoot);

        return placesView;
    }

    @Override
    public void onBindViewHolder(@NonNull PlacesView holder, int position) {
        holder.getName().setText(places.get(position).getPlaceName());
        Bitmap bitMap = BitmapFactory.decodeFile(places.get(position).getImagePath());
        holder.getImage().setImageBitmap(bitMap);
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

}
