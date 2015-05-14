package com.gtx.eatandplay;


import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import com.gtx.model.Entry;


public class MainActivity extends TabActivity
{
    private TabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Entry.initialDatabase(this);

        mTabHost = this.getTabHost();
        mTabHost.setup(this.getLocalActivityManager());

        Intent intent =new Intent();
        intent.setClass(this, EatActivity.class);

        mTabHost.addTab(mTabHost.newTabSpec("EAT")
                .setIndicator(null, getResources().getDrawable(R.drawable.food))
                .setContent(intent));
        mTabHost.addTab(mTabHost.newTabSpec("EAT")
                .setIndicator(null, getResources().getDrawable(R.drawable.drink))
                .setContent(R.id.drink));
        mTabHost.addTab(mTabHost.newTabSpec("EAT")
                .setIndicator(null, getResources().getDrawable(R.drawable.play))
                .setContent(R.id.play));
        mTabHost.addTab(mTabHost.newTabSpec("EAT")
                .setIndicator(null, getResources().getDrawable(R.drawable.happy))
                .setContent(R.id.happy));

    }

}
