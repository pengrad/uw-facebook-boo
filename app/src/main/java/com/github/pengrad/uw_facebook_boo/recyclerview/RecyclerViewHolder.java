package com.github.pengrad.uw_facebook_boo.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * stas
 * 7/23/15
 */
public abstract class RecyclerViewHolder<T> extends RecyclerView.ViewHolder {

    public RecyclerViewHolder(View itemView) {
        super(itemView);
    }

    public void onBind(final T item, final ItemClickListener<T> itemClickListener) {
        if (itemClickListener != null) {
            super.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClick(item);
                }
            });
        }
        onBindItem(item);
    }

    public abstract void onBindItem(T item);
}
