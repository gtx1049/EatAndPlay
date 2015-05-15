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

        Intent eatintent =new Intent();
        eatintent.setClass(this, EatActivity.class);
        Intent drinkintent =new Intent();
        drinkintent.setClass(this, DrinkActivity.class);
        Intent playintent =new Intent();
        playintent.setClass(this, PlayActivity.class);
        Intent happyintent =new Intent();
        happyintent.setClass(this, HappyActivity.class);

        mTabHost.addTab(mTabHost.newTabSpec("EAT")
                .setIndicator(null, getResources().getDrawable(R.drawable.food))
                .setContent(eatintent));
        mTabHost.addTab(mTabHost.newTabSpec("DRINK")
                .setIndicator(null, getResources().getDrawable(R.drawable.drink))
                .setContent(drinkintent));
        mTabHost.addTab(mTabHost.newTabSpec("PLAY")
                .setIndicator(null, getResources().getDrawable(R.drawable.play))
                .setContent(playintent));
        mTabHost.addTab(mTabHost.newTabSpec("HAPPY")
                .setIndicator(null, getResources().getDrawable(R.drawable.happy))
                .setContent(happyintent));

    }

}
