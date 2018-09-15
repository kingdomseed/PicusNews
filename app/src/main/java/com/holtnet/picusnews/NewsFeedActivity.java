package com.holtnet.picusnews;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewsFeedActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsArticle>> {

    private static final int NEWS_LOADER_ID = 1;
    private static final String NEWS_REQUEST_URL = "https://content.guardianapis.com/us/technology?show-tags=contributor&api-key=11ea37a2-6fe3-412b-bfa8-e984bf496acc";

    private NewsAdapter newsAdapter;
    private TextView emptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        ListView newsArticleListView = findViewById(R.id.list_view);
        newsAdapter = new NewsAdapter(this, new ArrayList<NewsArticle>());
        newsArticleListView.setAdapter(newsAdapter);

        emptyStateTextView = findViewById(R.id.empty_view);
        newsArticleListView.setEmptyView(emptyStateTextView);

        newsArticleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NewsArticle currentNewsArticle = newsAdapter.getItem(i);
                Uri newsArticleUri = Uri.parse(currentNewsArticle.getWebUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsArticleUri);
                startActivity(websiteIntent);
            }
        });

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected) {


            LoaderManager loaderManager = getSupportLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);


        } else {
            emptyStateTextView.setText(R.string.no_internet);
        }
    }

    @NonNull
    @Override
    public Loader<List<NewsArticle>> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new NewsArticleLoader(this, NEWS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<NewsArticle>> loader, List<NewsArticle> newsArticleList) {
        newsAdapter.clear();
        if (newsArticleList != null && !newsArticleList.isEmpty()) {
            newsAdapter.addAll(newsArticleList);
        }

        emptyStateTextView.setText(R.string.no_news);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<NewsArticle>> loader) {
        newsAdapter.clear();
    }
}
