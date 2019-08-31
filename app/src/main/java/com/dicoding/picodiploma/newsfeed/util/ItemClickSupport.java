package com.dicoding.picodiploma.newsfeed.util;

import android.view.View;

import com.dicoding.picodiploma.newsfeed.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemClickSupport {
    private RecyclerView recyclerView;
    private OnItemClickListener onItemClickListener;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (onItemClickListener!= null) {
                RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(v);
                onItemClickListener.onItemClicked(recyclerView, holder.getAdapterPosition(), v);
            }
        }
    };

    private RecyclerView.OnChildAttachStateChangeListener attachStateChangeListener =
            new RecyclerView.OnChildAttachStateChangeListener() {
                @Override
                public void onChildViewAttachedToWindow(@NonNull View view) {
                    if (onItemClickListener!=null){
                        view.setOnClickListener(mOnClickListener);
                    }
                }

                @Override
                public void onChildViewDetachedFromWindow(@NonNull View view) {

                }
    };

    private ItemClickSupport(RecyclerView recyclerView){
        this.recyclerView=recyclerView;
        this.recyclerView.setTag(R.id.item_click_support,this);
        this.recyclerView.addOnChildAttachStateChangeListener(attachStateChangeListener);
    }

    public static ItemClickSupport addTo(RecyclerView recyclerView){
        ItemClickSupport support=(ItemClickSupport)recyclerView.getTag(R.id.item_click_support);
        if (support==null)
            support=new ItemClickSupport(recyclerView);
        return support;
    }

    public static ItemClickSupport removeFrom(RecyclerView recyclerView){
        ItemClickSupport support = (ItemClickSupport)recyclerView.getTag(R.id.item_click_support);
        if (support!=null)
            support.detach(recyclerView);
        return support;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener=listener;
    }

    private void detach(RecyclerView recyclerView){
        recyclerView.removeOnChildAttachStateChangeListener(attachStateChangeListener);
        recyclerView.setTag(R.id.item_click_support, null);
    }

    public interface OnItemClickListener{
        void onItemClicked(RecyclerView recyclerView, int position, View v);
    }
}
