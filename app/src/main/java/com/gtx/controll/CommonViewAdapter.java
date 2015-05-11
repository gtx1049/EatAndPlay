package com.gtx.controll;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.gtx.eatandplay.R;

/**
 * Created by Administrator on 2015/5/11.
 */
public class CommonViewAdapter extends BaseSwipeAdapter
{
    private Context context;

    public CommonViewAdapter(Context context)
    {
        this.context = context;
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

        TextView t = (TextView)view.findViewById(R.id.position);
        t.setText((i + 1) + ".");
    }

    @Override
    public int getCount()
    {
        return 4;
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
}
