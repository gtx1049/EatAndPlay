package com.gtx.model;

import android.content.Context;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Toast;

import com.gtx.crawler.DianDig;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.HttpGet;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.CookiePolicy;

/**
 * Created by Administrator on 2015/5/22.
 */
public class DianFilter extends BaseFilter
{
    public static String TAG = "DianFilter";

    public DianFilter(Context context)
    {
        super(context);
    }

    @Override
    public Entry getEntry(final String url)
    {
        Toast.makeText(context, url, Toast.LENGTH_SHORT);
        AsyncHttpClient client = new AsyncHttpClient();

        //I can't use AsyncHttpClient but I use the default httpclient

        //YES! I know the reason, DIANPING will inspect the user_agent param
        //if the param is null, it will reply wrong info
        //if the param is something others, it replies the elder content page
        //So, we need put the user agent ad WebKit
        client.getHttpClient().getParams().setParameter(CoreProtocolPNames.USER_AGENT, "WebKit");

        client.get(url, new AsyncHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
            {
                Toast.makeText(context, "Success!", Toast.LENGTH_SHORT);

                //writeToFile(filename, responseBody);
                parseForm(new String(responseBody));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
            {
                Toast.makeText(context, "Failure!", Toast.LENGTH_SHORT);
                Log.d(TAG, new String(responseBody));
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

        Element priceElement = doc.select("div.price").first();
        String price = priceElement.nextElementSibling().html();

        Element titleElement = doc.select("div.intro").first();
        String title = titleElement.select("h3").first().html();

        String description = titleElement.select("p").first().html();

        String address = doc.select("div.address").first().html();

        Element content = doc.select("div.content").first();
        String urlpic = content.select("div.info").first().select("img").attr("src");

        this.savePic(urlpic, urlpic.substring(urlpic.length() - 15, urlpic.length()).replaceAll("/", "&"));

        Log.d(TAG, "Price : " + price);
        Log.d(TAG, "Title : " + title);
        Log.d(TAG, "Description : " + description);
        Log.d(TAG, "Address : " + address);
        Log.d(TAG, "Pic : " + urlpic);

        return null;
    }
}
