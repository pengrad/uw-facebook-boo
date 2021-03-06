package com.github.pengrad.uw_facebook_boo.feed;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.pengrad.uw_facebook_boo.R;
import com.github.pengrad.uw_facebook_boo.utils.TextUtils;
import com.github.pengrad.uw_facebook_boo.utils.recyclerview.ItemClickListener;
import com.github.pengrad.uw_facebook_boo.utils.recyclerview.RecyclerViewHolder;
import com.github.pengrad.uw_facebook_boo.utils.recyclerview.RecyclerViewListAdapter;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * stas
 * 9/2/15
 */
public class FeedRecyclerAdapter extends RecyclerViewListAdapter<FeedData.Post> {

    private ItemClickListener<FeedData.Post> mImageClickListener;

    public FeedRecyclerAdapter(ItemClickListener<FeedData.Post> itemClickListener,
                               ItemClickListener<FeedData.Post> imageClickListener) {
        super(itemClickListener);
        this.mImageClickListener = imageClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_feed_post, parent, false);
        return new ViewHolder(view, mImageClickListener);
    }

    public static class ViewHolder extends RecyclerViewHolder<FeedData.Post> {

        @Bind(R.id.imageView) ImageView mImageView;
        @Bind(R.id.text_title) TextView mTextTitle;
        @Bind(R.id.text_likes) TextView mTextLikes;
        @Bind(R.id.text_comments) TextView mTextComments;
        @Bind(R.id.text_time) TextView mTextTime;

        @BindString(R.string.post_likes) String likesText;
        @BindString(R.string.post_comments) String commentsText;

        private ItemClickListener<FeedData.Post> mImageClickListener;

        public ViewHolder(View itemView, ItemClickListener<FeedData.Post> imageClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.mImageClickListener = imageClickListener;
        }

        @Override
        public void onBindItem(final FeedData.Post item) {
            String likes = TextUtils.formatNumber(item.getLikesCount());
            String comments = TextUtils.formatNumber(item.getCommentsCount());

            mTextLikes.setText(String.format(likesText, likes));
            mTextComments.setText(String.format(commentsText, comments));
            mTextTitle.setText(TextUtils.formatHashtags(item.message));
            mTextTime.setText(TextUtils.formatTime(item.created_time));

            // picasso will cache image on disk
            Picasso.with(itemView.getContext()).load(item.full_picture).into(mImageView);

            if (mImageClickListener != null) {
                mImageView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        mImageClickListener.onItemClick(item);
                    }
                });
            }
        }
    }
}
