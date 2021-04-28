package com.example.places;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.places.util.Constants;
import com.example.places.util.HTTPSWebUtilDomi;

public class ListaLugares extends Fragment {

    //variables
    private RecyclerView recyclerView;
    private String placeNameVar;
    private LinearLayoutManager layoutManager;
    private PlacesAdapter adapter;

    public ListaLugares() {

    }


    public static ListaLugares newInstance() {
        ListaLugares fragment = new ListaLugares();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_lista_lugares, container, false);
        recyclerView = root.findViewById(R.id.listRecycler);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PlacesAdapter();
        recyclerView.setAdapter(adapter);

        return root;
    }

}
   /* public void getPlace(){
        HTTPSWebUtilDomi https = new HTTPSWebUtilDomi();

        new Thread(
                () -> {
                    String response = https.GETrequest(Constants.BASEURL + "place/" + placeNameVar + ".json");
                    getActivity().runOnUiThread(
                            ()->{
                                placeInfo.setText(response);
                                Log.e(">>>", placeInfo.getText().toString());
                            }
                    );
                }
        ).start();
    }*/