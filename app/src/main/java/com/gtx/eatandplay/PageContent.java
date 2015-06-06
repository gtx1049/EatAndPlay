package com.gtx.eatandplay;

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


public class PageContent extends ActionBarActivity
{
    private WebView wb;
    private Button bt;

    BaseDig bd;

    private int webtype;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_conten);

        wb = (WebView)findViewById(R.id.webview);
        bt = (Button)findViewById(R.id.get_content);

        webtype = getIntent().getIntExtra(Constant.KEY_TUAN, 0);

        if(webtype == Constant.KEY_MEITUAN)
        {
            bd = new MeiDig(wb, bt);
            bd.load();
        }
        else if(webtype == Constant.KEY_NUOMI)
        {
            bd = new NuoDig(wb, bt);
            bd.load();
        }
        else if(webtype == Constant.KEY_DAZHONG)
        {
            bd = new DianDig(wb, bt);
            bd.load();
        }



        bt = (Button)findViewById(R.id.get_content);

        bt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                bd.saveContent(PageContent.this);
            }
        });

        bt.setEnabled(false);
    }

}
