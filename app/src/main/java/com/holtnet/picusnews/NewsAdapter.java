package com.holtnet.picusnews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<NewsArticle> {
    // package-private
    NewsAdapter(Context context, List<NewsArticle> newsArticles) {
        super(context, 0, newsArticles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View newsItemView = convertView;
        if (newsItemView == null) {
            newsItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_item, parent, false);
        }

        NewsArticle currentNewsArticle = getItem(position);

        TextView articleTitleTextView = newsItemView.findViewById(R.id.article_title);
        articleTitleTextView.setText(currentNewsArticle.getWebTitle());

        TextView sectionTextView = newsItemView.findViewById(R.id.section_title);
        sectionTextView.setText(currentNewsArticle.getSectionName());

        TextView publishDateTextView = newsItemView.findViewById(R.id.date_published);
        publishDateTextView.setText(currentNewsArticle.getWebPublicationDate().replaceFirst("(\\d{4})-(\\d{2})-(\\d{2}).(\\d{2}):(\\d{2}):.{3}", "$2-$3-$1 at $4:$5"));

        TextView authorTextView = newsItemView.findViewById(R.id.author_name);
        authorTextView.setText(currentNewsArticle.getPublicationAuthor());

        return newsItemView;
    }
}
