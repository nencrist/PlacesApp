package com.example.places;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.places.models.Place;
import com.example.places.util.Constants;
import com.example.places.util.HTTPSWebUtilDomi;
import com.google.gson.Gson;

import java.util.UUID;

public class NewItemFragment extends Fragment implements View.OnClickListener, ModalDialog.ImageListener {

    //Sate o variables globales
    private MapsFragment mapFragment;
    private ModalDialog dialog;
    private Place place;
    private String imagePath;
    private PlacesAdapter adapter;
    private RecyclerView recyclerView;

    //View
    private EditText placeNameET;
    private ImageButton locationBtn;
    private ImageButton addImageBtn;
    private Button registerBtn;
    private ImageView pictureChosen;

    public NewItemFragment() {
        // Required empty public constructor
    }


    public static NewItemFragment newInstance() {
        NewItemFragment fragment = new NewItemFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    //El fragment se vuelve visible
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_new_item, container, false);
        placeNameET = root.findViewById(R.id.placeNameET);
        locationBtn = root.findViewById(R.id.locationBtn);
        addImageBtn = root.findViewById(R.id.addImageBtn);
        registerBtn = root.findViewById(R.id.registerBtn);
        pictureChosen = root.findViewById(R.id.pictureChosen);
        locationBtn.setOnClickListener(this);
        addImageBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        mapFragment = new MapsFragment();
        place = new Place();
        adapter = new PlacesAdapter();

        return root;

    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences preferences = getActivity().getSharedPreferences("serialiacionJSON", getActivity().MODE_PRIVATE);
        String json = preferences.getString("tempPlace", "NO_OBJ");

        if (!json.equals("NO_OBJ")){
            Gson gson = new Gson();
            Place place = gson.fromJson(json, Place.class);

            placeNameET.setText(place.getPlaceName());
            imagePath = place.getImagePath();
        }
    }

    @Override
    public void onPause() {
        String name = placeNameET.getText().toString();
        String pathTemp = imagePath;
        Place place = new Place(name, pathTemp);

        //json
        Gson gson = new Gson();
        String json = gson.toJson(place);
        Log.e("....", "" + json);

        //localStorage = SharedPreferences
        SharedPreferences preferences = getActivity().getSharedPreferences("serializacionJSON", getActivity().MODE_PRIVATE);
        preferences.edit()
                .putString("tempPlace", json)
                .apply();

        super.onPause();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.locationBtn:
                    showFragment(mapFragment);
                break;
            case R.id.addImageBtn:
                    dialog = ModalDialog.newInstance();
                    dialog.setObservador(this);
                    dialog.show(getActivity().getSupportFragmentManager(), "dialog");

                break;
            case R.id.registerBtn:
                String name = placeNameET.getText().toString();
                String temp = imagePath;
                Log.e("....", name + temp);
                Place place = new Place(name, temp);
                adapter.addPlace(place);
                Log.e("......", place.getPlaceName() + place.getImagePath());
                /*placeN = placeNameET.getText().toString();
                Place place = new Place(UUID.randomUUID().toString(), placeN, pictureChosen.getId());
                Gson gson = new Gson();
                String json = gson.toJson(place);
                HTTPSWebUtilDomi https = new HTTPSWebUtilDomi();
                new Thread(
                        () -> {
                            String response = https.PUTrequest(Constants.BASEURL+"place/"+place.getPlaceName()+".json",json);
                        }
                ).start();*/



                break;
        }
    }

    public void showFragment(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }

    @Override
    public void onImageChosen(String path) {
        imagePath = path;
        Bitmap image = BitmapFactory.decodeFile(path);
        Bitmap thumbnail = Bitmap.createScaledBitmap(
                image, image.getWidth()/4, image.getHeight()/4, true
        );
        pictureChosen.setImageBitmap(thumbnail);
        dialog.dismiss();
    }


}