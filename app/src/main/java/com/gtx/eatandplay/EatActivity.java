package com.gtx.eatandplay;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.daimajia.swipe.SwipeLayout;
import com.gtx.controll.CommonListener;
import com.gtx.controll.CommonViewAdapter;


public class EatActivity extends ActionBarActivity
{
    private SwipeLayout swipeLayout;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eat);

        //swipeLayout = (SwipeLayout)findViewById(R.id.eat);
        //swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        //swipeLayout.addSwipeListener(new CommonListener());

        list = (ListView)findViewById(R.id.common_list);
        list.setAdapter(new CommonViewAdapter(this));
    }

}
