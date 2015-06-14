package com.gtx.filter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.gtx.crawler.NuoDig;
import com.gtx.filter.BaseFilter;
import com.gtx.model.Constant;
import com.gtx.model.Entry;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2015/5/23.
 */
public class NuoFilter extends BaseFilter
{

    public static String TAG = "NuoFilter";

    private String nuoaddress = null;

    private boolean ispicset = false;
    private boolean isaddrset = false;

    public NuoFilter(Context context, Handler handler)
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

                if(msg.arg1 == Constant.MESSAGE_PIC)
                {
                    String path = (String)msg.obj;
                    entry.setBitmap(path);
                    ispicset = true;
                    if(isaddrset && ispicset)
                    {
                        Message tomsg = new Message();
                        tomsg.obj = entry;
                        NuoFilter.this.handler.sendMessage(tomsg);
                    }
                }

                if(msg.arg1 == Constant.MESSAGE_ADDR)
                {
                    entry.setAddress(nuoaddress);
                    isaddrset = true;
                    if(isaddrset && ispicset)
                    {
                        Message tomsg = new Message();
                        tomsg.obj = entry;
                        NuoFilter.this.handler.sendMessage(tomsg);
                    }
                }


            }
        };
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

            String price = doc.select("em.price-value").first().html();

            String title = doc.select("h3.title").first().html();

            Element desc = doc.select("div.product-title").first();
            String description = desc.select("p.desc").first().html();

            String address = getAddress(doc.html());

            String urlpic = fetchPic(doc.select("a.detail-product-img").first().html());
            this.savePic(urlpic, urlpic.substring(urlpic.length() - 15, urlpic.length()));

            Entry entry = new Entry(title, address, description, new Date(), new Double(price).intValue());
            return entry;
        }
        catch (Exception e)
        {
            Toast.makeText(context, "读取失败", Toast.LENGTH_LONG);
        }


        //Log.d(TAG, "Price : " + price);
        //Log.d(TAG, "Title : " + title);
        //Log.d(TAG, "Description : " + description);
        //Log.d(TAG, "Address : " + address);
        //Log.d(TAG, "Pic : " + urlpic);
        return new Entry("", "", "", new Date(), 0);

    }

    private String getAddress(String html)
    {
        String retaddress = "";

        Pattern pid = Pattern.compile("dealId: \".*\"");
        Pattern parea = Pattern.compile("areaDomain: \".*\"");
        String id = "";
        String area = "";
        Matcher m = pid.matcher(html);
        while (m.find())
        {
            id = m.group();
        }
        m = parea.matcher(html);
        while (m.find())
        {
            area = m.group();
        }
        id = id.substring(id.indexOf("\"") + 1, id.length() - 1);
        area = area.substring(area.indexOf("\"") + 1, area.length() - 1);

        String url = "http://m.nuomi.com/webapp/tuan/detailAjax?dealId=" + id + "&areaDomain=" + area;

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
            {
                String json = new String(responseBody);
                JSONObject jo = null;
                String shopinfo = "";
                try
                {
                    jo = new JSONObject(json);
                    shopinfo = jo.getJSONObject("data").getString("merchant-info");
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }


                Document doc = Jsoup.parse(shopinfo);
                NuoFilter.this.nuoaddress = doc.select("p.shop-address").first().html();

                Message msg = new Message();
                msg.arg1 = Constant.MESSAGE_ADDR;
                pichandler.sendMessage(msg);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
            {
                Log.d(TAG, "st " + statusCode + " " + new String(responseBody));
            }
        });

        return retaddress;
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
