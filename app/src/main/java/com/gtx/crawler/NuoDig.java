package com.gtx.crawler;

import android.content.Context;
import android.os.Handler;
import android.webkit.WebView;
import android.widget.Button;

import com.gtx.filter.NuoFilter;
import com.gtx.model.Entry;

/**
 * Created by Administrator on 2015/5/23.
 */
public class NuoDig extends BaseDig
{
    public final static String NOU_URL = "http://m.nuomi.com";

    public NuoDig(WebView wb,  Button clickme, Handler handler)
    {
        super(wb, clickme, handler);
    }

    public void load()
    {
        loadURL(NOU_URL);
    }

    public Entry saveContent(Context context)
    {
        String url = wb.getUrl();
        filter = new NuoFilter(context, handler);
        return filter.getEntry(url);
    }
}
