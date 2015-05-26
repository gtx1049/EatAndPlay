package com.gtx.crawler;

import android.content.ContentValues;
import android.content.Context;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.gtx.model.BaseFilter;

/**
 * Created by Administrator on 2015/5/19.
 */
public class BaseDig
{
    protected WebView wb;
    protected BaseFilter filter;

    public BaseDig(WebView wb)
    {
        this.wb = wb;
        wb.setWebViewClient(new WebViewClient());
    }

    public void loadURL(String url)
    {
        WebSettings settings = wb.getSettings();
        settings.setJavaScriptEnabled(true);
        wb.loadUrl(url);
    }

    public String getUrl()
    {
        return wb.getUrl();
    }

    public void saveContent(Context context)
    {

    }

    public void load()
    {

    }
}
