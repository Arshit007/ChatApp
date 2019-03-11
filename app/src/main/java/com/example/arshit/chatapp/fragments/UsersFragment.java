package com.example.arshit.chatapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arshit.chatapp.Adapters.UserAdapter;
import com.example.arshit.chatapp.AllUserActivity;
import com.example.arshit.chatapp.Message;
import com.example.arshit.chatapp.R;
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



public class UsersFragment extends Fragment  {


    private RecyclerView userrv;
    private  ArrayList<User> userdata;
  private UserAdapter userAdapter;
  private DatabaseReference database;
  private FirebaseUser firebaseUser;
public UsersFragment(){

}
    private RecyclerView all_user_rv;
  private FirebaseAuth mAuth;
private  String cuurentUserId;



String imageURL;
   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users, container, false);
//
       userrv = view.findViewById(R.id.userecyclerview);
       userrv.setHasFixedSize(true);
       userrv.setLayoutManager(new LinearLayoutManager(getContext()));

       userdata = new ArrayList<>();

       database = FirebaseDatabase.getInstance().getReference("Users");

//       database.addValueEventListener(new ValueEventListener() {
//           @Override
//           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//
//               if (dataSnapshot.hasChild("imageURL"))
//
//                 imageURL = dataSnapshot.child("imageURL").getValue().toString();
//
////               Toast.makeText(getContext(), imageURL, Toast.LENGTH_SHORT).show();
//
//           }
//
//           @Override
//           public void onCancelled(@NonNull DatabaseError databaseError) {
//
//           }
//       });

       display();





//       all_user_rv.setHasFixedSize(true);



       mAuth = FirebaseAuth.getInstance();
       cuurentUserId = mAuth.getCurrentUser().getUid();


       database = FirebaseDatabase.getInstance().getReference().child("Users");

       all_user_rv = (RecyclerView)view.findViewById(R.id.userecyclerview);
       all_user_rv.setLayoutManager(new LinearLayoutManager(getContext()));


       return  view;

    }



    private void display(){

       firebaseUser =FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference().child("Users");

   database.addValueEventListener(new ValueEventListener() {

       @Override
       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                  userdata.clear();

           for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {

                    User user = postSnapshot.getValue(User.class);
                    assert user != null;
                    assert firebaseUser != null;

                if (!user.getId().equals(firebaseUser.getUid())){
                    userdata.add(user);
                }

           }

            userAdapter = new UserAdapter(getContext(),userdata,imageURL);
           userrv.setAdapter(userAdapter);

       }

       @Override
       public void onCancelled(@NonNull DatabaseError databaseError) {

       }
   });


   }





//    @Override
//    public void onStart() {
//        super.onStart();
//
//
////
//        database = FirebaseDatabase.getInstance().getReference().child("Users");
////        database.keepSynced(true);
//
//        FirebaseRecyclerOptions<User> options = new FirebaseRecyclerOptions.Builder<User>()
//                .setQuery(database, User.class).build();
//
//        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<User, UsersFragment.UserViewHolder>(options) {
//
//
//            @Override
//            protected void onBindViewHolder(@NonNull final UsersFragment.UserViewHolder holder, int position, @NonNull final User user) {
//
//
//
//                final String selected_user_id = getRef(position).getKey();
//
//
//
//                database.child(selected_user_id).addValueEventListener(new ValueEventListener() {
//                    @Override
//
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
////                            if (dataSnapshot.hasChild("imageURL")) {
////
//
//
//
//                            final String userImage = dataSnapshot.child("imageURL").getValue().toString();
//
//                            String id = dataSnapshot.child("id").getKey();
//                            String username = dataSnapshot.child("username").getValue().toString();
//                            String status = dataSnapshot.child("status").getValue().toString();
//
//                            holder.uName.setText(username);
//                            holder.uStatus.setText(status);
//                            Picasso.get().load(userImage).networkPolicy(NetworkPolicy.OFFLINE)
//                                    .placeholder(R.drawable.profile_avatar_small).into(holder.profileImg, new Callback() {
//                                @Override
//                                public void onSuccess() {
//
//                                }
//
//                                @Override
//                                public void onError(Exception e) {
//
//                                    Picasso.get().load(userImage).placeholder(R.drawable.profile_avatar_small).into(holder.profileImg);
//                                }
//                            });
//
//
//
//                                        if (user.getId().equals(cuurentUserId)){
//                                            Toast.makeText(getContext(), "working", Toast.LENGTH_SHORT).show();
//
////                                            holder.uName.setVisibility(View.INVISIBLE);
////                                            holder.uStatus.setVisibility(View.INVISIBLE);
////                                            holder.profileImg.setVisibility(View.INVISIBLE);
//
//                                           holder.layout.setVisibility(View.INVISIBLE);
//
//                                        }
//
//                                    //
////                                    holder.getAdapterPosition(user.getId()).setVisibility(View.INVISIBLE);
////                                    holder.uStatus.setVisibility(View.INVISIBLE);
//
//
//
////                            }
//
//
////                        else {
////
////
////                            String username = dataSnapshot.child("username").getValue().toString();
////                            String status = dataSnapshot.child("status").getValue().toString();
////
////                            holder.uName.setText(username);
////                            holder.uStatus.setText(status);
////
////
////                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//
//
//
//
//
//
////                Picasso.get().load(user.getProfilePic()).placeholder(R.drawable.profile_avatar_small).into(holder.profileImg);
//
////                Set event for component
//                holder.mView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
////                                Intent profileIntent = new Intent(AllUserActivity.this, ProfileActivity.class);
////                                profileIntent.putExtra("selected_user_id", selected_user_id);
////                                startActivity(profileIntent);
//
//                        Intent intent = new Intent(getContext(),Message.class);
//                        intent.putExtra("userid",user.getId());
//                        startActivity(intent);
//                    }
//                });
//
//            }
//
//
//
//            @NonNull
//            @Override
//            public UsersFragment.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
//
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.userdisplay, parent, false);
//
//                UserViewHolder viewHolderm = new UserViewHolder(view);
//                return viewHolderm;
//
//            }
//
//        } ;
//
//        all_user_rv.setAdapter(adapter);
//        adapter.startListening();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//    }



//    public static class UserViewHolder extends RecyclerView.ViewHolder {
//        public ImageView profileImg;
//        private LinearLayout layout;
//         LinearLayout.LayoutParams params;
//        TextView uName,uStatus;
//        View  mView;
//
//        public UserViewHolder(View itemView) {
//            super(itemView);
//            mView = itemView;
//
//            layout =(LinearLayout)mView.findViewById(R.id.displayLinearLayout);
//
//
//            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT);
//
//
//            uName = mView.findViewById(R.id.uname);
//            uStatus = mView.findViewById(R.id.ustatus);
//            profileImg = mView.findViewById(R.id.userdiaplayimg1);
//        }
//
//
//        private void Layout_hide() {
//            params.height = 0;
//// itemView.setLayoutParams(params); //This One.
//            layout.setLayoutParams(params);   //Or This one.
//
//        }
////
//    }


}