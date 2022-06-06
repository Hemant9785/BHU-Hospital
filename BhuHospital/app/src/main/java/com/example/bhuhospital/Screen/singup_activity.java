package com.example.bhuhospital.Screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.bhuhospital.MainActivity;
import com.example.bhuhospital.R;
import com.example.bhuhospital.firebase_.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class singup_activity extends AppCompatActivity implements View.OnClickListener{
    FirebaseAuth mAuth;
    EditText name,email,password,age,gender,blood_grp;
    Button register;
    ProgressBar prgbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);
        mAuth = FirebaseAuth.getInstance();
        viewIds();
        register.setOnClickListener(this);
    }

    private void viewIds() {
        name = findViewById(R.id.signup_name);
        email = findViewById(R.id.signup_email);
        gender = findViewById(R.id.signup_gender);
        password =findViewById(R.id.singup_password);
        blood_grp = findViewById(R.id.singup_bg);
        age = findViewById(R.id.singup_age);
        prgbar = findViewById(R.id.signup_prgbar);
        register = findViewById(R.id.singup_register_btn);


    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.singup_register_btn:{
                String sname,semail,sgender,sage,spassword,sbg;
                sname = name.getText().toString().trim();
                semail = email.getText().toString().trim();
                sgender = gender.getText().toString().trim().toLowerCase(Locale.ROOT);
                spassword = password.getText().toString().trim();
                sbg = blood_grp.getText().toString().trim();
                sage = age.getText().toString().trim();
                prgbar.setVisibility(View.VISIBLE);
//                pre checking
                if(preChecking(sname,semail,sgender,sage,spassword,sbg)) {
                    prgbar.setVisibility(View.GONE);
                    return;
                }

//firebase register code
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(semail,spassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
//                           String name,String email,String age,String blood_group,String gender
                            MainActivity.user  = new User(sname,semail,sage,sbg,sgender);
                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(MainActivity.user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        prgbar.setVisibility(View.GONE);
                                        Toast.makeText(singup_activity.this, "SUCESSFULLY SIGNED IN ", Toast.LENGTH_LONG).show();
                                        prgbar.setVisibility(View.GONE);
                                        Intent i  = new Intent(singup_activity.this,LandingPage.class);
                                        startActivity(i);

                                    }
                                    else
                                    {

                                        Toast.makeText(singup_activity.this, "SIGN UP FAILED , TRY AGAIN ", Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(singup_activity.this, "SIGN UP FAILED , TRY AGAIN ", Toast.LENGTH_LONG).show();
                        }
                        prgbar.setVisibility(View.GONE);
                    }
                });




            }
        }
    }

    public boolean preChecking(String sname,String semail,String sgender,String sage,String spassword,String sbg)
    {
        if(checkLengths(sname,name)) return true;
        else if(checkLengths(semail,email)) return true;
        else if(checkLengths(spassword,password)) return true;
        else if(checkLengths(sage,age)) return true;
        else if(checkLengths(sbg,blood_grp)) return true;
        else if(checkLengths(sgender,gender)) return true;

        if(!Patterns.EMAIL_ADDRESS.matcher(semail).matches())
        {
            email.setError("Valid Email Required");
            email.requestFocus();
            return true;
        }
        else if(spassword.length() < 6)
        {
            password.setError("Minimum 6 length password required");;
            password.requestFocus();
            return true;
        }
        else if(!(sgender.equals("male") || sgender.equals("female")))
        {
            gender.setError("Male/Female");
            gender.requestFocus();
            return true;
        }
        sage.toLowerCase(Locale.ROOT);
        Integer intValue;
        try {

            intValue = Integer.parseInt(sage);

        } catch (NumberFormatException e) {
            age.setError("Valid age");
            age.requestFocus();
            return true;
        }

    if(intValue<=0 || intValue>110 )
    {
        age.setError("not in valid range");
        age.requestFocus();
        return true;
    }
    return false;
    }
    public boolean checkLengths(String s,EditText et)
    {
        if(s.length()==0)
        {
            et.setError("field required");
            et.requestFocus();
            return true;
        }
        return false;
    }
}