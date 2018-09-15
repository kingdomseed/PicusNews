package com.holtnet.picusnews;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

public class NewsArticleLoader extends AsyncTaskLoader<List<NewsArticle>>{

    private String url;

    // package-private
    NewsArticleLoader(Context context, String url)
    {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading()
    {
        forceLoad();
    }

    @Override
    public List<NewsArticle> loadInBackground()
    {
        if(url == null)
        {
            return null;
        }
        return ConnectionUtility.retrieveNewsArticleData(url);
    }
}
