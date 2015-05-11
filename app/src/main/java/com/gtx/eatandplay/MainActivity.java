package com.gtx.eatandplay;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.media.Image;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;


public class MainActivity extends TabActivity
{
    private TabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
