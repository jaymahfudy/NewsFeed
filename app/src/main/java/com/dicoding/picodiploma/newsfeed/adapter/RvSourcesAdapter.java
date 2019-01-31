package com.dicoding.picodiploma.newsfeed.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dicoding.picodiploma.newsfeed.R;
import com.dicoding.picodiploma.newsfeed.model.Sources;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RvSourcesAdapter extends RecyclerView.Adapter<RvSourcesAdapter.RvHolder> {
    private ArrayList<Sources> listSources;

    public RvSourcesAdapter(){

    }

    private ArrayList<Sources> getListSources(){
        return  listSources;
    }

    public void setListSources(ArrayList<Sources> listSources){
        this.listSources=listSources;
    }

    @NonNull
    @Override
    public RvHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_sources, parent,false);
        return new RvHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RvSourcesAdapter.RvHolder holder, int position) {
        Sources sources = getListSources().get(position);
        holder.tvName.setText(sources.getName());
        holder.tvDesc.setText(sources.getDescription());
    }

    @Override
    public int getItemCount() {
        return listSources.size();
    }

    public class RvHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDesc;
        RvHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvDesc = itemView.findViewById(R.id.tv_description);
        }
    }
}
