package com.kl.apps.MySimpleTweets.fragment;

import android.os.Bundle;
import android.util.Log;

import com.kl.apps.MySimpleTweets.EndlessScrollListener;
import com.kl.apps.MySimpleTweets.TwitterApplication;
import com.kl.apps.MySimpleTweets.TwitterClient;
import com.kl.apps.MySimpleTweets.data.QueryReq;
import com.kl.apps.MySimpleTweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by kovenliao on 8/20/15.
 */
public class UserTimelineFragment extends TweetsListFragment {

    private TwitterClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
        QueryReq req = new QueryReq();
        req.setSinceIdByPage(0);
        populateTimeLine(req);
    }

    public static UserTimelineFragment newInstance(String screenName) {
        UserTimelineFragment userTimelineFragment = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putString("screen_name", screenName);
        userTimelineFragment.setArguments(args);
        return userTimelineFragment;
    }

    @Override
    protected void populateTimeLine(QueryReq req) {
        String screenName = getArguments().getString("screen_name");
        req.setScreenName(screenName);
        client.getUserTimeline(req, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
                Log.d(TAG, jsonArray.toString());
                addAll(Tweet.fromJSONArray(jsonArray));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d(TAG, errorResponse.toString());
            }
        });
    }
}
