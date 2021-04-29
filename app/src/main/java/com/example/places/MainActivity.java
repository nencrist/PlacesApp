package com.example.places;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.places.models.Place;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private NewItemFragment newItemFragment;
    private MapsFragment mapFragment;
    private ListaLugares listaLugares;
    private BottomNavigationView navigationMenu;
    public static final int PERMISSIONS_CALLBACK = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationMenu = findViewById(R.id.navigationMenu);
        newItemFragment = NewItemFragment.newInstance();
        mapFragment = new MapsFragment();
        listaLugares = ListaLugares.newInstance();
        newItemFragment.setObserver(listaLugares);

        showFragment(newItemFragment);

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
                }, PERMISSIONS_CALLBACK
        );

        navigationMenu.setOnNavigationItemSelectedListener(
                (menuItem) -> {
                    switch (menuItem.getItemId()){
                        case R.id.newItem:
                            showFragment(newItemFragment);
                            break;
                        case R.id.mapItem:
                            showFragment(mapFragment);
                            break;
                        case R.id.listItem:
                            showFragment(listaLugares);
                            break;

                    }
                    return true;
                }
        );



    }

    public void showFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSIONS_CALLBACK){
            boolean allGrant = true;
            for (int i=0 ; i<grantResults.length ; i++){
                if (grantResults[i] == PackageManager.PERMISSION_DENIED){
                    allGrant = false;
                    break;
                }
            }
            if (allGrant){
                Toast.makeText(this, "Todos los permisos concedidos", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, "Alerta!, no todos los permisos fueron concedidos", Toast.LENGTH_LONG).show();
            }
        }
    }

}