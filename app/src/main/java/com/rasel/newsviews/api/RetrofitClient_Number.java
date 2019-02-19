package com.rasel.newsviews.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient_Number {

    private static final String BASE_URL = "http://numbersapi.com/";
    private static RetrofitClient_Number retrofitClient;
    private Retrofit retrofit;

    private RetrofitClient_Number(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized RetrofitClient_Number getInstance(){
        if( retrofitClient==null){
            retrofitClient = new RetrofitClient_Number();
        }
        return retrofitClient;
    }

    public JsonPlaceHolderApi getApi(){
        return retrofit.create(JsonPlaceHolderApi.class);
    }
}
