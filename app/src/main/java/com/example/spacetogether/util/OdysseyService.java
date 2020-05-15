package com.example.spacetogether.util;

import com.example.spacetogether.data.Result;

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
    Call<Result> login(@Field("id") String id, @Field("pw") String password);

    @FormUrlEncoded
    @POST("auth/signup")
    Call<Result> signup(@Field("id") String id, @Field("pw") String password, @Field("username") String username);

}
