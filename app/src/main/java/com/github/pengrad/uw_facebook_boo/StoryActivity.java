/*
 * Copyright (c) 2014
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.pengrad.uw_facebook_boo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.pengrad.uw_facebook_boo.story.DragLayout;
import com.github.pengrad.uw_facebook_boo.utils.recyclerview.RecyclerViewHolder;
import com.github.pengrad.uw_facebook_boo.utils.recyclerview.RecyclerViewListAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;


public class StoryActivity extends AppCompatActivity {

    @Bind(R.id.dragLayout) DragLayout mDragLayout;
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

        DragLayout.DragListener dragListener = new DragLayout.DragListener() {
            @Override
            public boolean shouldIntercept(MotionEvent event) {
                return !mRecyclerView.canScrollVertically(-1);
            }

            @Override
            public void onDragEnd() {
                finish();
                overridePendingTransition(0, 0);
            }
        };


        mDragLayout.init(mCardView, dragListener);
    }
}
