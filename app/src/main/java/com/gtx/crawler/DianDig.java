package com.gtx.crawler;

import android.content.Context;
import android.webkit.WebView;

import com.gtx.model.DianFilter;

/**
 * Created by Administrator on 2015/5/22.
 */
public class DianDig extends BaseDig
{
    public final static String DIAN_URL = "http://m.dianping.com";

    public DianDig(WebView wb)
    {
        super(wb);
    }

    public void load()
    {
        loadURL(DIAN_URL);
    }

    public void saveContent(Context context)
    {
        String url = wb.getUrl();
        filter = new DianFilter(context);
        filter.getEntry(url);
    }
}
