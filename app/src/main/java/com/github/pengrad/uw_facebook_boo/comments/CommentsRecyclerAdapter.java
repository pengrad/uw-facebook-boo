package com.github.pengrad.uw_facebook_boo.comments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.pengrad.uw_facebook_boo.R;
import com.github.pengrad.uw_facebook_boo.utils.recyclerview.ItemClickListener;
import com.github.pengrad.uw_facebook_boo.utils.recyclerview.RecyclerViewHolder;
import com.github.pengrad.uw_facebook_boo.utils.recyclerview.RecyclerViewListAdapter;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Stas Parshin
 * 06 September 2015
 */
public class CommentsRecyclerAdapter extends RecyclerViewListAdapter<CommentsData.Comment> {

    public CommentsRecyclerAdapter(ItemClickListener<CommentsData.Comment> itemClickListener) {
        super(itemClickListener);
    }

    @Override
    public RecyclerViewHolder<CommentsData.Comment> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_comment, parent, false);
        return new ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerViewHolder<CommentsData.Comment> {
        @Bind(R.id.imageView) ImageView mImageUser;
        @Bind(R.id.text_user) TextView mTextUser;
        @Bind(R.id.text_message) TextView mTextMessage;
        @Bind(R.id.text_time) TextView mTextTime;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBindItem(CommentsData.Comment item) {
            mTextMessage.setText(item.message);
            mTextUser.setText(item.getUserName());
            mTextTime.setText(item.created_time);

            // picasso will cache image on disk
            Picasso.with(itemView.getContext()).load(item.getUserPicture()).into(mImageUser);
        }
    }
}
