package com.gtx.eatandplay;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.daimajia.swipe.SwipeLayout;
import com.gtx.controll.CommonListener;
import com.gtx.controll.CommonViewAdapter;
import com.gtx.controll.CommonViewClicker;
import com.gtx.model.Entry;

import java.util.ArrayList;
import java.util.List;


public class EatActivity extends ActionBarActivity
{
    private SwipeLayout swipeLayout;
    private ListView list;
    private BootstrapButton add;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eat);

        //swipeLayout = (SwipeLayout)findViewById(R.id.eat);
        //swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        //swipeLayout.addSwipeListener(new CommonListener());

        List<Entry> entryList;
        entryList = Entry.getEntrylist(Entry.EAT_TYPE);
        list = (ListView)findViewById(R.id.common_list);
        list.setAdapter(new CommonViewAdapter(this, entryList));

        add = (BootstrapButton)findViewById(R.id.add_food);
        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(EatActivity.this, AddActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {

    }

}
