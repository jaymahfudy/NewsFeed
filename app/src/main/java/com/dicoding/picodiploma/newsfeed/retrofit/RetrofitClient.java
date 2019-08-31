package com.dicoding.picodiploma.newsfeed.retrofit;

import com.dicoding.picodiploma.newsfeed.BuildConfig;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;
    public static Retrofit getClient(){
        if(retrofit ==null){
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new LoggingInterceptor())
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.NEWS_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
