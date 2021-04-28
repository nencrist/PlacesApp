package com.example.places;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import static android.content.Context.LOCATION_SERVICE;

public class MapsFragment extends Fragment implements OnMapReadyCallback, LocationListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private LocationManager manager;
    private Marker myMarker;
    private ArrayList<Marker> markers;

    public MapsFragment(){

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        manager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

        View root = inflater.inflate(R.layout.fragment_maps, container, false);
        markers = new ArrayList<>();
        return root;

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 2, this);
        setInitPos();

        mMap.setOnMapLongClickListener(this);
        mMap.setOnMarkerClickListener(this);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);

        }

    }

    public void setInitPos(){
        @SuppressLint("MissingPermission")
       Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null){
            updateMyLocation(location);
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        updateMyLocation(location);
    }

    public void updateMyLocation(Location location){
        LatLng myPos = new LatLng(location.getLatitude(), location.getLongitude());
        if (myMarker == null){
            myMarker = mMap.addMarker(new MarkerOptions().position(myPos).title("Yo"));
        }else{
            myMarker.setPosition(myPos);
        }
        //esto hace que la camara me siga
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPos, 13));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        Marker m = mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
        markers.add(m);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(getContext(), marker.getPosition().latitude + "," + marker.getPosition().longitude, Toast.LENGTH_LONG).show();
        Log.e(">>>>", marker.getPosition().latitude + "," + marker.getPosition().longitude);
        marker.showInfoWindow();
        return true;
    }
}