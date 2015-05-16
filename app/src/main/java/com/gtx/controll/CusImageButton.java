package com.gtx.controll;

import android.content.Context;
import android.media.Image;
import android.util.AttributeSet;
import android.widget.ImageButton;

import com.beardedhen.androidbootstrap.FontAwesomeText;

/**
 * Created by Administrator on 2015/5/15.
 */
public class CusImageButton extends FontAwesomeText
{
    public int entryid;

    public CusImageButton(Context context)
    {
        super(context);
    }

    public CusImageButton(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public CusImageButton(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }


    public int getentryId()
    {
        return entryid;
    }

    public void setentryId(int id)
    {
        this.entryid = id;
    }
}
