package com.github.pengrad.uw_facebook_boo.comments;

import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;

/**
 * Stas Parshin
 * 05 September 2015
 */
public class FacebookCommentsRequest {

    public static GraphRequest createRequest(String postId, GraphRequest.Callback callback) {
        AccessToken token = AccessToken.getCurrentAccessToken();

        Bundle parameters = new Bundle();
        parameters.putString("fields", "message,created_time,from{name,picture.height(200).width(200)}");
        parameters.putString("limit", "20");

        GraphRequest request = GraphRequest.newGraphPathRequest(token, "/" + postId + "/comments", callback);
        request.setParameters(parameters);
        return request;
    }

}