package com.rasel.newsviews.api;

import com.rasel.newsviews.model.GoogleNewsResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {

    @GET("top-headlines")
    Call<GoogleNewsResponse> getNews(
            @Query("sources") String source,
            @Query("apiKey") String apiKey
    );

    @GET("{number}/date")
    Call<ResponseBody> getNumberText(@Path("number") String number);
}