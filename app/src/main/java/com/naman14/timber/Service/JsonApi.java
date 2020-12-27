package com.naman14.timber.Service;

import com.google.gson.JsonObject;
import com.naman14.timber.models.Playlist;
import com.naman14.timber.models.Song;
import com.naman14.timber.models.authentication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface JsonApi {
    @POST("login")
    Call<authentication> login(@Query("username") String username, @Query("password") String password);
    @POST("register")
    Call<authentication> register(@Body JsonObject register_body);
    @GET("getSongByPlayListId")
    Call<Song> getSongByPlayListId(@Query("Id") int id);
    @POST("getPlaylist")
    Call<List<Playlist>> getPlaylist(@Body JsonObject getPlaylist_body);
}
