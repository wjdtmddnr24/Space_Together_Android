package com.example.spacetogether.util;

import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;
//    public static final String SERVER_URL = "http://192.168.0.10:3000";//http://115.68.221.40:3000/
    public static final String SERVER_URL = "http://115.68.221.40:3000/";

    private RetrofitClient() {
    }

    public static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(SERVER_URL).addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                    .setDateFormat("E, dd MMMM yyyy HH:mm:ss X")
                    .create())).build();
        }
        return retrofit;
    }

    public static OdysseyService getOdysseyService(){
        return getInstance().create(OdysseyService.class);
    }
}
