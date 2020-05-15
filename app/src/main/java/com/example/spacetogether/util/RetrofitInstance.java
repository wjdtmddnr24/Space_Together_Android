package com.example.spacetogether.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit retrofit;

    private RetrofitInstance() {
    }

    public static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl("http://115.68.221.40:3000/").addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
