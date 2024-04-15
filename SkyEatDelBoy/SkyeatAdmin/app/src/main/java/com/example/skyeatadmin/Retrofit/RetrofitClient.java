package com.example.skyeatadmin.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient
{
    private static RetrofitClient instance = null;
    private Api myApi;

   // String BASE_URL="https://skynetitsolutions.com/Android_Register_login/";
    String BASE_URL="https://skynetitsolutions.com/skyeat_flutter/";
    public RetrofitClient()
    {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myApi = retrofit.create(Api.class);
    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public Api getMyApi() {
        return myApi;
    }
}
