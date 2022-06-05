package com.example.superlist.java.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.superlist.R;
import com.example.superlist.java.Objects.List;

import java.util.ArrayList;


public class ListsFragment extends Fragment {

    private RecyclerView fragment_RECYC_lists;
    private ArrayList<List> listsArrayList;
    private ListAdapter list_adapter;


    public ListsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lists, container, false);
    }


    
}