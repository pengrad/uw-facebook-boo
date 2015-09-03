package com.github.pengrad.uw_facebook_boo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.pengrad.uw_facebook_boo.recyclerview.ItemClickListener;
import com.github.pengrad.uw_facebook_boo.recyclerview.RecyclerViewHolder;
import com.github.pengrad.uw_facebook_boo.recyclerview.RecyclerViewListAdapter;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * stas
 * 9/2/15
 */
public class FeedRecyclerAdapter extends RecyclerViewListAdapter<FeedData.Post> {

    public FeedRecyclerAdapter(ItemClickListener<FeedData.Post> itemClickListener) {
        super(itemClickListener);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_post, parent, false);
        return new ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerViewHolder<FeedData.Post> {

        @Bind(R.id.imageView) ImageView mImageView;
        @Bind(R.id.text_title) TextView mTextTitle;
        @Bind(R.id.text_comments) TextView mTextComments;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBindItem(FeedData.Post item) {
            mTextTitle.setText(item.message);
            mTextComments.setText(item.getCommentsCount() + "");
            Picasso.with(itemView.getContext()).load(item.full_picture).into(mImageView);
        }
    }
}
