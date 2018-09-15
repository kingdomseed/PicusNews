package com.holtnet.picusnews;

public class NewsArticle {

    private String webTitle;
    private String sectionName;
    private String webPublicationDate;
    private String publicationAuthor;
    private String webUrl;

    // package-private
    NewsArticle(String title, String section, String date, String author, String url)
    {
        webTitle = title;
        sectionName = section;
        webPublicationDate = date;
        publicationAuthor = author;
        webUrl = url;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getWebPublicationDate() {
        return webPublicationDate;
    }

    public String getPublicationAuthor() {
        return publicationAuthor;
    }

    public String getWebUrl() {
        return webUrl;
    }
}
