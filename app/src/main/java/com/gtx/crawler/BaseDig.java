package com.gtx.crawler;

import android.content.ContentValues;
import android.content.Context;
import android.os.Handler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.gtx.filter.BaseFilter;
import com.gtx.model.Constant;
import com.gtx.model.Entry;

import org.apache.http.conn.scheme.HostNameResolver;

/**
 * Created by Administrator on 2015/5/19.
 */
public class BaseDig
{
    protected WebView wb;
    protected BaseFilter filter;
    protected Handler handler;

    private Button clickme;

    public BaseDig(WebView wb, final Button clickme, Handler handler)
    {
        this.handler = handler;
        this.wb = wb;
        wb.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                if(url.contains(Constant.KEY_WORD))
                {
                    clickme.setEnabled(true);
                }

                WebSettings settings = view.getSettings();
                settings.setJavaScriptEnabled(true);
                view.loadUrl(url);
                return true;
            }
        });
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

    public Entry saveContent(Context context)
    {
        return null;
    }

    public void load()
    {

    }
}
