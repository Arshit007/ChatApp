package com.example.arshit.chatapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {

    EditText username, inputEmail, inputPassword;

    TextView btnSignIn;
    private Button  btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    DatabaseReference dbreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        auth = FirebaseAuth.getInstance();

        btnSignIn = findViewById(R.id.sign_in_text);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);

        inputEmail = (EditText) findViewById(R.id.email1);
        username = (EditText) findViewById(R.id.username1);
        inputPassword = (EditText) findViewById(R.id.password1);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


//        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SignUp.this, SignIn.class));
            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                           final String username1 = username.getText().toString();

                if (TextUtils.isEmpty(username1)) {
                    Toast.makeText(getApplicationContext(), "Enter Username!", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 5) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 5 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                Toast.makeText(SignUp.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignUp.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {

//                                    Toast.makeText(SignUp.this, "hrll", Toast.LENGTH_SHORT).show();
                                    FirebaseUser firebaseUser =  auth.getCurrentUser();
                                    assert firebaseUser != null;
                                    String userid = firebaseUser.getUid();

                                    dbreference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                                    HashMap<String,String> hashMap = new HashMap<>();
                                    hashMap.put("id",userid);
                                    hashMap.put("username",username1);
                                    hashMap.put("imageURL","default");
                                    hashMap.put("status","I am using ChatApp");

                                    dbreference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful())
                                            {
                                          Intent intent = new Intent(SignUp.this,SignIn.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);

                                            finish();

                                           }
                                        }
                                    });




                                }
                            }
                        });

            }
        });


    }

        @Override
        protected void onResume() {
            super.onResume();
            progressBar.setVisibility(View.GONE);
        }

    }

