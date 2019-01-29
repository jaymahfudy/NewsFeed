package com.dicoding.picodiploma.newsfeed;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class RetrofitClient {
//    public static Retrofit getClient() {
//        return new Retrofit.Builder().
//                baseUrl(BuildConfig.NEWS_URL).
//                addConverterFactory(GsonConverterFactory.create())
//                .build();
//    }
    private static Retrofit RETROFIT = null;

    static Retrofit getClient(){
        if(RETROFIT==null){
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new LoggingInterceptor())
                    .build();
            RETROFIT = new Retrofit.Builder()
                    .baseUrl(BuildConfig.NEWS_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return RETROFIT;
    }
}
