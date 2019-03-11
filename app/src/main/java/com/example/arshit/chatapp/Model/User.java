 package com.example.arshit.chatapp.Model;

import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

public class User {

    private String id;
    private String username;
    private String profilePic;
    private String status;

    public User(String id, String username, String profilePic, String status) {
        this.id = id;
        this.username = username;
        this.profilePic = profilePic;
        this.status = status;
    }

    public User()
    {

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePic() { return profilePic; }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
}
