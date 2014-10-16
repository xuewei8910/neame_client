package com.xuewei8910.neame_client.models;

import android.provider.ContactsContract;

/**
 * Created by Wei on 2014/10/13.
 */
public class User {
    private long id;
    private String username;
    private String email;
    private String first_name;
    private String last_name;

    public User(long id, String username, String email, String first_name, String last_name){
        this.id = id;
        this.username = username;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public User(String username){
        this.username = username;
    }

    protected User(){
    }

    public String getUsername(){
        return username;
    }

    public long getId(){
        return id;
    }

    public String getEmail(){
        return this.email;
    }

    public String getFirst_name(){
        return this.first_name;
    }

    public String getLast_name(){
        return this.last_name;
    }
}
