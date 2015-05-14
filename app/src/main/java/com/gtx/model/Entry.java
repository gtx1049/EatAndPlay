package com.gtx.model;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.widget.ListView;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/5/12.
 */
public class Entry implements Serializable
{
    public static final int EAT_TYPE = 1;
    public static final int DRINK_TYPE = 2;
    public static final int PLAY_TYPE = 3;
    public static final int HAPPY_TYPE = 4;

    public static final String TABLE = "entry";
    public static final String KEY_NAME = "name";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_DATE = "predate";
    public static final String KEY_MONEY = "money";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_TYPE = "type";

    private static SQLiteDatabase db = null;

    private String name;
    private String description;
    private String address;
    private Date date;
    private int money;
    private String bitmap;
    private int type;

    private static String CRATE = "CREATE TABLE entry (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                       "name VARCHAR, description VARCHAR, address VARCHAR," +
                                                       "predate DATE, money INTEGER, image VARCHAR, type INTEGER)";
    private static String QUERY = "select * from entry where type=?";

    public Entry(String name, String address, String description, Date date, int money)
    {
        this.name = name;
        this.address = address;
        this.description = description;
        this.date = date;
        this.money = money;
    }

    public boolean saveSelf()
    {
        DateFormat format = new SimpleDateFormat(Constant.TIME_FORMAT);

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, name);
        values.put(KEY_DATE, format.format(date));
        values.put(KEY_DESCRIPTION, description);
        values.put(KEY_ADDRESS, address);
        values.put(KEY_MONEY, money);
        values.put(KEY_IMAGE, bitmap);
        values.put(KEY_TYPE, type);

        if(db != null)
        {
            long id = db.insert(TABLE, null, values);
            System.out.println(id);
        }

        return true;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public int getMoney()
    {
        return money;
    }

    public void setMoney(int money)
    {
        this.money = money;
    }

    public String getBitmap()
    {
        return bitmap;
    }

    public void setBitmap(String bitmap)
    {
        this.bitmap = bitmap;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public static boolean initialDatabase(Context context)
    {
        db = context.openOrCreateDatabase(Constant.DATABASE, Context.MODE_PRIVATE, null);
        SharedPreferences preferences = context.getSharedPreferences(Constant.SP, Context.MODE_PRIVATE);
        if(preferences.getBoolean(Constant.FIRST_FLAG, false) == false)
        {

            db.execSQL(CRATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(Constant.FIRST_FLAG, true);
            editor.commit();
        }

        return true;
    }

    public static List<Entry> getEntrylist(Integer type)
    {
        List<Entry> entryList = new ArrayList<>();


        Cursor cursor = db.rawQuery(QUERY, new String[]{type.toString()});

        while (cursor.moveToNext())
        {
            String name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
            String description = cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION));
            String strdate = cursor.getString(cursor.getColumnIndex(KEY_DATE));
            String address = cursor.getString(cursor.getColumnIndex(KEY_ADDRESS));
            int money = cursor.getInt(cursor.getColumnIndex(KEY_MONEY));
            String image = cursor.getString(cursor.getColumnIndex(KEY_IMAGE));

            Date date = new Date();
            try
            {
                SimpleDateFormat sdf = new SimpleDateFormat(Constant.TIME_FORMAT);
                date = sdf.parse(strdate);
            }catch (Exception e)
            {
                e.printStackTrace();
            }


            Entry entry = new Entry(name, address, description, date, money);
            entry.setBitmap(image);

            entryList.add(entry);
        }

        return entryList;
    }
}
