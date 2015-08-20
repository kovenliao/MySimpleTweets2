package com.kl.apps.MySimpleTweets.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kl.apps.MySimpleTweets.R;
import com.kl.apps.MySimpleTweets.TwitterClient;
import com.kl.apps.MySimpleTweets.activity.ProfileActivity;
import com.kl.apps.MySimpleTweets.activity.TimelineActivity;
import com.kl.apps.MySimpleTweets.models.Tweet;
import com.kl.apps.MySimpleTweets.util.TimeUtil;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by kovenliao on 8/13/15.
 */
public class TweetArrayAdapter extends ArrayAdapter<Tweet> {

    private static class ViewHolder {
        ImageView profileImage;
        TextView userName;
        TextView body;
        TextView createAt;
    }

    public TweetArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, android.R.layout.simple_list_item_1, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Tweet tweet = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
            viewHolder.profileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
            viewHolder.userName =  (TextView) convertView.findViewById(R.id.tvUserName);
            viewHolder.body = (TextView) convertView.findViewById(R.id.tvBody);
            viewHolder.createAt = (TextView) convertView.findViewById(R.id.tvCreateAt);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.profileImage.setImageResource(android.R.color.transparent);
        viewHolder.userName.setText(tweet.getUser().getScreenName());
        viewHolder.body.setText(tweet.getBody());
        viewHolder.createAt.setText(TimeUtil.getRelativeTimeAgo(tweet.getCreatedAt()));
        viewHolder.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProfileActivity.class);
                intent.putExtra("screen_name", tweet.getUser().getScreenName());
                intent.putExtra("tweet", tweet);
                getContext().startActivity(intent);
            }
        });

        Picasso.with(getContext())
                .load(tweet.getUser().getProfileImageUrl())
                .into(viewHolder.profileImage);

        return convertView;
    }
}
