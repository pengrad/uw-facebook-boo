package com.github.pengrad.uw_facebook_boo.comments;

import com.google.gson.JsonObject;

import java.util.List;

/**
 * Stas Parshin
 * 06 September 2015
 */
public class CommentsData {

    public List<Comment> data;

    public static class Comment {
        public String id;
        public String message;
        public long created_time;

        private JsonObject from;

        public String getUserName() {
            return from.getAsJsonPrimitive("name").getAsString();
        }

        public String getUserPicture() {
            return from.getAsJsonObject("picture").getAsJsonObject("data").getAsJsonPrimitive("url").getAsString();
        }

        @Override
        public String toString() {
            return "Comment{" +
                    "id='" + id + '\'' +
                    ", message='" + message + '\'' +
                    ", created_time='" + created_time + '\'' +
                    ", from=" + from +
                    '}';
        }
    }
}
