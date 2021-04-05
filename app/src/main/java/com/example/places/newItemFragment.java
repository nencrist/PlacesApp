package com.example.places;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class newItemFragment extends Fragment {

    //Sate o variables globales

    public newItemFragment() {
        // Required empty public constructor
    }


    public static newItemFragment newInstance() {
        newItemFragment fragment = new newItemFragment();
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
        return inflater.inflate(R.layout.fragment_new_item, container, false);
    }
}