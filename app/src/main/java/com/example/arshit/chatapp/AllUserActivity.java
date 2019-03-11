package com.example.arshit.chatapp;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arshit.chatapp.Adapters.UserAdapter;
import com.example.arshit.chatapp.Model.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllUserActivity extends AppCompatActivity {

    private RecyclerView all_user_rv;
    private DatabaseReference database;
//    private ArrayList<User> userdata;
//    private UserAdapter userAdapter;
String imageURL;

    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_user);

        all_user_rv = findViewById(R.id.all_user_recyclerview);
        all_user_rv.setHasFixedSize(false);
        all_user_rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

//        firebaseUser =FirebaseAuth.getInstance().getCurrentUser();
//        database = FirebaseDatabase.getInstance().getReference("Users");

//        firebaseUser =FirebaseAuth.getInstance().getCurrentUser();

        database = FirebaseDatabase.getInstance().getReference().child("Users");



//        userdata = new ArrayList<>();

        toolbar();
//          display();




    }




    private void toolbar()
    {

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.user_toolbar);
        TextView title = findViewById(R.id.toolbar_title);
        title.setText("All Users");
        toolbar.setTitle("Account Toolbar");
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

    @Override
    protected void onStart() {
        super.onStart();

        database = FirebaseDatabase.getInstance().getReference().child("Users");
        database.keepSynced(true);

        FirebaseRecyclerOptions<User> options = new FirebaseRecyclerOptions.Builder<User>()
                .setQuery(database, User.class).build();

        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<User, UserViewHolder>(options) {


            @Override
            protected void onBindViewHolder(@NonNull final UserViewHolder holder, int position, @NonNull final User user) {

                final String selected_user_id = getRef(position).getKey();

                database.child(selected_user_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.hasChild("imageURL")){

                            final String userImage = dataSnapshot.child("imageURL").getValue().toString();
                            String username = dataSnapshot.child("username").getValue().toString();
                            String status = dataSnapshot.child("status").getValue().toString();




                            holder.uName.setText(username);
                            holder.uStatus.setText(status);
                            Picasso.get().load(userImage).networkPolicy(NetworkPolicy.OFFLINE)
                                    .placeholder(R.drawable.profile_avatar_small).into(holder.profileImg, new Callback() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onError(Exception e) {

                                    Picasso.get().load(userImage).placeholder(R.drawable.profile_avatar_small).into(holder.profileImg);
                                }
                            });


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


//                Picasso.get().load(user.getProfilePic()).placeholder(R.drawable.profile_avatar_small).into(holder.profileImg);




//                Set event for component
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                                Intent profileIntent = new Intent(AllUserActivity.this, ProfileActivity.class);
//                                profileIntent.putExtra("selected_user_id", selected_user_id);
//                                startActivity(profileIntent);
                    }
                });

            }

            @NonNull
            @Override
            public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.userdisplay, parent, false);

                return new UserViewHolder(view);

            }

        } ;


        all_user_rv = findViewById(R.id.all_user_recyclerview);
        all_user_rv.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }



    public static class UserViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView profileImg;

        TextView uName,uStatus;
        View  mView;

        public UserViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            uName = mView.findViewById(R.id.uname);
            uStatus = mView.findViewById(R.id.ustatus);
            profileImg = mView.findViewById(R.id.userdiaplayimg1);
        }

    }



}
