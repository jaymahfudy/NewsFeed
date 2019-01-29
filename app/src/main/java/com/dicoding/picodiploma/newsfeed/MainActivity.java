package com.dicoding.picodiploma.newsfeed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvNews;
    private ArrayList<News> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvNews = findViewById(R.id.rv_news);
        rvNews.setHasFixedSize(true);
        list = new ArrayList<>();
        fetchData("the-verge");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void showRecyclerView(){
        rvNews.setLayoutManager(new LinearLayoutManager(this));
        CvNewsAdapter cvNewsAdapter = new CvNewsAdapter();
        cvNewsAdapter.setListNews(list);
        rvNews.setAdapter(cvNewsAdapter);
        ItemClickSupport.addTo(rvNews).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent i = new Intent(getApplicationContext(), DisplayNewsActivity.class);
                i.putExtra("NEWS_URL",list.get(position).getUrl());
                startActivity(i);
            }
        });
    }

    private void fetchData(String source){
        NewsService service = RetrofitClient.getClient().create(NewsService.class);
        final Call<ListNews> listNews = service.listNews(source, BuildConfig.API_KEY);
        listNews.enqueue(new Callback<ListNews>() {
            @Override
            public void onResponse(Call<ListNews> call, Response<ListNews> response) {
                Log.v("INI",response.toString());
                if (response.body() != null)
                    list.addAll(response.body().getListNews());
                showRecyclerView();
            }

            @Override
            public void onFailure(Call<ListNews> call, Throwable t) {

            }
        });
    }
}
