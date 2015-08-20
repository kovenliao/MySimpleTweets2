package com.kl.apps.MySimpleTweets.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.kl.apps.MySimpleTweets.EndlessScrollListener;
import com.kl.apps.MySimpleTweets.R;
import com.kl.apps.MySimpleTweets.TwitterApplication;
import com.kl.apps.MySimpleTweets.TwitterClient;
import com.kl.apps.MySimpleTweets.adapter.TweetArrayAdapter;
import com.kl.apps.MySimpleTweets.data.QueryReq;
import com.kl.apps.MySimpleTweets.models.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kovenliao on 8/20/15.
 */
abstract public class TweetsListFragment extends Fragment {
    public static final String TAG = "devdev";

    protected TwitterClient client;
    private ArrayList<Tweet> tweets;
    private TweetArrayAdapter tweetAdapter;
    private ListView lvTweets;

    abstract protected void populateTimeLine(QueryReq req);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tweets_list, parent, false);
        lvTweets = (ListView) view.findViewById(R.id.lvTweets);
        lvTweets.setAdapter(tweetAdapter);

        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int pageLabel, int totalItemsCount) {
                Log.i(TAG, "onLoadMore, page" + pageLabel);
                QueryReq req = new QueryReq();
                req.setSinceIdByPage(pageLabel - 1);
                populateTimeLine(req);
            }
        });
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
        tweets = new ArrayList<>();
        tweetAdapter = new TweetArrayAdapter(getActivity(), tweets);
    }

    public void add(Tweet input) {
        this.add(0, input);
    }

    public void add(int position, Tweet input) {
        tweets.add(position, input);
        tweetAdapter.notifyDataSetChanged();
    }

    protected void addAll(List<Tweet> input) {
        tweetAdapter.addAll(input);
    }
}
