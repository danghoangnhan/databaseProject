package com.naman14.timber.Service;

import com.google.gson.JsonObject;
import com.naman14.timber.models.Download;
import com.naman14.timber.models.Playlist;
import com.naman14.timber.models.Song;

import com.naman14.timber.models.Tune;
import com.naman14.timber.models.authentication;
import com.squareup.okhttp.ResponseBody;

import java.util.ArrayList;
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
    @GET("DownloadTuneFile")
    Call<ResponseBody> downloadTuneFile(@Query("Id")long tuneId);
    @GET("DownloadSongFile")
    Call<ResponseBody> downloadSongFile(@Query("songId") int songId);
    @POST("getRandomTune")
    Call<ArrayList<Tune>> getRandomTune(@Query("number") int number);

    Call<List<Tune>> DownloadTuneFile(int tuneId);
}
