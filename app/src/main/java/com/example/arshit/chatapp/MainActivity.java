package com.example.arshit.chatapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.v4.app.FragmentPagerAdapter;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.arshit.chatapp.fragments.ChatsFragment;
import com.example.arshit.chatapp.fragments.StoryFragment;
import com.example.arshit.chatapp.fragments.UsersFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    CircleImageView profileimg;
   private TextView username;
ViewPager viewPager;
TabLayout tabLayout;
   FirebaseUser firebaseUser;
    DatabaseReference dbrfer;
Toolbar toolbar;
FirebaseAuth mAuth;
private     DatabaseReference Rootref;
    private int[] tabIcons = {
            R.drawable.ic_add_circle_black_24dp,
            R.drawable.ic_camera_alt_black_24dp,
            R.drawable.ic_add_circle_black_24dp
    };

    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//       setSupportActionBar(toolbar);
//          getSupportActionBar().setTitle("");

//        profileimg = findViewById(R.id.profilePic);
//
//        username = findViewById(R.id.usernameMain);

//        mtablayout();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        dbrfer = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
          Rootref = FirebaseDatabase.getInstance().getReference();



//           dbrfer.addValueEventListener(new ValueEventListener() {
//               @Override
//               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//
//                   User user = dataSnapshot.getValue(User.class);
//
////                   Toast.makeText(MainActivity.this,"WOrkingggggg" , Toast.LENGTH_SHORT).show();
//                   username.setText(user.getUsername());
////
////                   if (user.getProfilePic().equals("default")){
////
////                       profileimg.setImageResource(R.mipmap.ic_launcher);
////                   }
////
////                   else {
////
////                       Glide.with(getApplicationContext()).load(user.getProfilePic()).into(profileimg);
////                   }
//
//               }
//
//               @Override
//               public void onCancelled(@NonNull DatabaseError databaseError) {
//
//               }
//           });

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
//
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);
       ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());


   viewPagerAdapter.addFragment(new ChatsFragment(),"Groups");
viewPagerAdapter.addFragment(new UsersFragment(),"Users");
        viewPagerAdapter.addFragment(new StoryFragment(),"Story");


//
//   viewPagerAdapter.addFragment(new UsersFragment(),"Users");


viewPager.setAdapter(viewPagerAdapter);
tabLayout.setupWithViewPager(viewPager);
//        setupTabIcons();

    
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, SignIn.class));
                finish();
                return true;

            case R.id.accc_setting:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));

                return true;


            case R.id.all_Users:
                startActivity(new Intent(MainActivity.this, AllUserActivity.class));

                return true;

            case R.id.create_group:

           new_group();
                return true;
        }

        return true;

    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

  class ViewPagerAdapter extends FragmentPagerAdapter {

            private final  ArrayList<Fragment> mfragments;
            private final  ArrayList<String> titles;

            ViewPagerAdapter(FragmentManager  fragmentManager){
                super(fragmentManager);
               this.mfragments = new ArrayList<>();
               this.titles = new ArrayList<>();
               }

        @Override
        public Fragment getItem(int position) {
            return mfragments.get(position);
        }


            @Override
            public int getCount() {
                return mfragments.size();
            }

            public void addFragment(Fragment fragment, String title) {

                mfragments.add(fragment);
                titles.add(title);
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return titles.get(position);
            }

        }


        private void new_group() {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,R.style.AlertDialog);

            builder.setTitle("Enter Group Name : ");

            final EditText groupNameField = new EditText(MainActivity.this);
            groupNameField.setHint("E.g Fantastic Four");
            builder.setView(groupNameField);

            builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {


                    String groupName = groupNameField.getText().toString();

                    if (TextUtils.isEmpty(groupName)){

                        Toast.makeText(MainActivity.this, "Please Write group ", Toast.LENGTH_SHORT).show();
                    }

                    else {


                        CreateNewGroup(groupName);
                    }

                }
            });


            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    dialogInterface.cancel();
                }
            });

            builder.show();
        }

        private void CreateNewGroup(final String groupName){


        Rootref.child("Groups").child(groupName).setValue("").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){

                    Toast.makeText(MainActivity.this, groupName+"  is created", Toast.LENGTH_SHORT).show();
                }

            }
        });

        }
}



