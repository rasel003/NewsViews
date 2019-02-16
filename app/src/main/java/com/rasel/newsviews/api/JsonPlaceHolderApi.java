package com.rasel.newsviews.api;

import com.rasel.newsviews.model.GoogleNewsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {

    @GET("top-headlines?sources=google-news&apiKey=cc39eb8a0bf94781933a765ee91dd8a5")
    Call<GoogleNewsResponse> getNews();

}