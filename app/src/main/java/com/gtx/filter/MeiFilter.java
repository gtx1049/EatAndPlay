package com.gtx.model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Created by Administrator on 2015/5/19.
 */
public class MeiFilter extends BaseFilter
{
    public static String TAG = "MeiFilter";

    public MeiFilter(Context context)
    {
        super(context);
    }

    @Override
    public Entry getEntry(String url)
    {
        Toast.makeText(context, url, Toast.LENGTH_SHORT);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
            {
                Toast.makeText(context, "Success!", Toast.LENGTH_SHORT);
                String filename = "Mei.html";
                //writeToFile(filename, responseBody);
                parseForm(new String(responseBody));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
            {
                Toast.makeText(context, "Failure!", Toast.LENGTH_SHORT);
            }

            @Override
            public void onStart()
            {
                Toast.makeText(context, "Start!", Toast.LENGTH_SHORT);
                // called before request is started
            }
        });
        return null;
    }

    public Entry parseForm(String form)
    {
        Document doc = Jsoup.parse(form);

        Element dl = doc.select("dl").first();

        Element priceElement = dl.select("dd").first();
        String price = priceElement.select("strong").first().html();

        Element titleElement = priceElement.nextElementSibling();
        String title = titleElement.select("h1").first().html();

        String description = titleElement.select("p").first().html();

        String address = doc.select("div.address").first().html();

        String urlpic = doc.select("div.album").first().select("img").first().attr("src");

        this.savePic(urlpic, urlpic.substring(urlpic.length() - 15, urlpic.length()));

        Log.d(TAG, "Price : " + price);
        Log.d(TAG, "Title : " + title);
        Log.d(TAG, "Description : " + description);
        Log.d(TAG, "Address : " + address);
        Log.d(TAG, "Pic : " + urlpic);

        return null;
    }

}
