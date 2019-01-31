package com.dicoding.picodiploma.newsfeed.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.dicoding.picodiploma.newsfeed.R;
import com.dicoding.picodiploma.newsfeed.adapter.RvSourcesAdapter;
import com.dicoding.picodiploma.newsfeed.model.Sources;
import com.dicoding.picodiploma.newsfeed.presenter.SourcesPresenter;
import com.dicoding.picodiploma.newsfeed.util.ItemClickSupport;
import com.dicoding.picodiploma.newsfeed.util.ViewUtil;
import com.dicoding.picodiploma.newsfeed.view.SourcesView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SourcesFragment extends Fragment implements SourcesView {
    private RecyclerView rvSources;
    private ProgressBar pbSources;
    public static final String NEWS_SOURCES_ID = "news_sources_id";
    private final SourcesPresenter sourcesPresenter = new SourcesPresenter(this);
    public SourcesFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sources, container, false);
        pbSources = view.findViewById(R.id.pb_sources);
        rvSources = view.findViewById(R.id.rv_sources);
        rvSources.setHasFixedSize(true);
        sourcesPresenter.fetchData();
        return view;
    }

    private void showRvSources(final ArrayList<Sources> list){
        rvSources.setLayoutManager(new LinearLayoutManager(getContext()));
        RvSourcesAdapter rvSourcesAdapter = new RvSourcesAdapter();
        rvSourcesAdapter.setListSources(list);
        rvSources.setAdapter(rvSourcesAdapter);
        ItemClickSupport.addTo(rvSources).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                NewsFragment newsFragment = new NewsFragment();
                Bundle idArg = new Bundle();
                idArg.putString(NEWS_SOURCES_ID, list.get(position).getId());
                newsFragment.setArguments(idArg);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction;
                if (fragmentManager != null) {
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.main_container, newsFragment, NewsFragment.class.getSimpleName());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        });
    }

    @Override
    public void getDataSuccess(ArrayList<Sources> listData) {
        showRvSources(listData);
        ViewUtil.setVisibility(pbSources, rvSources);
    }

    @Override
    public void getDataFailure() {
        ViewUtil.setVisibility(pbSources,rvSources);
        ViewUtil.showToast(getContext());
    }
}
