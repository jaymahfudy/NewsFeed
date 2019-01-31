package com.dicoding.picodiploma.newsfeed.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dicoding.picodiploma.newsfeed.BuildConfig;
import com.dicoding.picodiploma.newsfeed.ItemClickSupport;
import com.dicoding.picodiploma.newsfeed.R;
import com.dicoding.picodiploma.newsfeed.retrofit.RetrofitClient;
import com.dicoding.picodiploma.newsfeed.adapter.RvSourcesAdapter;
import com.dicoding.picodiploma.newsfeed.object.ListSources;
import com.dicoding.picodiploma.newsfeed.object.Sources;
import com.dicoding.picodiploma.newsfeed.retrofit.NewsService;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SourcesFragment extends Fragment {
    private RecyclerView rvSources;
    private ProgressBar pbSources;
    private ArrayList<Sources> list;
    public static final String NEWS_SOURCES_ID = "news_sources_id";

    public SourcesFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sources, container, false);
        pbSources = view.findViewById(R.id.pb_sources);
        rvSources = view.findViewById(R.id.rv_sources);
        rvSources.setHasFixedSize(true);
        fetchData();
        return view;
    }

    private void showRvSources(){
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

    private void fetchData(){
        list = new ArrayList<>();
        NewsService service = RetrofitClient.getClient().create(NewsService.class);
        final Call<ListSources> listSources = service.listSources("en", BuildConfig.API_KEY);
        listSources.enqueue(new Callback<ListSources>() {
            @Override
            public void onResponse(@NonNull Call<ListSources> call,@NonNull Response<ListSources> response) {
                if (response.body()!=null){ list.addAll(response.body().getListSources()); }
                showRvSources();
                setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<ListSources> call,@NonNull Throwable t) {
                setVisibility(View.VISIBLE);
                showToast(R.string.check_ur_connection);
            }
        });
    }

    private void setVisibility(int rvVisibility){
        pbSources.setVisibility(View.GONE);
        rvSources.setVisibility(rvVisibility);
    }

    private void showToast(int resId){
        Toast.makeText(getContext(), resId, Toast.LENGTH_SHORT).show();
    }
}
