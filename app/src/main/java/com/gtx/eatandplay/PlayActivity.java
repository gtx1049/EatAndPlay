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


public class PlayActivity extends ActionBarActivity
{

    private SwipeLayout swipeLayout;
    private ListView list;
    private BootstrapButton add;
    private BootstrapButton playweb;

    private CommonViewAdapter cva;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        List<Entry> entryList;
        entryList = Entry.getEntrylist(Entry.PLAY_TYPE);
        list = (ListView)findViewById(R.id.common_list_play);
        cva = new CommonViewAdapter(this, entryList, Entry.PLAY_TYPE);
        list.setAdapter(cva);

        add = (BootstrapButton)findViewById(R.id.add_play);
        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(PlayActivity.this, AddActivity.class);
                intent.putExtra(Constant.TYPE, Entry.PLAY_TYPE);
                startActivityForResult(intent, 0);
            }
        });

        playweb = (BootstrapButton)findViewById(R.id.playweb);
        playweb.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(PlayActivity.this, ChooseTuan.class);
                intent.putExtra(Constant.TYPE, Entry.PLAY_TYPE);
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
