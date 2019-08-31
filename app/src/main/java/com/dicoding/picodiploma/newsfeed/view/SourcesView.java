package com.dicoding.picodiploma.newsfeed.view;

import com.dicoding.picodiploma.newsfeed.model.Sources;

import java.util.ArrayList;

public interface SourcesView {
    void getDataSuccess(ArrayList<Sources> listData);
    void getDataFailure();
}
