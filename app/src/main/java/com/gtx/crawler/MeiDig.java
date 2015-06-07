package com.gtx.crawler;

import android.content.Context;
import android.os.Handler;
import android.webkit.WebView;
import android.widget.Button;

import com.gtx.filter.MeiFilter;
import com.gtx.model.Entry;

/**
 * Created by Administrator on 2015/5/19.
 */
public class MeiDig extends BaseDig
{
    public final static String MEI_URL = "http://i.meituan.com";
    public MeiDig(WebView wb, Button clickme, Handler handler)
    {
        super(wb, clickme, handler);
    }

    public void load()
    {
        loadURL(MEI_URL);
    }

    public Entry saveContent(Context context)
    {
        String url = wb.getUrl();
        filter = new MeiFilter(context, handler);
        return filter.getEntry(url);
    }
}
