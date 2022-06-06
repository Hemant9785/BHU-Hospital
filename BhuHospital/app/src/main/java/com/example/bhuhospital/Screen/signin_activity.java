package com.example.bhuhospital.Screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bhuhospital.MainActivity;
import com.example.bhuhospital.R;
import com.example.bhuhospital.firebase_.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class signin_activity extends AppCompatActivity implements View.OnClickListener {

    TextView txt_view;
    EditText email_et,password_et;
    Button sign_btn;
    FirebaseAuth mAuth;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        findViews();
        sign_btn.setOnClickListener(this);
    }


    private void findViews()
    {
        txt_view = findViewById(R.id.signin_activity_textView);
        email_et = findViewById(R.id.signin_activity_email);
        password_et = findViewById(R.id.signin_activity_password);
        sign_btn = findViewById(R.id.signin_activity_btn);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.signin_activity_btn:{
                String semail,spassword;
                semail = email_et.getText().toString().trim();
                spassword = password_et.getText().toString().trim();
                if(preProcess(semail,spassword))return ;
                Log.e("Authentication ",""+semail+" "+spassword);
                txt_view.setText("Wait......");
                mAuth.signInWithEmailAndPassword(semail,spassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            String userId = mAuth.getCurrentUser().getUid().toString();
                            reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    User userProfile = snapshot.getValue(User.class);
                                    if(userProfile!=null)
                                    {
                                        MainActivity.user = userProfile;
                                        Intent i = new Intent(signin_activity.this,LandingPage.class);
                                        startActivity(i);

                                    }
                                    else
                                    {
                                        Toast.makeText(signin_activity.this, "Failed , try Again", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(signin_activity.this, "Try Again else ", Toast.LENGTH_SHORT).show();
                        }
                        txt_view.setText("SIGN IN");
                    }
                });


            }
        }
    }

    private boolean preProcess(String semail, String spassword) {
        if(semail.isEmpty())
        {
            email_et.setError("field required !");
            email_et.requestFocus();
            return true;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(semail).matches())
        {
            email_et.setError("Not a valid email");
            email_et.requestFocus();
            return true;
        }
        if(spassword.isEmpty())
        {
            password_et.setError("field required ");
            password_et.requestFocus();
            return true;
        }
        if(spassword.length()<6)
        {
            password_et.setError("Min Length of Password is 6");
            password_et.requestFocus();
            return true;
        }

        return false;
    }
}