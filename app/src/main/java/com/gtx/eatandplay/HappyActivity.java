package com.gtx.eatandplay;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.daimajia.swipe.SwipeLayout;
import com.gtx.controll.CommonViewAdapter;
import com.gtx.model.Constant;
import com.gtx.model.Entry;

import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class HappyActivity extends ActionBarActivity
{

    private SwipeLayout swipeLayout;
    private ListView list;
    private BootstrapButton add;
    private BootstrapButton happyweb;

    private CommonViewAdapter cva;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_happy);

        List<Entry> entryList;
        entryList = Entry.getEntrylist(Entry.HAPPY_TYPE);
        list = (ListView)findViewById(R.id.common_list_happy);
        cva = new CommonViewAdapter(this, entryList, Entry.HAPPY_TYPE);
        list.setAdapter(cva);

        add = (BootstrapButton)findViewById(R.id.add_happy);
        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(HappyActivity.this, AddActivity.class);
                intent.putExtra(Constant.TYPE, Entry.HAPPY_TYPE);
                startActivityForResult(intent, 0);
            }
        });

        happyweb = (BootstrapButton)findViewById(R.id.happyweb);
        happyweb.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(HappyActivity.this, ChooseTuan.class);
                intent.putExtra(Constant.TYPE, Entry.HAPPY_TYPE);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if(resultCode == Constant.RESULT_ADD)
        {
            Entry entry = (Entry)intent.getSerializableExtra(Constant.ENTRY);
            cva.addEntry(entry);
            cva.notifyDataSetChanged();
        }
        else if(resultCode == Constant.RESULT_UPDATE)
        {
            Entry entry = (Entry)intent.getSerializableExtra(Constant.ENTRY);
            cva.updateEntry(entry);
            cva.notifyDataSetChanged();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
