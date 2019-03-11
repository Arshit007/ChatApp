package com.example.arshit.chatapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AlertDialogLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.arshit.chatapp.Adapters.UserAdapter;
import com.example.arshit.chatapp.GroupChatActivity;
import com.example.arshit.chatapp.Model.Chat;
import com.example.arshit.chatapp.Model.User;
import com.example.arshit.chatapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class ChatsFragment extends Fragment {

    private View groupFragmentView;
    private ListView group_View;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list_of_group = new ArrayList<>();
      View view;
    private  DatabaseReference databaseReference;

    public ChatsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_chats, container, false);

databaseReference = FirebaseDatabase.getInstance().getReference().child("Groups");
intialise();

displayAllGroup();
group_View.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        String CurrentGroupName = adapterView.getItemAtPosition(i).toString();
        Intent intent = new Intent(getContext(),GroupChatActivity.class);
        intent.putExtra("groupName",CurrentGroupName);
        startActivity(intent);
}
});

return view;

    }

    private void displayAllGroup(){


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Set<String> set = new HashSet<>();
                Iterator iterator = dataSnapshot.getChildren().iterator();

                while (iterator.hasNext()){

                    set.add(((DataSnapshot)iterator.next()).getKey());

                }

                list_of_group.clear();
                list_of_group.addAll(set);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void intialise(){

        group_View =  view.findViewById(R.id.all_group_recyclerview);
        arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,list_of_group);
    group_View.setAdapter(arrayAdapter);

    }
}
