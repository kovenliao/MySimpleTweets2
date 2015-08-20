package com.kl.apps.MySimpleTweets.activity;

import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.kl.apps.MySimpleTweets.R;
import com.kl.apps.MySimpleTweets.TwitterClient;

import com.kl.apps.MySimpleTweets.fragment.HomeTimelineFragment;
import com.kl.apps.MySimpleTweets.fragment.MentionsTimelineFragment;
import com.kl.apps.MySimpleTweets.fragment.TweetsListFragment;
import com.kl.apps.MySimpleTweets.models.Tweet;


public class TimelineActivity extends ActionBarActivity {

    public static final Integer REQUEST_CODE = 200;

    public static final String TAG = "devdev";

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabStrip.setViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.miProfile) {
            onProfileView();
            return true;

        }
        else if (id == R.id.miComposePost) {
            onComposeClick();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requectCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requectCode == REQUEST_CODE) {
            Tweet newTweet = (Tweet) data.getSerializableExtra("tweet");
            TweetsPagerAdapter adapter = (TweetsPagerAdapter) viewPager.getAdapter();
            adapter.addHomeTweet(newTweet);

//            viewPager.getAdapter().notifyDataSetChanged();
//            tweets.add(0, newTweet);
//            tweetAdapter.notifyDataSetChanged();
//            lvTweets.setSelectionAfterHeaderView();
        }
    }

    private void onComposeClick() {
        Intent intent = new Intent(TimelineActivity.this, ComposeActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    private void onProfileView() {
        Intent intent = new Intent(TimelineActivity.this, ProfileActivity.class);
        intent.putExtra("screen_name", TwitterClient.SCREEN_NAME);
        startActivityForResult(intent, REQUEST_CODE);
    }

    /**
     * TweetsPagerAdapter: for tabs
     */
    public class TweetsPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 2;
        private TweetsListFragment fragmentInstance;
        private HomeTimelineFragment homeTimelineFragment;
        private MentionsTimelineFragment mentionsTimelineFragment;

        private String tabTitles[] = {"Home", "Mentions"};
//        private String tabTitles[] = {"Home"};

        public TweetsPagerAdapter(FragmentManager fm) {
            super(fm);
            fragmentInstance = null;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
//                fragmentInstance = new HomeTimelineFragment();
                homeTimelineFragment = new HomeTimelineFragment();
                return homeTimelineFragment;
            } else if (position == 1) {
//                fragmentInstance = new MentionsTimelineFragment();
                mentionsTimelineFragment = new MentionsTimelineFragment();
                return mentionsTimelineFragment;
            } else {
                return null;
            }
//            return fragmentInstance;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        public void addHomeTweet(Tweet tweet) {
            homeTimelineFragment.add(tweet);
        }
    }
}
