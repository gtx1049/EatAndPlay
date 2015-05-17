package com.gtx.controll;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.gtx.eatandplay.AddActivity;
import com.gtx.eatandplay.R;
import com.gtx.model.Constant;
import com.gtx.model.Entry;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2015/5/11.
 */
public class CommonViewAdapter extends BaseSwipeAdapter
{
    private Context context;
    private List<Entry> entryList;
    private ButtonListener buttonListener;
    private int entrytype;

    public CommonViewAdapter(Context context, List<Entry> entryList, int entrytype)
    {
        this.context = context;
        this.entryList = entryList;
        buttonListener = new ButtonListener(this);
        this.entrytype = entrytype;
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
        swipeLayout.close(false);

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

        CusImageButton editentry = (CusImageButton)view.findViewById(R.id.edit_entry);
        editentry.setentryId(entryList.get(i).getId());
        editentry.setOnClickListener(buttonListener);

        CusImageButton deleteentry = (CusImageButton)view.findViewById(R.id.delete_entry);
        deleteentry.setentryId(entryList.get(i).getId());
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

    public void deleteEntry(int id)
    {
        for(int i = 0; i < entryList.size(); i++)
        {
            if(entryList.get(i).getId() == id)
            {
                entryList.get(i).deleteSelf();
                entryList.remove(i);
                return;
            }
        }
    }

    public Entry findEntry(int id)
    {
        for(int i = 0; i < entryList.size(); i++)
        {
            if(entryList.get(i).getId() == id)
            {
                return entryList.get(i);
            }
        }
        return  null;
    }

    public void updateEntry(Entry entry)
    {
        Entry target = findEntry(entry.getId());
        target.copyEntry(entry);
    }

    public class ButtonListener implements View.OnClickListener
    {
        private  CommonViewAdapter cva;

        private int entryid;

        public ButtonListener(CommonViewAdapter cva)
        {
            super();
            this.cva = cva;
        }

        @Override
        public void onClick(View v)
        {
            CusImageButton civ = (CusImageButton)v;
            int id = civ.getId();
            entryid = civ.getentryId();

            if(id == R.id.delete_entry)
            {
                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(cva.context, SweetAlertDialog.WARNING_TYPE);
                sweetAlertDialog.setTitleText("确定要删除？");
                sweetAlertDialog.setConfirmText("是");
                sweetAlertDialog.setCancelText("取消");

                sweetAlertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener()
                {
                    @Override
                    public void onClick(SweetAlertDialog sDialog)
                    {
                        sDialog.cancel();
                    }
                });
                sweetAlertDialog.showCancelButton(true);

                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener()
                {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog)
                    {
                        cva.deleteEntry(entryid);
                        cva.notifyDataSetChanged();
                        sweetAlertDialog.dismiss();
                    }
                });

                sweetAlertDialog.show();


            }
            else if(id == R.id.edit_entry)
            {
                Intent intent = new Intent(context, AddActivity.class);
                intent.putExtra(Constant.TYPE, entrytype);
                intent.putExtra(Entry.KEY_ID, cva.findEntry(entryid));
                ((Activity)context).startActivityForResult(intent, Constant.REQUEST_EDIT);
            }
        }
    }
}
