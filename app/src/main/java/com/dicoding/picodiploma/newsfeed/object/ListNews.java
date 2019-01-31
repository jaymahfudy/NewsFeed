package com.dicoding.picodiploma.newsfeed.object;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListNews {
    @SerializedName("articles")
    private List<News> listNews;

    public ListNews(List<News> listNews) {
        this.listNews = listNews;
    }

    public List<News> getListNews() {
        return listNews;
    }
}
