package com.dicoding.picodiploma.newsfeed.view;

import com.dicoding.picodiploma.newsfeed.model.News;

import java.util.ArrayList;

public interface NewsView {
    void getDataSuccess(ArrayList<News> listData);
    void getDataFailure();
    void searchDataSuccess(ArrayList<News> searchRes);
}
