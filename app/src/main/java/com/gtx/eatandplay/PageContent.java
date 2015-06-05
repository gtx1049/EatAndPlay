package com.gtx.eatandplay;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.gtx.crawler.BaseDig;
import com.gtx.crawler.NuoDig;


public class PageContent extends ActionBarActivity
{
    private WebView wb;
    private Button bt;

    BaseDig bd;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_conten);

        bd = new NuoDig(wb);
        bd.load();

        bt = (Button)findViewById(R.id.get_content);

        bt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                bd.saveContent(PageContent.this);
            }
        });
    }

}
