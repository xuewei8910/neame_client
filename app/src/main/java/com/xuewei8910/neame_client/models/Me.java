package com.xuewei8910.neame_client.models;

/**
 * Created by Wei on 2014/10/14.
 */
public class Me extends User {
    private static Me instance = null;
    private String password;

    protected Me(){
        super();
    }

    public static synchronized Me getInstance(){
        if (instance == null){
            instance = new Me();
        }

        return instance;
    }

    public void  setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return this.password;
    }
}
