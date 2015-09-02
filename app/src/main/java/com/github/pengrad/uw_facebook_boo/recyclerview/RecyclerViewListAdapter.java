package com.github.pengrad.uw_facebook_boo.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * stas
 * 7/23/15
 */
public abstract class RecyclerViewListAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder<T>> {

    private ItemClickListener<T> itemClickListener;
    private List<T> data;

    public RecyclerViewListAdapter(ItemClickListener<T> itemClickListener) {
        this.itemClickListener = itemClickListener;
        setDataImpl(null);
    }

    private void setDataImpl(List<T> data) {
        this.data = data != null ? data : new ArrayList<T>(0);
    }

    public RecyclerViewListAdapter<T> setData(List<T> data) {
        setDataImpl(data);
        notifyDataSetChanged();
        return this;
    }

    public RecyclerViewListAdapter<T> add(T item) {
        data.add(item);
        notifyDataSetChanged();
        return this;
    }

    public RecyclerViewListAdapter<T> addAll(Collection<? extends T> items) {
        data.addAll(items);
        notifyDataSetChanged();
        return this;
    }

    public RecyclerViewListAdapter<T> addAll(T... items) {
        for (T item : items) {
            data.add(item);
        }
        notifyDataSetChanged();
        return this;
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public abstract RecyclerViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(RecyclerViewHolder<T> holder, int position) {
        holder.onBind(data.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
