package com.gtx.controll;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.media.ThumbnailUtils;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
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

        ImageView iv = (ImageView)view.findViewById(R.id.pic_entry);
        String e = entryList.get(i).getBitmap();
        if(e != null)
        {
            LoadImage li = new LoadImage(new UpdateHandler(iv), e);
            li.start();
        }
        else
        {
            iv.setImageDrawable(context.getResources().getDrawable(R.drawable.cat));
        }

        iv.setOnClickListener(new ImageClickListener(i));
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
                sweetAlertDialog.setTitleText("确定要删除吗？");
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

    public class ImageClickListener implements View.OnClickListener
    {
        private int i;

        public ImageClickListener(int i)
        {
            this.i = i;
        }

        @Override
        public void onClick(View v)
        {
            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE);
            sweetAlertDialog.setTitleText("~详情~");
            sweetAlertDialog.setContentText(entryList.get(i).getDescription());
            sweetAlertDialog.setConfirmText("好的！");
            sweetAlertDialog.show();
        }
    }

    private int dip2px(float dpValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public class LoadImage extends Thread
    {
        Handler handler;
        String bitmap;

        public LoadImage(Handler handler, String bitmap)
        {
            this.bitmap = bitmap;
            this.handler = handler;
        }

        public void run()
        {
            BitmapFactory.Options option = new BitmapFactory.Options();
            option.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(bitmap, option);

            if(option.outHeight > Constant.MAX_BOUNDS || option.outWidth > Constant.MAX_BOUNDS)
            {
                option.inSampleSize = 4;
            }

            option.inJustDecodeBounds = false;
            Bitmap image = BitmapFactory.decodeFile(bitmap, option);

            Message msg = new Message();
            msg.obj = ThumbnailUtils.extractThumbnail(image, dip2px(Constant.DP_WIDTH), dip2px(Constant.DP_HEIGHT), ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
            handler.sendMessage(msg);
        }
    }

    public class UpdateHandler extends Handler
    {
        private ImageView iv;

        public UpdateHandler(ImageView iv)
        {
            this.iv = iv;
        }

        public void handleMessage(Message msg)
        {
            Bitmap bitmap = (Bitmap)msg.obj;
            iv.setImageBitmap(bitmap);
        }
    }

}
