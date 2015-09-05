package com.github.pengrad.uw_facebook_boo;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.pengrad.uw_facebook_boo.story.DragDownLayout;
import com.github.pengrad.uw_facebook_boo.utils.recyclerview.RecyclerViewHolder;
import com.github.pengrad.uw_facebook_boo.utils.recyclerview.RecyclerViewListAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;


public class StoryActivity extends AppCompatActivity implements DragDownLayout.DragListener {

    @Bind(R.id.dragLayout) DragDownLayout mDragDownLayout;
    @Bind(R.id.cardview) CardView mCardView;
    @Bind(R.id.recycler_view) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        ButterKnife.bind(this);

        String[] items = new String[20];
        for (int i = 0; i < items.length; i++) {
            items[i] = "Item " + (i + 1);
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewListAdapter<String> adapter = new RecyclerViewListAdapter<String>(null) {
            @Override
            public RecyclerViewHolder<String> onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);

                return new RecyclerViewHolder<String>(view) {
                    @Override
                    public void onBindItem(String item) {
                        TextView textView = (TextView) itemView.findViewById(android.R.id.text1);
                        textView.setText(item);
                    }
                };
            }
        };
        adapter.addAll(items);
        mRecyclerView.setAdapter(adapter);
        mDragDownLayout.init(mCardView, this);
    }

    // Should drag layout process this event
    @Override
    public boolean shouldIntercept(MotionEvent event) {
        // allow only if can't scroll up comments
        return !ViewCompat.canScrollVertically(mRecyclerView, -1);
    }

    // Callback - view dragged to the end, finish without animation
    @Override
    public void onDragEnd() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    // back pressing finish, animate slide down
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.slide_down);
    }
}
