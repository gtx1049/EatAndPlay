package com.gtx.controll;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.gtx.eatandplay.R;
import com.gtx.model.Constant;
import com.gtx.model.Entry;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2015/5/11.
 */
public class CommonViewAdapter extends BaseSwipeAdapter
{
    private Context context;
    private List<Entry> entryList;

    public CommonViewAdapter(Context context, List<Entry> entryList)
    {
        this.context = context;
        this.entryList = entryList;
    }

    @Override
    public int getSwipeLayoutResourceId(int i)
    {
        return R.id.swipe;
    }

    @Override
    public View generateView(int i, ViewGroup viewGroup)
    {
        return LayoutInflater.from(context).inflate(R.layout.swipe_layout, null);
    }

    @Override
    public void fillValues(int i, View view)
    {
        SwipeLayout swipeLayout = (SwipeLayout)view;
        swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        swipeLayout.addSwipeListener(new CommonListener());

        i = entryList.size() - i - 1;

        TextView nameview = (TextView)view.findViewById(R.id.name_display);
        nameview.setText(entryList.get(i).getName());

        TextView addressview = (TextView)view.findViewById(R.id.address_display);
        addressview.setText(entryList.get(i).getAddress());

        TextView timeview = (TextView)view.findViewById(R.id.time_display);
        DateFormat format = new SimpleDateFormat(Constant.TIME_FORMAT);
        timeview.setText(format.format(entryList.get(i).getDate()));

        TextView moneyview = (TextView)view.findViewById(R.id.money_display);
        moneyview.setText(entryList.get(i).getMoney() + Constant.YUAN);

        ButtonListener buttonListener = new ButtonListener();
        ImageButton editentry = (CusImageButton)view.findViewById(R.id.edit_entry);
        editentry.setId(i);
        editentry.setOnClickListener(buttonListener);

        ImageButton deleteentry = (CusImageButton)view.findViewById(R.id.delete_entry);
        deleteentry.setId(i);
        deleteentry.setOnClickListener(buttonListener);

    }

    @Override
    public int getCount()
    {
        return entryList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    public void addEntry(Entry entry)
    {
        entryList.add(entry);
    }

    public class ButtonListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {
            CusImageButton civ = (CusImageButton)v;
            int id = civ.getId();
            int entrid = civ.getentryId();
        }
    }
}
