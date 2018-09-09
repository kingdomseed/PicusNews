package com.holtnet.picusnews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class NewsFeedActivity extends AppCompatActivity {

    private static final int NEWS_LOADER_ID = 1;
    private static final String NEWS_REQUEST_URL = "https://content.guardianapis.com/search?api-key=11ea37a2-6fe3-412b-bfa8-e984bf496acc";

    private NewsAdapter newsAdapter;
    private TextView emptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);
    }
}
