package com.example.bhuhospital.Screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bhuhospital.R;
import com.example.bhuhospital.Screen.Fragments_LandingPage.MakeAppoinments_Fragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener {

    //layout set
    //option view change functionality
    //firebase side work

    private EditText et_name,et_email,et_pass,et_field;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        viewID();
        btn.setOnClickListener(AdminActivity.this);

    }

    private void viewID() {
        et_name = findViewById(R.id.admin_name);
        et_email = findViewById(R.id.admin_email);
        et_pass = findViewById(R.id.admin_password);
        et_field = findViewById(R.id.admin_field);
        btn = findViewById(R.id.admin_act_addbtn);
    }


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.admin_act_addbtn)
        {
            String name,email,pass,field;
            name = et_name.getText().toString().trim();
            email = et_email.getText().toString().trim();
            pass = et_pass.getText().toString().trim();
            field = et_field.getText().toString().trim();

            if(email.length()==0 || pass.length()<=6)
            {
                Toast.makeText(this, "email >=0 and password >6", Toast.LENGTH_SHORT).show();
                return;
            }
            else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            {
                Toast.makeText(this, "please enter a valid email", Toast.LENGTH_SHORT).show();
                return;
            }
            String sp = name+"--->"+field;
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("docsList");
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        ref.child(FirebaseAuth.getInstance().getUid()).setValue(sp).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    MakeAppoinments_Fragment.docs.add(sp);

                                }
                                else
                                {
                                    Toast.makeText(AdminActivity.this, "Not added try again", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        FirebaseAuth.getInstance().signOut();

                    }
                    else
                    {
                        Toast.makeText(AdminActivity.this, "try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });



        }
    }
}