package com.gtx.eatandplay;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Administrator on 2015/5/17.
 */
public class ApplicationEP extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/FZMWFONT.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }
}
