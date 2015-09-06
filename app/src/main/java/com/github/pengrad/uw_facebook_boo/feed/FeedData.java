package com.github.pengrad.uw_facebook_boo.feed;

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
        public String full_picture;
        public long created_time;
        private JsonObject comments;
        private JsonObject likes;

        public int getCommentsCount() {
            return comments.getAsJsonObject("summary").getAsJsonPrimitive("total_count").getAsInt();
        }

        public int getLikesCount() {
            return likes.getAsJsonObject("summary").getAsJsonPrimitive("total_count").getAsInt();
        }

        @Override
        public String toString() {
            return "Post{" +
                    "full_picture='" + full_picture + '\'' +
                    ", message='" + message + '\'' +
                    ", id='" + id + '\'' +
                    '}';
        }
    }

}
