package com.dicoding.picodiploma.newsfeed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvNews;
    private ArrayList<News> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvNews = findViewById(R.id.rv_news);
        rvNews.setHasFixedSize(true);
        list = new ArrayList<>();
        fetchData("the-verge");
        showRecyclerView();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void showRecyclerView(){
        rvNews.setLayoutManager(new LinearLayoutManager(this));
        CvNewsAdapter cvNewsAdapter = new CvNewsAdapter(this);
        cvNewsAdapter.setListNews(list);
        rvNews.setAdapter(cvNewsAdapter);
    }

    private void fetchData(String source){
        NewsService service = RetrofitClient.getClient().create(NewsService.class);
        Call<ListNews> listNews = service.listNews(source, BuildConfig.API_KEY);
        listNews.enqueue(new Callback<ListNews>() {
            @Override
            public void onResponse(Call<ListNews> call, Response<ListNews> response) {
                Log.v("INI",response.toString());
                if (response.body() != null) {
                    list.addAll(response.body().getListNews());
                }
                showRecyclerView();
            }

            @Override
            public void onFailure(Call<ListNews> call, Throwable t) {

            }
        });
    }
}
