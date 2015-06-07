package com.gtx.crawler;

import android.content.Context;
import android.os.Handler;
import android.webkit.WebView;
import android.widget.Button;

import com.gtx.filter.DianFilter;
import com.gtx.model.Entry;


/**
 * Created by Administrator on 2015/5/22.
 */
public class DianDig extends BaseDig
{
    public final static String DIAN_URL = "http://m.dianping.com";

    public DianDig(WebView wb, Button clickme, Handler handler)
    {
        super(wb, clickme, handler);
    }

    public void load()
    {
        loadURL(DIAN_URL);
    }

    public Entry saveContent(Context context)
    {
        String url = wb.getUrl();
        filter = new DianFilter(context, handler);
        return filter.getEntry(url);
    }
}
