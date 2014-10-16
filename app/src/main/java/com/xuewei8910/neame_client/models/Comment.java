package com.xuewei8910.neame_client.models;

/**
 * Created by Wei on 2014/10/13.
 */
public class Comment {
    private long id;
    private long eventId;
    private User author;
    private String content;
    private String createtime;

    public Comment(long id, long eventId, User u, String content, String createtime){
        this.id = id;
        this.eventId = eventId;
        this.author = u;
        this.content = content;
    }

    public long getId(){
        return this.id;
    }

    public long getEventId(){
        return this.eventId;
    }

    public User getAuthor(){
        return this.author;
    }

    public String getContent(){
        return this.content;
    }

    public String getCreatetime(){
        return this.createtime;
    }
}
