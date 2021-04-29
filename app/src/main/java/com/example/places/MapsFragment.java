package com.example.places;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

public class MapsFragment extends Fragment implements OnMapReadyCallback, LocationListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private LocationManager manager;
    private Marker myMarker;
    private ArrayList<Marker> markers;
    private String address;
    private AddressListener observer;
    private LatLng latLngGlobal;

    public MapsFragment(){

    }

    public void setObserver(AddressListener observer){
        this.observer = observer;
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
       // SharedPreferences preferences = getActivity().getSharedPreferences("Direccion", getActivity().MODE_PRIVATE);
       // String tempAdress = preferences.getString("tempDirection", "NO_OBJ");

       // if (!tempAdress.equals("NO_OBJ")){
        //    address = tempAdress;
        //}

        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 2, this);
        setInitPos();
        //updateMarkers();

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

    /*public void updateMarkers(){
        for (int i = 0; i<markers.size(); i++){
            LatLng markersPos = new LatLng(markers.get(i).getPosition().latitude, markers.get(i).getPosition().longitude);
            Marker marker = mMap.addMarker(new MarkerOptions().position(markersPos).title("marker"));
            markers.add(marker);
        }
    }*/

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

   // @Override
   //public void onStop() {
     //   super.onStop();
        /*Geocoder geocoder = new Geocoder(getContext());
        try {
            List<Address> addresses = geocoder.getFromLocation(latLngGlobal.latitude, latLngGlobal.longitude, 1);
            address = addresses.get(0).getAddressLine(0);
            SharedPreferences preferences = getActivity().getSharedPreferences("Direccion", getActivity().MODE_PRIVATE);
            preferences.edit()
                    .putString("tempDirection", address)
                    .apply();
            //observer.onMarkerAdded(address);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    //}

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        latLngGlobal = latLng;
        Marker m = mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
        markers.add(m);
        Geocoder geocoder = new Geocoder(getContext());
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            address = addresses.get(0).getAddressLine(0);
            SharedPreferences preferences = getActivity().getSharedPreferences("Direccion", getActivity().MODE_PRIVATE);
            preferences.edit()
                    .putString("tempDirection", address)
                    .apply();
            //observer.onMarkerAdded(address);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(getContext(), marker.getPosition().latitude + "," + marker.getPosition().longitude, Toast.LENGTH_LONG).show();
        Log.e(">>>>", marker.getPosition().latitude + "," + marker.getPosition().longitude);
        Log.e("direccion", "" + address);
        marker.showInfoWindow();
        return true;
    }

    public interface AddressListener{
        void onMarkerAdded(String dir);
    }
}