package com.example.superlist.superlist.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.superlist.R;
import com.example.superlist.superlist.Activities.CreateListActivity;
import com.example.superlist.superlist.Objects.List;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class ListsFragment extends Fragment {

    private RecyclerView fragment_RECYC_lists;
    private ArrayList<List> listsArrayList;
    private ListAdapter list_adapter;
    private FloatingActionButton toolbar_FAB_add;
    private Activity currentActivity;


    public ListsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentActivity = getActivity();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_lists, container, false);

        findViews(view);

        //panel_Toolbar_Top.setTitle(getString(R.string.my_lists));

       // createRecycler();
        //listsArrayChangeListener();


        return view;
    }
    private void findViews(View view){
        toolbar_FAB_add = currentActivity.findViewById(R.id.toolbar_FAB_add);

    }

    @Override
    public void onStart() {
        super.onStart();

        toolbar_FAB_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(currentActivity, "Create List", Toast.LENGTH_LONG).show();
                startActivity(new Intent(currentActivity, CreateListActivity.class));
            }
        });


    }




}