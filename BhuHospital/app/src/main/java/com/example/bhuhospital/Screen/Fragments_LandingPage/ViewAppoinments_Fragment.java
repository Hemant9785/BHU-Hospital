package com.example.bhuhospital.Screen.Fragments_LandingPage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bhuhospital.Adaptors.appoinment_adapter;
import com.example.bhuhospital.MainActivity;
import com.example.bhuhospital.R;
import com.example.bhuhospital.Room.appoinment_entity;
import com.example.bhuhospital.Screen.LandingPage;

import java.util.List;


public class ViewAppoinments_Fragment extends Fragment {

    public static RecyclerView recyclerView;
    public static appoinment_adapter adapter;
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_view_appoinments_, container, false);
        initUi();
        return rootView;
    }

    private void initUi() {
        recyclerView = rootView.findViewById(R.id.view_appoinment_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.HORIZONTAL));


        adapter = new appoinment_adapter();
        LandingPage.note_viewmodel.getAllNotes().observe(getViewLifecycleOwner()
                , new Observer<List<appoinment_entity>>() {
                    @Override
                    public void onChanged(List<appoinment_entity> appoinment_entities) {
                        adapter.setDataset(appoinment_entities);
                    }
                });
        recyclerView.setAdapter(adapter);
    }
}