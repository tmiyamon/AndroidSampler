package com.tmiyamon.androidsampler.login;

import java.util.Map;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by tmiyamon on 12/15/14.
 */
public interface ParseService {
    @GET("/1/login")
    Map<String, String> login(@Query("username") String username, @Query("password") String password);

    @POST("/1/users")
    @Headers("Content-Type: application/json")
    Map<String, String> signup(@Body Map<String, String> body);
}
