package com.gtx.eatandplay;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
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

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.gtx.crawler.BaseDig;
import com.gtx.crawler.DianDig;
import com.gtx.crawler.MeiDig;
import com.gtx.crawler.NuoDig;
import com.gtx.model.Constant;
import com.gtx.model.Entry;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class PageContent extends ActionBarActivity
{
    private WebView wb;
    private BootstrapButton bt;
    private BootstrapButton back;

    BaseDig bd;

    private int webtype;
    private int entrytype;

    private Handler handler;

    private SweetAlertDialog pDialog;

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
                PageContent.this.startActivityForResult(intent, 0);
                pDialog.dismiss();
            }
        };

        wb = (WebView)findViewById(R.id.webview);
        bt = (BootstrapButton)findViewById(R.id.get_content);

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

        bt = (BootstrapButton)findViewById(R.id.get_content);

        bt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                pDialog = new SweetAlertDialog(PageContent.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("数据读取中...");
                pDialog.setCancelable(false);
                pDialog.show();
                Entry entry = bd.saveContent(PageContent.this);

            }
        });

        back = (BootstrapButton)findViewById(R.id.content_back);
        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });


        bt.setBootstrapButtonEnabled(false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if(resultCode == Constant.RESULT_ADD)
        {
            Entry entry = (Entry)intent.getSerializableExtra(Constant.ENTRY);
            Intent backintent = new Intent();
            backintent.putExtra(Constant.ENTRY, entry);
            setResult(Constant.RESULT_ADD, backintent);

            finish();
        }
    }

}
