package com.gtx.filter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.gtx.filter.BaseFilter;
import com.gtx.model.Entry;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Date;

/**
 * Created by Administrator on 2015/5/19.
 */
public class MeiFilter extends BaseFilter
{
    public static String TAG = "MeiFilter";

    public MeiFilter(Context context, Handler handler)
    {
        super(context, handler);

        pichandler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                while (true)
                {
                    if (entry != null)
                    {
                        break;
                    }
                }

                String path = (String)msg.obj;
                entry.setBitmap(path);

                Message tomsg = new Message();
                tomsg.obj = entry;
                MeiFilter.this.handler.sendMessage(tomsg);
            }
        };
    }

    @Override
    public Entry getEntry(String url)
    {
        //从这里开始要一直卡住

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
                entry = parseForm(new String(responseBody));
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
        try
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

            //Log.d(TAG, "Price : " + price);
            //Log.d(TAG, "Title : " + title);
            //Log.d(TAG, "Description : " + description);
            //Log.d(TAG, "Address : " + address);
            //Log.d(TAG, "Pic : " + urlpic);

            Entry entry = new Entry(title, address, description, new Date(), new Double(price).intValue());
            return entry;
        }catch (Exception e)
        {
            Toast.makeText(context, "读取失败", Toast.LENGTH_LONG);
        }
        return new Entry("", "", "", new Date(), 0);
    }

}
