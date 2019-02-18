package com.rasel.newsviews.api;

import com.rasel.newsviews.model.GoogleNewsResponse;

import java.util.List;

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

}