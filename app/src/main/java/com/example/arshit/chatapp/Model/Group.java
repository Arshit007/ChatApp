package com.example.arshit.chatapp.Model;

import java.sql.Time;
import java.util.Date;

public class Group {

    private String message;
    private String name;
//    private Date date;
//    private Time time;

    public Group() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public Date getDate() {
//        return date;
//    }
//
//    public void setDate(Date date) {
//        this.date = date;
//    }
//
//    public Time getTime() {
//        return time;
//    }
//
//    public void setTime(Time time) {
//        this.time = time;
//    }

    public Group(String message, String name) {
        this.message = message;
        this.name = name;
//        this.date = date;
//        this.time = time;
    }
}


