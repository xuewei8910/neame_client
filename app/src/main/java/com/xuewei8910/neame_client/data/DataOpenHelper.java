package com.xuewei8910.neame_client.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.xuewei8910.neame_client.models.Comment;
import com.xuewei8910.neame_client.models.Event;
import com.xuewei8910.neame_client.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wei on 2014/10/15.
 */
public class DataOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "nearme";
    private static final String USER_TABLE_NAME = "user";
    private static final String EVENT_TABLE_NAME = "event";
    private static final String COMMENT_TABLE_NAME  = "comment";

    public DataOpenHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table = "CREATE TABLE " + USER_TABLE_NAME +
                        "(ID INTEGER, " +
                        "USERNAME TEXT, " +
                        "EMAIL TEXT, " +
                        "FIRSTNAME TEXT, " +
                        "LASTNAME TEXT);";
        db.execSQL(create_table);
        create_table =  "CREATE TABLE " + EVENT_TABLE_NAME +
                        "(ID INTEGER, " +
                        "TITLE TEXT, " +
                        "CONTENT TEXT, " +
                        "AUTHOR INTEGER, " +
                        "CREATETIME TEXT, " +
                        "LATITUDE REAL, " +
                        "LONGITUDE REAL);";
        db.execSQL(create_table);
        create_table = "CREATE TABLE " + COMMENT_TABLE_NAME +
                        "(ID INTEGER, " +
                        "EVENTID INTEGER, " +
                        "AUTHOR INTEGER, " +
                        "CONTENT TEXT, " +
                        "CREATETIME TEXT);";
        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addEvent(Event e){
        if(getUser(e.getAuthor().getId()) == null){
            addUser(e.getAuthor());
        }
        for (Comment c:e.getComments()){
            addComment(c);
        }
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("ID",e.getId());
        values.put("TITLE", e.getTitle());
        values.put("CONTENT",e.getContent());
        values.put("AUTHOR", e.getAuthor().getId());
        values.put("LONGITUDE",e.getLongitude());
        values.put("LATITUDE", e.getLatitude());
        values.put("CREATETIME",e.getCreateTime());

        db.insert(this.EVENT_TABLE_NAME,null,values);
        db.close();
    }

    public Event getEvent(long id){
        List<Comment> commentList = getCommentList(id);
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(this.EVENT_TABLE_NAME, new String[] {
                "ID", "TITLE","CONTENT","AUTHOR","LONGITUDE","LATITUDE","CREATETIME"},
                "ID=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null){
            cursor.moveToFirst();
        }else{
            return null;
        }

        return new Event(cursor.getLong(0),
                cursor.getString(1),
                cursor.getString(2),
                getUser(cursor.getLong(3)),
                cursor.getDouble(4),
                cursor.getDouble(5),
                cursor.getString(6),
                commentList);
    }

    public List<Event> getEventList(){
        List<Event> eventList = new ArrayList<Event>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+this.EVENT_TABLE_NAME, null);

        if(cursor != null){
            cursor.moveToFirst();
            do {
                List<Comment> commentList = getCommentList(cursor.getLong(0));
                Event event = new Event(cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        getUser(cursor.getLong(3)),
                        cursor.getDouble(4),
                        cursor.getDouble(5),
                        cursor.getString(6),
                        commentList);
                eventList.add(event);
            } while (cursor.moveToNext());
        }
        return eventList;
    }

    public User getUser(long id){
        //TODO
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(this.USER_TABLE_NAME, new String[] {
                "ID", "USERNAME", "EMAIL", "FIRSTNAME", "LASTNAME"},
                "ID=?", new String[] {String.valueOf(id)},null,null,null);

        if (cursor != null){
            cursor.moveToFirst();
        }else {
            return null;
        }

        return new User(cursor.getLong(0),cursor.getString(1),cursor.getString(2),
                        cursor.getString(3),cursor.getString(4));
    }

    public void addUser(User u){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("ID",u.getId());
        values.put("USERNAME",u.getUsername());
        values.put("EMAIL",u.getEmail());
        values.put("FIRSTNAME",u.getFirst_name());
        values.put("LASTNAME",u.getLast_name());

        db.insert(this.USER_TABLE_NAME, null, values);
        db.close();
    }

    public void addComment(Comment c){
        if( getUser(c.getAuthor().getId()) == null){
            addUser(c.getAuthor());
        }
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("ID",c.getId());
        values.put("EVENTID",c.getEventId());
        values.put("AUTHOR",c.getAuthor().getId());
        values.put("CONTENT",c.getContent());
        values.put("CREATETIME",c.getCreatetime());

        db.insert(this.COMMENT_TABLE_NAME,null,values);
        db.close();
    }

    public Comment getComment(long id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(this.COMMENT_TABLE_NAME,new String[] {
                "ID", "EVENTID", "AUTHOR", "COTENT", "CREATETIME"},
                "ID=?",new String[] { String.valueOf(id)}, null, null, null);
        if (cursor != null){
            cursor.moveToFirst();
        }else {
            return null;
        }

        return new Comment(cursor.getLong(0),
                cursor.getLong(1),
                getUser(cursor.getLong(2)),
                cursor.getString(3),
                cursor.getString(4)
                );
    }

    public List<Comment> getCommentList(long id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(this.COMMENT_TABLE_NAME,new String[] {
                        "ID", "EVENTID", "AUTHOR", "COTENT", "CREATETIME"},
                "EVENTID=?",new String[] { String.valueOf(id)}, null, null, null);
        List<Comment> commentList = new ArrayList<Comment>();
        if (cursor != null){
            cursor.moveToFirst();
            do{
                Comment comment = new Comment(cursor.getLong(0),
                        cursor.getLong(1),
                        getUser(cursor.getLong(2)),
                        cursor.getString(3),
                        cursor.getString(4)
                );
                commentList.add(comment);
            } while (cursor.moveToNext());
        }
        return commentList;
    }
}
