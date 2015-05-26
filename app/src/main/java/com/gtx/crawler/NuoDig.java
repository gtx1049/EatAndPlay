package com.gtx.crawler;

import android.content.Context;
import android.webkit.WebView;

import com.gtx.model.MeiFilter;
import com.gtx.model.NuoFilter;

/**
 * Created by Administrator on 2015/5/23.
 */
public class NuoDig extends BaseDig
{
    public final static String NOU_URL = "http://m.nuomi.com";

    public NuoDig(WebView wb)
    {
        super(wb);
    }

    public void load()
    {
        loadURL(NOU_URL);
    }

    public void saveContent(Context context)
    {
        String url = wb.getUrl();
        filter = new NuoFilter(context);
        filter.getEntry(url);
    }
}
