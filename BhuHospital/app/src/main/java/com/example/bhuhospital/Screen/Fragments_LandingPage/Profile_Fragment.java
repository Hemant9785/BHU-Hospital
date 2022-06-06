package com.example.bhuhospital.Screen.Fragments_LandingPage;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bhuhospital.MainActivity;
import com.example.bhuhospital.R;
import com.example.bhuhospital.Screen.LandingPage;
import com.example.bhuhospital.Screen.signin_activity;
import com.example.bhuhospital.firebase_.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.theme.MaterialComponentsViewInflater;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Locale;


public class Profile_Fragment extends Fragment {

    private View rootView;
    private EditText et_name,et_email,et_age,et_bg,et_gender;
    public static int a = 0;
    private Button edit_btn;
    User userprofile;
    DatabaseReference reference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_profile_, container, false);

        viewId();

        disable(et_name);
        disable(et_email);
        disable(et_age);
        disable(et_bg);
        disable(et_gender);
        showProfile();
        reference = FirebaseDatabase.getInstance().getReference("Users");



        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(a==0)
                {
                    edit_btn.setText("Save Changes");
                    enableAll();
                    a=1;

                }
                else
                {
//                    pre process the data
                    String  name,email,age,gender,bg;
                    name = et_name.getText().toString().trim();
                    email = et_email.getText().toString().trim();
                    age = et_age.getText().toString().trim();
                    gender = et_gender.getText().toString().trim();
                    bg = et_bg.getText().toString().trim();
                    if(preChecking(name,email,gender,age,"123456",bg))
                        return ;
                    a = 0;
                    edit_btn.setText("Edit Profile");
                    saveChanges();

                    HashMap<String,Object>hM = new HashMap<>();
                    hM.put("name",name);
                    hM.put("age",age);
                    hM.put("blood_group",bg);
                    hM.put("gender",gender);
                    hM.put("email",email);
                    reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(hM).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                User up = new User(name,email,age,bg,gender);
                                MainActivity.user = up;
                                showProfile();
                                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                                String userId = mAuth.getCurrentUser().getUid().toString();
                                reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        User uProfile = snapshot.getValue(User.class);
                                        MainActivity.user  = uProfile;
                                        showProfile();

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });



                            }
                            else
                            {
                                showProfile();
                                Toast.makeText(getContext(),  "Changes Not Saved", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    disable(et_age);
                    disable(et_bg);
                    disable(et_name);
                    disable(et_gender);


                }

            }
        });
        return rootView;
    }
    private void enableAll()
    {
        et_name.setInputType(InputType.TYPE_CLASS_TEXT);
        et_gender.setInputType(InputType.TYPE_CLASS_TEXT);
        et_age.setInputType(InputType.TYPE_CLASS_NUMBER);
        et_bg.setInputType(InputType.TYPE_CLASS_TEXT);
    }
    private boolean saveChanges()
    {
        Toast.makeText(getContext(), "Save Changes Called", Toast.LENGTH_SHORT).show();



        return true;
    }
    public boolean checkLengths(String s, EditText e)
    {
        if(s.isEmpty())
        {
            e.setError("Field Required");
            e.requestFocus();
            return true;
        }
        return false;
    }
    public boolean preChecking(String sname,String semail,String sgender,String sage,String spassword,String sbg)
    {
        if(checkLengths(sname,et_name)) return true;
        else if(checkLengths(semail,et_email)) return true;
//        else if(checkLengths(spassword,password)) return true;
        else if(checkLengths(sage,et_age)) return true;
        else if(checkLengths(sbg,et_bg)) return true;
        else if(checkLengths(sgender,et_gender)) return true;



        if(!(sgender.equals("male") || sgender.equals("female")))
        {
            et_gender.setError("Male/Female");
            et_gender.requestFocus();
            return true;
        }
        sage.toLowerCase(Locale.ROOT);
        Integer intValue;
        try {

            intValue = Integer.parseInt(sage);

        } catch (NumberFormatException e) {
            et_age.setError("Valid age");
            et_age.requestFocus();
            return true;
        }

        if(intValue<=0 || intValue>110 )
        {
            et_age.setError("not in valid range");
            et_age.requestFocus();
            return true;
        }
        return false;
    }

    private void disable(EditText et)
    {
        et.setInputType(InputType.TYPE_NULL);
    }
    private void showProfile()
    {
        userprofile = MainActivity.user;
        if(userprofile==null)
            return;
        et_name.setText(userprofile.name);
        et_age.setText(userprofile.age);
        et_bg.setText(userprofile.blood_group);
        et_gender.setText(userprofile.gender);
        et_email.setText(userprofile.email);


    }
    private void viewId() {
        et_name = rootView.findViewById(R.id.profile_fragment_name);
        et_email = rootView.findViewById(R.id.profile_fragment_email);
        et_age = rootView.findViewById(R.id.profile_fragment_age);
        et_bg = rootView.findViewById(R.id.profile_fragment_bg);
        et_gender = rootView.findViewById(R.id.profile_fragment_gender);
        edit_btn = rootView.findViewById(R.id.profile_fragment_editbtn);
    }
}