package com.dicoding.picodiploma.newsfeed;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CvNewsAdapter extends RecyclerView.Adapter<CvNewsAdapter.CvHolder> {
    private ArrayList<News>listNews;

    CvNewsAdapter() {

    }

    private ArrayList<News> getListNews() {
        return listNews;
    }

    void setListNews(ArrayList<News> listNews) {
        this.listNews = listNews;
    }

    @NonNull
    @Override
    public CvNewsAdapter.CvHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_news,parent, false);
        return new CvHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CvNewsAdapter.CvHolder holder, int position) {
        News news = getListNews().get(position);
        holder.tvTitle.setText(news.getTitle());
        holder.tvDate.setText(news.getPublishedAt().substring(0,10));
        Picasso.get().load(news.getUrlToImage()).resize(120,60).into(holder.imNews);
    }

    @Override
    public int getItemCount() {
        return listNews.size();
    }

    class CvHolder extends RecyclerView.ViewHolder {
        ImageView imNews;
        TextView tvTitle, tvDate;
        CvHolder(@NonNull View itemView) {
            super(itemView);
            imNews = itemView.findViewById(R.id.image_news);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDate = itemView.findViewById(R.id.tv_date);
        }
    }
}
