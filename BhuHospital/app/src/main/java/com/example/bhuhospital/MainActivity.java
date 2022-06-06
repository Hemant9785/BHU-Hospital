package com.example.bhuhospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bhuhospital.Screen.signin_activity;
import com.example.bhuhospital.Screen.singup_activity;
import com.example.bhuhospital.firebase_.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


//INS FOR SIGN IN
/*
* FirebaseAuth mAuth variable
* onCreate mai mAuth ka instance bnao
* onStart mai check kro null h ya nhi
* */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
//    public static User uesr = null;
    public static User user = null;
    private FirebaseAuth mAuth;
    //xml fields
    private Button signin,signup;
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user==null)
        {
            Toast.makeText(this, "NOT SIGNED IN", Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(this, "SIGNED IN ", Toast.LENGTH_SHORT).show();
        
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        viewId();
        signup.setOnClickListener(this);
        signin.setOnClickListener(this);


    }

    private void viewId() {
        signin = findViewById(R.id.main_activity_singin);
        signup = findViewById(R.id.main_activity_singup);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.main_activity_singup:{
                Intent intent = new Intent(this, singup_activity.class);
                startActivity(intent);
                break;

            }
            case R.id.main_activity_singin:{
                Intent intent = new Intent(this, signin_activity.class);
                startActivity(intent);
                break;
            }
        }
    }
}