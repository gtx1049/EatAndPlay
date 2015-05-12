package com.gtx.model;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import java.util.Date;

/**
 * Created by Administrator on 2015/5/12.
 */
public class Entry
{
    public static final int EAT_TYPE = 1;
    public static final int DRINK_TYPE = 2;
    public static final int PLAY_TYPE = 3;
    public static final int HAPPY_TYPE = 4;

    public static final String KEY_NAME = "name";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_DATE = "predate";
    public static final String KEY_MONEY = "money";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_TYPE = "type";

    private static SQLiteDatabase db = null;

    private String name;
    private String description;
    private Date date;
    private int money;
    private Bitmap bitmap;
    private int type;

    private static String CRATE = "CREATE TABLE entry (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                       "name VARCHAR, description VARCHAR, " +
                                                       "predate DATE, money INTEGER, image BLOB, type INTEGER)";

    public Entry(String name, String description, Date date, int money)
    {
        this.name = name;
        this.description = description;
        this.date = date;
        this.money = money;
    }

    public boolean saveSelf()
    {
        ContentValues values = new ContentValues();

        values.put(KEY_NAME, name);
        values.put(KEY_DATE, date.toString());
        values.put(KEY_DESCRIPTION, description);
        values.put(KEY_MONEY, money);
        values.put(KEY_TYPE, type);

        if(db != null)
        {
            db.replace("entry", null, values);
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

    public Bitmap getBitmap()
    {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap)
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

    public static boolean initialDatabase(Context context)
    {
        db = context.openOrCreateDatabase("eat_play.db", Context.MODE_PRIVATE, null);
        SharedPreferences preferences = context.getSharedPreferences("eat_play", Context.MODE_PRIVATE);
        if(preferences.getBoolean("is_first", false) == false)
        {
            System.out.println("first create");
            db.execSQL(CRATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("is_first", true);
            editor.commit();
        }else
            System.out.println("first create");

        return true;
    }
}
