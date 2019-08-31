package com.dicoding.picodiploma.newsfeed.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.dicoding.picodiploma.newsfeed.R;
import com.dicoding.picodiploma.newsfeed.adapter.RvNewsAdapter;
import com.dicoding.picodiploma.newsfeed.model.News;
import com.dicoding.picodiploma.newsfeed.presenter.NewsPresenter;
import com.dicoding.picodiploma.newsfeed.util.ItemClickSupport;
import com.dicoding.picodiploma.newsfeed.util.ViewUtil;
import com.dicoding.picodiploma.newsfeed.view.NewsView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NewsFragment extends Fragment implements NewsView {
    private RecyclerView rvNews;
    private ProgressBar pbNews;
    private RvNewsAdapter rvNewsAdapter = new RvNewsAdapter();
    private final NewsPresenter newsPresenter = new NewsPresenter(this);
    static final String TAG_NEWS_URL = "NEWS_URL";

    public NewsFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        ViewUtil.showBackButton(getContext());
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        pbNews = view.findViewById(R.id.pb_news);
        rvNews = view.findViewById(R.id.rv_news);
        rvNews.setHasFixedSize(true);
        String sourcesId = null;
        if (getArguments() != null) { sourcesId = getArguments().getString(SourcesFragment.NEWS_SOURCES_ID); }
        newsPresenter.fetchData(sourcesId);
        return  view;
    }

    private void showRvNews(final ArrayList<News> listData){
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
                    newsPresenter.searchData(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void getDataSuccess(ArrayList<News> listData) {
        showRvNews(listData);
        ViewUtil.setVisibility(pbNews, rvNews);
    }

    @Override
    public void getDataFailure() {
        ViewUtil.setVisibility(pbNews,rvNews);
        ViewUtil.showToast(getContext());
    }

    @Override
    public void searchDataSuccess(ArrayList<News> searchRes) {
        rvNewsAdapter.setListNews(searchRes);
        rvNewsAdapter.notifyDataSetChanged();
    }
}
