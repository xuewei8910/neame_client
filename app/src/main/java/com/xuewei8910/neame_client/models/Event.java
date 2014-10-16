package com.xuewei8910.neame_client.models;

import java.util.List;

/**
 * Created by Wei on 2014/10/13.
 */
public class Event {
    private long id;
    private User author;
    private String title;
    private String content;
    private List<Comment> comments;
    private double longitude;
    private double latitude;
    private String createTime;

    public Event(){}

    public Event(long id, String title, String content, User author, double longitude,
                 double latitude, String createTime, List<Comment> comments){
        this.id = id;
        this.title = title;
        this.author = author;
        this.content = content;
        this.latitude = latitude;
        this.longitude = longitude;
        this.createTime = createTime;
        this.comments = comments;
    }

    public long getId(){
        return this.id;
    }

    public String getContent(){
        return this.content;
    }

    public User getAuthor(){
        return this.author;
    }

    public String getTitle(){
        return this.title;
    }

    public List<Comment> getComments(){
        return this.comments;
    }

    public double getLongitude(){
        return this.longitude;
    }

    public double getLatitude(){
        return this.latitude;
    }

    public String getCreateTime(){
        return this.createTime;
    }
}
