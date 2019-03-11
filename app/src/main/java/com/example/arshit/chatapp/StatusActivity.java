package com.example.arshit.chatapp;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StatusActivity extends AppCompatActivity {

    TextInputLayout txtStatus;
    Button btnUpdate;
    private FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    ProgressDialog progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        toolbar();
           inti();
        updateStatus();
    }

    private void  updateStatus()
    {

        btnUpdate.setOnClickListener(new View.OnClickListener() {




            @Override
            public void onClick(View view) {

                progressBar = new ProgressDialog(StatusActivity.this);
                progressBar.setTitle("Saving Changes");
                progressBar.setMessage("Wait untill changes are saved");
                progressBar.show();

                String status = txtStatus.getEditText().getText().toString();

                databaseReference.child("status").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){

                             progressBar.dismiss();

                            Toast.makeText(StatusActivity.this, "Status Updated", Toast.LENGTH_SHORT).show();

                        }
                        else {

                            Toast.makeText(StatusActivity.this, "Error in making changes", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });

    }


    private void inti(){


        String status_value = getIntent().getStringExtra("status_value");

        txtStatus = findViewById(R.id.status_update);
        btnUpdate = findViewById(R.id.btnUpdate);

        progressBar = new ProgressDialog(this);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        txtStatus.getEditText().setText(status_value);


    }

    private void toolbar()
    {

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.user_toolbar);
        TextView title = findViewById(R.id.toolbar_title);
        title.setText("Account Status");

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


    }


}
