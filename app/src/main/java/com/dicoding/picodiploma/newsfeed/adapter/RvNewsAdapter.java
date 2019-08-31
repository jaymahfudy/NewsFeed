package com.dicoding.picodiploma.newsfeed.adapter;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dicoding.picodiploma.newsfeed.R;
import com.dicoding.picodiploma.newsfeed.model.News;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RvNewsAdapter extends RecyclerView.Adapter<RvNewsAdapter.RvHolder> {
    private ArrayList<News> listNews;

    public RvNewsAdapter() {

    }

    private ArrayList<News> getListNews() {
        return listNews;
    }

    public void setListNews(ArrayList<News> listNews) {
        this.listNews = listNews;
    }

    @NonNull
    @Override
    public RvHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_news, parent, false);
        return new RvHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RvHolder holder, int position) {
        News news = getListNews().get(position);
        holder.tvTitle.setText(Html.fromHtml(news.getTitle()));
        holder.tvDate.setText(Html.fromHtml(news.getPublishedAt()));
        if (!news.getUrlToImage().isEmpty()){
            Picasso.get().load(news.getUrlToImage()).placeholder(R.drawable.ic_image).resize(120,60).into(holder.imNews);
        } else {
            holder.imNews.setImageResource(R.drawable.ic_image);
        }
    }

    @Override
    public int getItemCount() {
        return listNews.size();
    }

    class RvHolder extends RecyclerView.ViewHolder {
        ImageView imNews;
        TextView tvTitle, tvDate;
        RvHolder(@NonNull View itemView) {
            super(itemView);
            imNews = itemView.findViewById(R.id.image_news);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDate = itemView.findViewById(R.id.tv_date);
        }
    }
}
