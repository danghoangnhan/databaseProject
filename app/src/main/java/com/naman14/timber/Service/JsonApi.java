package com.naman14.timber.Service;
import com.naman14.timber.activities.authentication;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface JsonApi {
    @POST("login")
    Call<authentication> login(@Query("username") String username, @Query("password") String password);
    @POST("register")
    Call<authentication> register(@Query("username") String username, @Query("password") String password);
}
