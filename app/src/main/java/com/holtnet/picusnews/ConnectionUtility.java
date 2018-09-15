package com.holtnet.picusnews;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class ConnectionUtility {

    private ConnectionUtility()
    {

    }

    public static List<NewsArticle> retrieveNewsArticleData(String requestUrl) {

        URL url = null;
        try {
            url = new URL(requestUrl);
        } catch (MalformedURLException e) {
            Log.e("ConnectionUtility URL", "Problem building URL", e);
        }

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e)
        {
            Log.e("ConnectionUtility JSON", "Problem making HTTP request");
        }

        return extractFeaturesFromJson(jsonResponse);
    }

    private static List<NewsArticle> extractFeaturesFromJson(String jsonResponse) {
        if(TextUtils.isEmpty(jsonResponse))
        {
            return null;
        }

        List<NewsArticle> newsArticles = new ArrayList<>();
        try {

            JSONObject rootJSONResponse = new JSONObject(jsonResponse);
            JSONObject responseObject = rootJSONResponse.getJSONObject("response");
            JSONArray resultsArray = responseObject.getJSONArray("results");

            for(int i = 0; i < resultsArray.length(); i++) {
                String contributor = null;
                JSONObject currentNewsArticle = resultsArray.getJSONObject(i);
                JSONArray getCurrentTags = currentNewsArticle.getJSONArray("tags");
                if(getCurrentTags.length() > 0)
                {
                   JSONObject contributorTitleTag = getCurrentTags.getJSONObject(0);
                   contributor = contributorTitleTag.getString("webTitle");
                } else
                {
                   contributor = "No author listed.";
                }
                String webTitle = currentNewsArticle.getString("webTitle");
                String sectionName = currentNewsArticle.getString("sectionName");
                String webPublicationDate = currentNewsArticle.getString("webPublicationDate");
                String webUrl = currentNewsArticle.getString("webUrl");
                NewsArticle newsArticle = new NewsArticle(webTitle, sectionName, webPublicationDate, contributor, webUrl);
                newsArticles.add(newsArticle);
            }


        } catch (JSONException e) {
            Log.e("ConnectionUtil Extract", "Problem parsing the news article JSON results", e);
        }

        return newsArticles;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("Connection Utility", "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("Connection Utility", "Problem retrieving the news articles JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

}
