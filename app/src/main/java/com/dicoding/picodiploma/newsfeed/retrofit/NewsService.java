package com.dicoding.picodiploma.newsfeed.retrofit;

import com.dicoding.picodiploma.newsfeed.object.ListNews;
import com.dicoding.picodiploma.newsfeed.object.ListSources;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsService {
    @GET("articles")
    Call<ListNews> listNews(@Query("source") String sources, @Query("apiKey") String api);

    @GET("sources")
    Call<ListSources> listSources(@Query("language") String language, @Query("apiKey") String api);
}
