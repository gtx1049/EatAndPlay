package com.gtx.crawler;

import android.content.ContentValues;
import android.content.Context;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.beardedhen.androidbootstrap.BootstrapButton;
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

    private BootstrapButton clickme;

    public BaseDig(WebView wb, final BootstrapButton clickme, Handler handler)
    {
        this.handler = handler;
        this.wb = wb;
        wb.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {

                WebSettings settings = view.getSettings();
                settings.setJavaScriptEnabled(true);
                view.loadUrl(url);
                if(url.contains(Constant.KEY_WORD))
                {
                    clickme.setBootstrapButtonEnabled(true);
                }
                return true;
            }
        });

        wb.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if(event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    if(keyCode == KeyEvent.KEYCODE_BACK && BaseDig.this.wb.canGoBack())
                    {
                        BaseDig.this.wb.goBack();
                        return true;
                    }
                }
                return false;
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
