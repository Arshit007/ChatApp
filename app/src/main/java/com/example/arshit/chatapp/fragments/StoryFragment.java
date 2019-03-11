package com.example.arshit.chatapp.fragments;



import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.arshit.chatapp.Adapters.StoryAdapter;
import com.example.arshit.chatapp.Model.Story;
import com.example.arshit.chatapp.Model.User;


import com.example.arshit.chatapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class StoryFragment extends android.support.v4.app.Fragment {
    public StoryFragment() {
        // Required empty public constructor
    }

      private RecyclerView recyclerView_story,recyclerView;
//      private StoryAdapter storyAdapter;
       private List<String> followingList;
 private StoryAdapter storyAdapter;
    private List<Story> storyList;

    ProgressBar progressBar;
    View view;

        @Override
        public View onCreateView (LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState){
            // Inflate the layout for this fragment
           view = inflater.inflate(R.layout.fragment_story, container, false);

            recyclerView_story = view.findViewById(R.id.recycler_view_story);
            recyclerView_story.setHasFixedSize(true);

            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            llm.setOrientation(LinearLayoutManager.VERTICAL);

            recyclerView_story.setLayoutManager(llm);

            storyList = new ArrayList<>();
         storyAdapter = new StoryAdapter(getContext(), storyList);

            recyclerView_story.setAdapter(storyAdapter);

            progressBar = view.findViewById(R.id.progressBar);

            followingList =new ArrayList<>();


            intialise();

            return view;


        }

   private void intialise(){

       DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
//               .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//       Toast.makeText(getContext(), "ref"+reference, Toast.LENGTH_SHORT).show();

       reference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               followingList.clear();
               for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                   followingList.add(snapshot.getKey());

               }

               readStory();
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });
   }




    private void readStory(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Story");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long timecurrent = System.currentTimeMillis();
                storyList.clear();
                storyList.add(new Story("", 0, 0, "",
                        FirebaseAuth.getInstance().getCurrentUser().getUid()));

                for (String id : followingList) {

                     int countStory = 0;
                    Story story = null;

                    for (DataSnapshot snapshot : dataSnapshot.child(id).getChildren()) {
                       story = snapshot.getValue(Story.class);

                        if (timecurrent > story.getTimestart() && timecurrent < story.getTimeend()) {
                            countStory++;
                        }
                    }

                    if (countStory > 0){
                        storyList.add(story);
                    }
                }

                storyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
