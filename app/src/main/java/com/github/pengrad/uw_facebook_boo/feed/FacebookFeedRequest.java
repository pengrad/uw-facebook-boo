package com.github.pengrad.uw_facebook_boo.feed;

import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;

/**
 * Stas Parshin
 * 05 September 2015
 */
public class FacebookFeedRequest {

    public static GraphRequest createRequest(GraphRequest.Callback callback) {
        AccessToken token = AccessToken.getCurrentAccessToken();

        Bundle parameters = new Bundle();
        parameters.putString("fields", "message,created_time,full_picture,likes.limit(0).summary(true),comments.limit(0).summary(true)");
        parameters.putString("date_format", "U");

        GraphRequest request = GraphRequest.newGraphPathRequest(token, "/Boo/feed", callback);
        request.setParameters(parameters);
        return request;
    }

}