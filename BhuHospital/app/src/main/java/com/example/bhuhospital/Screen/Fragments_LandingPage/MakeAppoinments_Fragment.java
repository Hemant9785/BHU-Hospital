package com.example.bhuhospital.Screen.Fragments_LandingPage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bhuhospital.R;
import com.example.bhuhospital.Room.appoinment_entity;
import com.example.bhuhospital.Screen.LandingPage;
import com.example.bhuhospital.firebase_.appoinment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.ref.Reference;
import java.util.ArrayList;


public class MakeAppoinments_Fragment extends Fragment {

    private EditText et_doc,et_patient,et_email,et_date,et_symptoms;
    private Button apn_btn;
    private Spinner doc_spinner;
    public static ArrayList<String> docs = new ArrayList();
    public static ArrayList<String> docsuid = new ArrayList<>();
    public static ArrayAdapter<String>adapter_;


    String doc;
    int ind = 0;
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_make_appoinments_, container, false);
//        docs.add("Hemant Joshi--->Hair Transplant");
//        docs.add("Sonu Joshi--->Cardio Logist");
//        docs.add("Tarun Singh--->NeuroSurgen");
        viewId();
        adapter_= new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,docs);
        adapter_.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        doc_spinner.setAdapter(adapter_);
        doc_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                doc = docs.get(i);
                ind = i;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                doc = docs.get(0);
            }
        });


        apn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doc = docs.get(ind);
                String docId = docsuid.get(ind);
                for(int i=0;i<docs.size();i++)
                {
                    Log.e("docs",docs.get(i));
                }
                String patient,date,email,symptoms;
//                doc = et_doc.getText().toString().trim();
                patient = et_patient.getText().toString().trim();
                date = et_date.getText().toString().trim();
                email = et_email.getText().toString().trim();
                symptoms = et_symptoms.getText().toString().trim();


                appoinment a = new appoinment(doc,patient,email,date,symptoms);


                DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference(doc);
                DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Appoinments");
                reference2.child(FirebaseAuth.getInstance().getUid()).push().setValue(a).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            reference1.setValue(a).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        reference2.child(docId).push().setValue(a).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful())
                                                {
                                                    appoinment_entity note = new appoinment_entity(doc,patient,email,symptoms,date,0);
                                                }

                                            }
                                        });

//                                        LandingPage.note_viewmodel.insert(note);
                                    }
                                    else
                                    {
                                        Toast.makeText(getContext(), "Try Again", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(getContext(), "Try Again", Toast.LENGTH_SHORT).show();
                            return;

                        }
                    }
                });



                Toast.makeText(getContext(), "Added", Toast.LENGTH_LONG).show();
                et_date.setText("");
                et_email.setText("");
                et_symptoms.setText("");
                et_patient.setText("");;

            }
        });
        return rootView;
    }

    private void viewId() {
//        et_doc = rootView.findViewById(R.id.make_doctor_name);
        et_date = rootView.findViewById(R.id.make_date);
        et_email = rootView.findViewById(R.id.make_email);
        et_symptoms = rootView.findViewById(R.id.make_symptoms);
        et_patient = rootView.findViewById(R.id.make_patient_name);
        doc_spinner = rootView.findViewById(R.id.make_doctor_name);

        apn_btn = rootView.findViewById(R.id.make_apn_btn);
    }
}