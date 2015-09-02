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

/**
 * stas
 * 9/2/15
 */
public class FeedRecyclerAdapter extends RecyclerViewListAdapter<FeedData.Post> {

    public FeedRecyclerAdapter(ItemClickListener<FeedData.Post> itemClickListener) {
        super(itemClickListener);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_post, parent, false);
        return new VH(view);
    }

    public static class VH extends RecyclerViewHolder<FeedData.Post> {

        ImageView mImageView;
        TextView mTextTitle, mTextComments;

        public VH(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.imageView);
            mTextTitle = (TextView) itemView.findViewById(R.id.text_title);
            mTextComments = (TextView) itemView.findViewById(R.id.text_comments);
        }

        @Override
        public void onBindItem(FeedData.Post item) {
            mTextTitle.setText(item.message);
            mTextComments.setText("" + item.getCommentsCount());
            Picasso.with(itemView.getContext()).load(item.picture).into(mImageView);
        }
    }
}
