package com.example.arshit.chatapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arshit.chatapp.Message;
import com.example.arshit.chatapp.R;
import com.example.arshit.chatapp.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private Context context;
    private  ArrayList<User> userdata;
    private String imageURL;
    DatabaseReference database;
         String    image;

    public UserAdapter(Context context, ArrayList<User> userdata, String imageURL) {
        this.context = context;
        this.userdata = userdata;
        this.imageURL = imageURL;
    }



    @NonNull
    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.userdisplay, parent, false);
        return new UserViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, final int position) {


                final User user = userdata.get(position);
        holder.uname.setText(user.getUsername());
        holder.ustatus.setText(user.getStatus());


//        Picasso.get().load(imageURL).placeholder(R.drawable.profile_avatar_small).into(holder.profileImg);
//        Picasso.get().load(user.getProfilePic()).placeholder(R.drawable.profile_avatar_small).into(holder.profileImg);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context,Message.class);
                intent.putExtra("userid",user.getId());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() { return userdata.size(); }


    public class UserViewHolder extends RecyclerView.ViewHolder {
      public TextView uname,ustatus;
    public     ImageView profileImg;
        public UserViewHolder(View itemView) {
            super(itemView);

            profileImg = itemView.findViewById(R.id.userdiaplayimg1);
            ustatus = itemView.findViewById(R.id.ustatus);
            uname = itemView.findViewById(R.id.uname);

         }

        public void setUserImage(String profilePic) {

//            Picasso.with(itemView.getContext()).load(profilePic).placeholder(R.drawable.profile_avatar_small).into(profileimg);

//            Picasso.get().(itemView.getContext()).load(profilePic).into(profileimg);


        }
    }
}
