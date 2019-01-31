package com.dicoding.picodiploma.newsfeed.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListSources {
    @SerializedName("sources")
    private List<Sources> listSources;

    public ListSources(List<Sources> listSources) {
        this.listSources = listSources;
    }

    public List<Sources> getListSources() {
        return listSources;
    }
}
