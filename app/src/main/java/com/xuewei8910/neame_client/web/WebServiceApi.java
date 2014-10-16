package com.xuewei8910.neame_client.web;


import com.xuewei8910.neame_client.models.Comment;
import com.xuewei8910.neame_client.models.Event;
import com.xuewei8910.neame_client.models.User;

import java.util.List;
import java.util.Map;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.Streaming;
import retrofit.mime.TypedFile;

/**
 * Created by Wei on 2014/10/13.
 */
public interface WebServiceApi {

    @GET("/event")
    public List<Event> getEventList(@Query("longitude") double longitude,
                                     @Query("latitude") double latitude);

    @GET("/event/{id}")
    public Event getEvent(@Path("id") long id);

    @GET("/event/{id}/comment")
    public List<Comment> getComments(@Path("id") long id);

    @Streaming
    @GET("/image/{id}/data")
    public Response getImage(@Path("id") long id);

    @POST("/event")
    public Event addEvent(@Body Event e);

    @POST("/event/{id}")
    public Event updateEvent(@Body Event e);

    @Multipart
    @POST("/image/{id}/data")
    public long setImageData(@Path("id") long id, @Part("data") TypedFile imageData);

    @POST("/event/{id}/comment")
    public Comment addComment(@Body Comment comment);

    @FormUrlEncoded
    @POST("/signup")
    public User signup(@FieldMap Map<String,String> fields);

}
