package com.github.pengrad.uw_facebook_boo;

import com.google.gson.JsonObject;

import java.util.List;

/**
 * stas
 * 9/2/15
 */
public class FeedData {

    public List<Post> data;

    public static class Post {
        public String id;
        public String message;
        public String picture;
        private JsonObject comments;

        public int getCommentsCount() {
            return comments.getAsJsonObject("summary").getAsJsonPrimitive("total_count").getAsInt();
        }

    }

}