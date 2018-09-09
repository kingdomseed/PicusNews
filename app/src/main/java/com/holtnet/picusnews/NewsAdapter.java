package com.holtnet.picusnews;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<NewsArticle>
{
    public NewsAdapter(Context context, List<NewsArticle> newsArticles)
    {
        super(context, 0, newsArticles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View newsItemView = convertView;
        

        return newsItemView;
    }
}
