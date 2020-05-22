package com.example.spacetogether.util;

import com.example.spacetogether.data.Result;
import com.example.spacetogether.data.User;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OdysseyService {
    @FormUrlEncoded
    @POST("auth/login")
    Call<Result<String>> login(@Field("id") String id, @Field("pw") String password);

    @FormUrlEncoded
    @POST("auth/signup")
    Call<Result<String>> signup(@Field("id") String id, @Field("pw") String password, @Field("username") String username);

    @POST("user/{id}")
    Call<Result<User>> getUser(@Path("id") String userId, @Query("token") String token);

    @FormUrlEncoded
    @POST("user/all")
    Call<Result<List<User>>> getUsers(@Query("token") String token, @Field("userIds") List<String> userIds);


}
