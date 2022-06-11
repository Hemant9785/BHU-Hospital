package com.example.bhuhospital.Screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.bhuhospital.Adaptors.ViewPager_Adaptor;
import com.example.bhuhospital.MainActivity;
import com.example.bhuhospital.R;
import com.example.bhuhospital.Room.appoinment_entity;
import com.example.bhuhospital.Room.appoinment_viewmodel;
import com.example.bhuhospital.Screen.Fragments_LandingPage.MakeAppoinments_Fragment;
import com.example.bhuhospital.Screen.Fragments_LandingPage.Profile_Fragment;
import com.example.bhuhospital.Screen.Fragments_LandingPage.ViewAppoinments_Fragment;
import com.example.bhuhospital.firebase_.appoinment;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LandingPage extends AppCompatActivity {

    FirebaseAuth mAuth;
    TabLayout tabLayout;
    ViewPager viewPager;
    FragmentManager fragmentManager;
    ViewPager_Adaptor pager_adaptor;
    DatabaseReference databaseReference;

    public static appoinment_viewmodel note_viewmodel;
    public static List<appoinment_entity>dataset = new ArrayList<>(),pDataset = new ArrayList<>(),npDataset = new ArrayList<>();
    private DatabaseReference reference;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater  menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        
        switch (item.getItemId())
        {
            case R.id.menu_logout:
            {

                FirebaseAuth.getInstance().signOut();

            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        fragmentManager = getSupportFragmentManager();
        viewId();




        note_viewmodel = new ViewModelProvider(this).get(appoinment_viewmodel.class);


        pager_adaptor = new ViewPager_Adaptor(fragmentManager);
        pager_adaptor.addFragment(new Profile_Fragment(),"Profile Fragment");
        pager_adaptor.addFragment(new MakeAppoinments_Fragment(),"Make Appoinments");
        pager_adaptor.addFragment(new ViewAppoinments_Fragment(),"View Appoinments");
        viewPager.setAdapter(pager_adaptor);
        tabLayout.setupWithViewPager(viewPager);




       mAuth = FirebaseAuth.getInstance();
       mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
           @Override
           public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
               FirebaseUser user = firebaseAuth.getCurrentUser();
               if(user==null)
               {
                   note_viewmodel.deleteAll();
                   startActivity(new Intent(LandingPage.this,MainActivity.class));
                   finish();
               }
               else
               {
                   String uid = user.getUid();
                   databaseReference = FirebaseDatabase.getInstance().getReference("Appoinments").child(uid);
                   DatabaseReference ref = FirebaseDatabase.getInstance().getReference("docsList");
                   ref.addValueEventListener(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot snapshot) {
                           MakeAppoinments_Fragment.docs.clear();
                           MakeAppoinments_Fragment.docsuid.clear();
                           for(DataSnapshot d:snapshot.getChildren())
                           { MakeAppoinments_Fragment.docs.add(d.getValue(String.class));
                            MakeAppoinments_Fragment.docsuid.add(d.getKey());
                           }
                           MakeAppoinments_Fragment.adapter_.notifyDataSetChanged();
                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError error) {

                       }
                   });

                   databaseReference.addValueEventListener(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot snapshot) {
                           note_viewmodel.deleteAll();
                           for(DataSnapshot ds:snapshot.getChildren())
                           {
                               appoinment apn_ = ds.getValue(appoinment.class);
                               Log.e("sign in",ds.getValue().toString());
                               appoinment_entity apn = new appoinment_entity(apn_.doc,apn_.patient,apn_.email,apn_.symptoms,apn_.date,0 );
                               note_viewmodel.insert(apn);
                           }
                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError error) {

                       }
                   });



               }
               
           }
       });






    }

    private void viewId() {
        tabLayout = findViewById(R.id.landingpage_tablayout);
        viewPager = findViewById(R.id.landingpage_viewpager);
    }
}