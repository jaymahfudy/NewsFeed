package com.dicoding.picodiploma.newsfeed;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsService {
    @GET("articles")
    Call<ListNews> listNews(@Query("source") String sources, @Query("apiKey") String api);
}
