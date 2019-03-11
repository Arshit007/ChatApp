package com.example.arshit.chatapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arshit.chatapp.Message;
import com.example.arshit.chatapp.Model.Chat;
import com.example.arshit.chatapp.Model.Group;

//import com.example.arshit.chatapp.R;

import com.example.arshit.chatapp.Model.User;
import com.example.arshit.chatapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class GroupChatAdapter extends RecyclerView.Adapter<GroupChatAdapter.GroupChatViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    private Context context;

    private  ArrayList<Group> groupData;
    FirebaseUser firebaseUser;
    private  String imageurl;


    public GroupChatAdapter(Context context, ArrayList<Group> groupData) {
        this.context = context;
        this.groupData = groupData;

    }


    @NonNull
    @Override
    public GroupChatAdapter.GroupChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == MSG_TYPE_LEFT) {
            View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_chat_item_left, parent, false);
            return new GroupChatViewHolder(mView);
        }
        else {
            View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_chat_item_right, parent, false);
            return new GroupChatViewHolder(mView);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull GroupChatViewHolder holder, int position) {

        Group group = groupData.get(position);


        holder.show_msg.setText(group.getMessage());
        holder.group_name_display.setText(group.getName());
//


//        Toast.makeText(context, "from adapter"+group.getMessage(), Toast.LENGTH_SHORT).show();


    }

    @Override
    public int getItemCount() { return groupData.size(); }


    public class GroupChatViewHolder extends RecyclerView.ViewHolder {
        public TextView show_msg,group_name_display;
        public ImageView profileimg;
       LinearLayout linear_layout_group_chat;

        public GroupChatViewHolder(View itemView) {
            super(itemView);

            group_name_display = itemView.findViewById(R.id.group_chat_name_display);
            show_msg = itemView.findViewById(R.id.group_msg);


//            show_msg = itemView.findViewById(R.id.chat_msg);
            //            profileimg = itemView.findViewById(R.id.user_diaplay_img);

        }

    }

    @Override
    public int getItemViewType(int position) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        
//        Toast.makeText(context, groupData.get(position).getName(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(context, firebaseUser.getUid(), Toast.LENGTH_SHORT).show();

    if (!groupData.get(position).getName().equals(firebaseUser.getUid())) {
        
        
//    if (groupData.get(position).getName().equals(user.getUsername())) {
        return MSG_TYPE_LEFT;
    }

           return MSG_TYPE_RIGHT;

    }
}
