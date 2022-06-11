package com.example.bhuhospital.Screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;

import android.os.Bundle;

import com.example.bhuhospital.Adaptors.appoinment_adapter;
import com.example.bhuhospital.R;
import com.example.bhuhospital.Room.appoinment_entity;
import com.example.bhuhospital.firebase_.appoinment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DocActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    DatabaseReference reference;
    RecyclerView recyclerView;
    public static appoinment_adapter adapter_doc;
    List<appoinment_entity>data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc);
        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Appoinments");
        recyclerView  = findViewById(R.id.doc_view_appoinment_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(DocActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(DocActivity.this, LinearLayoutManager.HORIZONTAL));

        adapter_doc = new appoinment_adapter();
        recyclerView.setAdapter(adapter_doc);
        reference.child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data.clear();
                for(DataSnapshot ds:snapshot.getChildren())
                {
                    appoinment a = ds.getValue(appoinment.class);
                    appoinment_entity ae = new appoinment_entity(a.doc,a.patient,a.email,a.symptoms,a.date,0);
                    data.add(ae);
                }
                adapter_doc.setDataset(data);
                adapter_doc.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}