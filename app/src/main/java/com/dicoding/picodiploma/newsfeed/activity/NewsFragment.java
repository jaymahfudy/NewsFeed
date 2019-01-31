package com.dicoding.picodiploma.newsfeed.activity;

import android.net.rtp.RtpStream;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dicoding.picodiploma.newsfeed.BuildConfig;
import com.dicoding.picodiploma.newsfeed.ItemClickSupport;
import com.dicoding.picodiploma.newsfeed.R;
import com.dicoding.picodiploma.newsfeed.adapter.RvNewsAdapter;
import com.dicoding.picodiploma.newsfeed.object.ListNews;
import com.dicoding.picodiploma.newsfeed.object.News;
import com.dicoding.picodiploma.newsfeed.retrofit.NewsService;
import com.dicoding.picodiploma.newsfeed.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends Fragment {
    private RecyclerView rvNews;
    private ProgressBar pbNews;
    private ArrayList<News> listData = new ArrayList<>();
    private ArrayList<News> searchRes = new ArrayList<>();
    private RvNewsAdapter rvNewsAdapter = new RvNewsAdapter();

    static final String TAG_NEWS_URL = "NEWS_URL";

    public NewsFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        pbNews = view.findViewById(R.id.pb_news);
        rvNews = view.findViewById(R.id.rv_news);
        rvNews.setHasFixedSize(true);
        String sourcesId = null;
        if (getArguments() != null) { sourcesId = getArguments().getString(SourcesFragment.NEWS_SOURCES_ID); }
        fetchData(sourcesId);
        return  view;
    }

    private void showRvNews(){
        rvNews.setLayoutManager(new LinearLayoutManager(getContext()));
        rvNewsAdapter.setListNews(listData);
        rvNews.setAdapter(rvNewsAdapter);
        ItemClickSupport.addTo(rvNews).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                WebFragment webFragment = new WebFragment();
                Bundle urlArg = new Bundle();
                urlArg.putString(TAG_NEWS_URL, listData.get(position).getUrl());
                webFragment.setArguments(urlArg);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction;
                if (fragmentManager != null) {
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.main_container, webFragment, WebFragment.class.getSimpleName());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        });
    }

    private void fetchData(String source){
        listData = new ArrayList<>();
        NewsService service = RetrofitClient.getClient().create(NewsService.class);
        final Call<ListNews> listNews = service.listNews(source, BuildConfig.API_KEY);
        listNews.enqueue(new Callback<ListNews>() {
            @Override
            public void onResponse(@NonNull Call<ListNews> call,@NonNull Response<ListNews> response) {
                if (response.body() != null) { listData.addAll(response.body().getListNews());}
                showRvNews();
                setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<ListNews> call,@NonNull Throwable t) {
                setVisibility(View.VISIBLE);
                showToast(R.string.check_ur_connection);
            }
        });
    }

    private void searchData(String title){
        Pattern pattern = Pattern.compile(title);
        Matcher matcher;
        searchRes.clear();
        for (News news:listData){
            matcher = pattern.matcher(news.getTitle());
            if (matcher.find()){
                searchRes.add(news);
            }
        }
        rvNewsAdapter.setListNews(searchRes);
        rvNewsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!query.isEmpty()){
                    searchData(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void setVisibility(int rvVisibility){
        pbNews.setVisibility(View.GONE);
        rvNews.setVisibility(rvVisibility);
    }

    private void showToast(int resId){
        Toast.makeText(getContext(), resId, Toast.LENGTH_SHORT).show();
    }
}
