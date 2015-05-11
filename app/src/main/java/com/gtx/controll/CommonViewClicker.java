package com.gtx.controll;

import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by Administrator on 2015/5/11.
 */
public class CommonViewClicker implements AdapterView.OnItemClickListener
{
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        System.out.println("Hello");
        view.setBackgroundColor(Color.DKGRAY);
    }
}
