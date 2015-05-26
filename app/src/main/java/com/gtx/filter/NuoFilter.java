package com.gtx.model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.gtx.crawler.NuoDig;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2015/5/23.
 */
public class NuoFilter extends BaseFilter
{

    public static String TAG = "NuoFilter";

    public NuoFilter(Context context)
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

        String price = doc.select("em.price-value").first().html();

        String title = doc.select("h3.title").first().html();

        Element desc = doc.select("div.product-title").first();
        String description = desc.select("p.desc").first().html();

        String address = doc.select("p.shop-address").first().html();

        String urlpic = fetchPic(doc.select("a.detail-product-img").first().html());

        this.savePic(urlpic, urlpic.substring(urlpic.length() - 15, urlpic.length()));

        Log.d(TAG, "Price : " + price);
        Log.d(TAG, "Title : " + title);
        Log.d(TAG, "Description : " + description);
        Log.d(TAG, "Address : " + address);
        Log.d(TAG, "Pic : " + urlpic);

        return null;
    }

    private String fetchPic(String detail)
    {
        String regex = "img_src.*=.*\".*\"";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(detail);

        String s = null;

        if(m.find())
        {
            s = m.group();
            s = s.substring(s.indexOf("\"") + 1, s.length() - 1);
            Log.d(TAG, s);
        }

        String ret = s.replaceAll("\\\\", "");
        return ret;
    }
}
