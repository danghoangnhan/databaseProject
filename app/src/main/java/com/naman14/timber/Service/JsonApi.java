package com.naman14.timber.Service;
import com.naman14.timber.models.Playlist;
import com.naman14.timber.models.Song;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface JsonApi {
    @POST("login")
    Call<authentication> login(@Query("usenamre") String username, @Query("password") String password);
    @POST("register")
    Call<authentication> register(@Query("username") String username, @Query("password") String password);
    @GET("getSongByPlayListId")
    Call<Song> getSongByPlayListId(@Query("Id") int id);
    @GET("getPlayList")
    Call<List<Playlist>> getPlaylist(@Query("userid") int id);
}
