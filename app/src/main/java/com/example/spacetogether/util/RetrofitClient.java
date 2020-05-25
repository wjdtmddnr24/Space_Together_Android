package com.example.spacetogether.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;

    private RetrofitClient() {
    }

    public static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl("http://192.168.0.10:3000/").addConverterFactory(GsonConverterFactory.create()).build();
//            retrofit = new Retrofit.Builder().baseUrl("http://115.68.221.40:3000/").addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }

    public static OdysseyService getOdysseyService(){
        return getInstance().create(OdysseyService.class);
    }
}
