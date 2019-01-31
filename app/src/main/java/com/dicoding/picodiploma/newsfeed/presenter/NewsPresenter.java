package com.dicoding.picodiploma.newsfeed.presenter;

import com.dicoding.picodiploma.newsfeed.BuildConfig;
import com.dicoding.picodiploma.newsfeed.model.ListNews;
import com.dicoding.picodiploma.newsfeed.model.News;
import com.dicoding.picodiploma.newsfeed.retrofit.NewsService;
import com.dicoding.picodiploma.newsfeed.retrofit.RetrofitClient;
import com.dicoding.picodiploma.newsfeed.view.NewsView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsPresenter {
    private NewsView newsView;
    private ArrayList<News> listData = new ArrayList<>();
    private ArrayList<News> searchRes = new ArrayList<>();

    public NewsPresenter(NewsView newsView){
        this.newsView = newsView;
    }

    public void fetchData(String source){
        listData = new ArrayList<>();
        NewsService service = RetrofitClient.getClient().create(NewsService.class);
        final Call<ListNews> listNews = service.listNews(source, BuildConfig.API_KEY);
        listNews.enqueue(new Callback<ListNews>() {
            @Override
            public void onResponse(@NonNull Call<ListNews> call, @NonNull Response<ListNews> response) {
                if (response.body() != null) { listData.addAll(response.body().getListNews());}
                newsView.getDataSuccess(listData);
            }

            @Override
            public void onFailure(@NonNull Call<ListNews> call,@NonNull Throwable t) {
                newsView.getDataFailure();
            }
        });
    }

    public void searchData(String title){
        Pattern pattern = Pattern.compile(title);
        Matcher matcher;
        searchRes.clear();
        for (News news:listData){
            matcher = pattern.matcher(news.getTitle());
            if (matcher.find()){
                searchRes.add(news);
            }
        }
        newsView.searchDataSuccess(searchRes);
    }
}
