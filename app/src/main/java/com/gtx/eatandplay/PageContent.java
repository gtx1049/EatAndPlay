package com.gtx.eatandplay;

import android.app.Activity;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.gtx.crawler.BaseDig;
import com.gtx.crawler.DianDig;
import com.gtx.crawler.MeiDig;
import com.gtx.crawler.NuoDig;
import com.gtx.model.Constant;
import com.gtx.model.Entry;


public class PageContent extends ActionBarActivity
{
    private WebView wb;
    private Button bt;

    BaseDig bd;

    private int webtype;
    private int entrytype;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_conten);

        handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                Entry entry = (Entry)msg.obj;

                Intent intent = new Intent(PageContent.this, AddActivity.class);
                intent.putExtra(Constant.TYPE, entrytype);
                intent.putExtra(Constant.ADD_FROM_WEB, entry);
                PageContent.this.startActivity(intent);
            }
        };

        wb = (WebView)findViewById(R.id.webview);
        bt = (Button)findViewById(R.id.get_content);

        webtype = getIntent().getIntExtra(Constant.KEY_TUAN, 0);
        entrytype = getIntent().getIntExtra(Constant.TYPE, 0);

        if(webtype == Constant.KEY_MEITUAN)
        {
            bd = new MeiDig(wb, bt, handler);
            bd.load();
        }
        else if(webtype == Constant.KEY_NUOMI)
        {
            bd = new NuoDig(wb, bt, handler);
            bd.load();
        }
        else if(webtype == Constant.KEY_DAZHONG)
        {
            bd = new DianDig(wb, bt, handler);
            bd.load();
        }

        bt = (Button)findViewById(R.id.get_content);

        bt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Entry entry = bd.saveContent(PageContent.this);

            }
        });

        bt.setEnabled(false);
    }

}
