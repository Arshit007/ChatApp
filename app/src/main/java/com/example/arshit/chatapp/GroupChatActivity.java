package com.example.arshit.chatapp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arshit.chatapp.Adapters.GroupChatAdapter;
import com.example.arshit.chatapp.Adapters.MessageAdapter;
import com.example.arshit.chatapp.Model.Chat;
import com.example.arshit.chatapp.Model.Group;
import com.example.arshit.chatapp.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.SimpleTimeZone;

public class GroupChatActivity extends AppCompatActivity {

   private String cuurentGroupname,currentUserID,currentUserName,currentDate,currentTime;

    TextView toolbarTitle,group_msg,displayName;
    private DatabaseReference databaseReference,GroupNameRef,GroupMessageKey;
    FirebaseAuth mAuth;

    ScrollView scrollView;
    RecyclerView  grp_rv;

    ImageButton group_chat_send_btn,group_chat_camera_btn;
    EditText group_chat_send_msg;

   TextView displayTextView;
   private ArrayList<Group> mGroup;
    GroupChatAdapter groupAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);


        intialise();

        getUserInfo();


        group_chat_send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SaveMessageInfoToDatabase();

                group_chat_send_msg.setText("");
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);

            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();

        GroupNameRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                 if (dataSnapshot.exists()){

                     DisplayMessage(dataSnapshot);
                 }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                if (dataSnapshot.exists()){

                    DisplayMessage(dataSnapshot);
                }

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private void SaveMessageInfoToDatabase(){

    String message = group_chat_send_msg.getText().toString();
        String messageKey = GroupNameRef.push().getKey();


        if (TextUtils.isEmpty(message)){

        Toast.makeText(this, "Empty message not allowed", Toast.LENGTH_SHORT).show();
    }

    else {

        Calendar ccalForDate = Calendar.getInstance();
        SimpleDateFormat cuurentDateFormat = new SimpleDateFormat("MMM dd,yyyy");
        currentDate = cuurentDateFormat.format(ccalForDate.getTime());


        Calendar ccalForTime = Calendar.getInstance();
        SimpleDateFormat cuurentTimeFormat = new SimpleDateFormat("hh:mm aa");
        currentTime = cuurentTimeFormat.format(ccalForTime.getTime());

        HashMap<String,Object> grpMsgKey = new HashMap<>();

        GroupNameRef.updateChildren(grpMsgKey);

        GroupMessageKey = GroupNameRef.child(messageKey);


        HashMap<String,Object> messageInfoMap = new HashMap<>();

        messageInfoMap.put("name",currentUserName);
            messageInfoMap.put("message",message);
            messageInfoMap.put("time",currentTime);
            messageInfoMap.put("date",currentDate);

            GroupMessageKey.updateChildren(messageInfoMap);




    }

    }


    private void intialise(){


        toolbarTitle = findViewById(R.id.toolbar_title);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getUid();



        grp_rv = findViewById(R.id.all_group_chat_rv);
        grp_rv.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        grp_rv.setLayoutManager(linearLayoutManager);

        cuurentGroupname = getIntent().getExtras().get("groupName").toString();

        group_chat_send_msg = findViewById(R.id.group_chat_send_msg);

//        displayTextView = findViewById(R.id.displayTextView);

//
        scrollView =findViewById(R.id.my_scroll_view);

        group_chat_send_btn = findViewById(R.id.group_chat_send_btn);

//        displayName = findViewById(R.id.displayName);
//        group_msg = findViewById(R.id.group_msg);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        GroupNameRef  = FirebaseDatabase.getInstance().getReference().child("Groups").child(cuurentGroupname);

        toolbar();
    }

    private void getUserInfo(){

      databaseReference.child(currentUserID).addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

              if (dataSnapshot.exists()){

              currentUserName = dataSnapshot.child("username").getValue().toString();


              }

          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
      });

      }





    private void toolbar(){


        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.user_toolbar);
//        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(cuurentGroupname);
toolbarTitle.setText(cuurentGroupname);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }


    private void DisplayMessage(DataSnapshot dataSnapshot)

    {

        mGroup = new ArrayList<>();


        GroupNameRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mGroup.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Group group = snapshot.getValue(Group.class);


//                    if(chat.getReceiver().equals(myid) &&  chat.getSender().equals(userid)||
//                            chat.getReceiver().equals(userid) && chat.getSender().equals(myid)){


//                    }}
                      mGroup.add(group);

                    groupAdapter = new GroupChatAdapter(GroupChatActivity.this,mGroup);
                    grp_rv.setAdapter(groupAdapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

//
//    private void DisplayMessage(DataSnapshot dataSnapshot){
//
//
//        Iterator iterator = dataSnapshot.getChildren().iterator();
//
//          while (iterator.hasNext()){
//
//              String chatDate = (String) ((DataSnapshot)iterator.next()).getValue();
//              String chatMessage = (String) ((DataSnapshot)iterator.next()).getValue();
//              String chatName = (String) ((DataSnapshot)iterator.next()).getValue();
//              String chatTime = (String) ((DataSnapshot)iterator.next()).getValue();
//
//
//            displayTextView.append(chatName + " : \n" + chatMessage +  "\n" + chatTime  +  "   " + chatDate +"\n\n\n");
//
////              displayName.append(chatName+"\n");
////              group_msg.append(chatMessage+"\n");
//
//              scrollView.fullScroll(ScrollView.FOCUS_DOWN);
//
//          }
//    }
}
