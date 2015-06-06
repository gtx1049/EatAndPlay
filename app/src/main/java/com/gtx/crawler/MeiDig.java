package com.gtx.crawler;

import android.content.Context;
import android.webkit.WebView;
import android.widget.Button;

import com.gtx.filter.MeiFilter;

/**
 * Created by Administrator on 2015/5/19.
 */
public class MeiDig extends BaseDig
{
    public final static String MEI_URL = "http://i.meituan.com";
    public MeiDig(WebView wb, Button clickme)
    {
        super(wb, clickme);
    }

    public void load()
    {
        loadURL(MEI_URL);
    }

    public void saveContent(Context context)
    {
        String url = wb.getUrl();
        filter = new MeiFilter(context);
        filter.getEntry(url);
    }
}
