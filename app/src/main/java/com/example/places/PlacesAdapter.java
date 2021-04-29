package com.example.places;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.places.models.Place;
import com.google.gson.Gson;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesView> {

    private ArrayList<Place> places;
    private MainActivity mainActivity;

    public PlacesAdapter(){
        places = new ArrayList<>();
        mainActivity = new MainActivity();

       //places.add(new Place("jaime", "/storage/emulated/0/DCIM/Camera/IMG_20210428_104244.jpg"));
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

    public void setPlaces(ArrayList<Place> places){
        this.places = places;
        this.notifyDataSetChanged();
    }

}
