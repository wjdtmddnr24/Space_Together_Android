package com.example.spacetogether.util;

import com.example.spacetogether.data.Restaurant;
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

    @GET("user/{id}")
    Call<Result<User>> getUser(@Path("id") String userId, @Query("token") String token);

    @POST("user/{id}")
    Call<Result<String>> setUser(@Path("id") String userId, @Query("token") String token, @Body User user);

    @GET("friend")
    Call<Result<List<User>>> getFriendsUsers(@Query("token") String token);

    @GET("friend/request")
    Call<Result<List<User>>> getRequestFriendsUsers(@Query("token") String token);

    @POST("friend/request/{id}")
    Call<Result<String>> requestFriend(@Path("id") String userId, @Query("token") String token);

    @POST("friend/accept/{id}")
    Call<Result<String>> acceptFriend(@Path("id") String userId, @Query("token") String token);

    @POST("restaurant/recommend")
    Call<Result<List<Restaurant>>> recommendRestaurant(@Query("token") String token, @Body List<User> users);

    @POST("restaurant/select/{id}")
    Call<Result<String>> selectRestaurant(@Path("id") String restaurantId, @Query("token") String token, @Body List<User> users);


}
