package com.dicoding.picodiploma.newsfeed.presenter;

import com.dicoding.picodiploma.newsfeed.BuildConfig;
import com.dicoding.picodiploma.newsfeed.model.ListSources;
import com.dicoding.picodiploma.newsfeed.model.Sources;
import com.dicoding.picodiploma.newsfeed.retrofit.NewsService;
import com.dicoding.picodiploma.newsfeed.retrofit.RetrofitClient;
import com.dicoding.picodiploma.newsfeed.view.SourcesView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SourcesPresenter {
    private SourcesView view;
    private ArrayList<Sources> list;

    public SourcesPresenter(SourcesView view){
        this.view=view;
    }

    public void fetchData(){
        list = new ArrayList<>();
        NewsService service = RetrofitClient.getClient().create(NewsService.class);
        final Call<ListSources> listSources = service.listSources("en", BuildConfig.API_KEY);
        listSources.enqueue(new Callback<ListSources>() {
            @Override
            public void onResponse(@NonNull Call<ListSources> call, @NonNull Response<ListSources> response) {
                if (response.body()!=null){ list.addAll(response.body().getListSources()); }
                view.getDataSuccess(list);
            }

            @Override
            public void onFailure(@NonNull Call<ListSources> call,@NonNull Throwable t) {
                view.getDataFailure();
            }
        });
    }
}
