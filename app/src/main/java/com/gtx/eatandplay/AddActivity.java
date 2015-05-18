package com.gtx.eatandplay;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.gtx.model.Constant;
import com.gtx.model.Entry;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AddActivity extends ActionBarActivity
{
    private EditText nameinput;
    private EditText addressinput;
    private SeekBar adjustmoney;
    private TextView moneyvalue;

    private BootstrapButton choosetime;
    private BootstrapButton saveentry;
    private BootstrapButton description;
    private BootstrapButton addback;

    private RoundedImageView picselector;

    private String strdescription;
    private String bitmap;

    private Date date;

    private boolean dateflag;
    private boolean descriptionflag;

    private Entry pastentry;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        nameinput = (EditText)findViewById(R.id.name_input);
        addressinput = (EditText)findViewById(R.id.address_input);
        adjustmoney = (SeekBar)findViewById(R.id.adjust_money);
        moneyvalue = (TextView)findViewById(R.id.money_value);

        choosetime = (BootstrapButton)findViewById(R.id.choose_time);
        saveentry = (BootstrapButton)findViewById(R.id.save_entry);
        description = (BootstrapButton)findViewById(R.id.add_description);
        addback = (BootstrapButton)findViewById(R.id.add_back);

        picselector = (RoundedImageView)findViewById(R.id.pic_selector);

        dateflag = false;
        choosetime.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!dateflag)
                {
                    Intent intent = new Intent(AddActivity.this, TimeActivity.class);
                    startActivityForResult(intent, 0);
                }
                else
                {
                    Intent intent = new Intent(AddActivity.this, TimeActivity.class);
                    intent.putExtra(Constant.DATE_TAG, date);
                    startActivityForResult(intent, 0);
                }
            }
        });

        descriptionflag = false;
        description.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!descriptionflag)
                {
                    Intent intent = new Intent(AddActivity.this, DescriptionActivity.class);
                    startActivityForResult(intent, 0);
                }
                else
                {
                    Intent intent = new Intent(AddActivity.this, DescriptionActivity.class);
                    intent.putExtra(Constant.DES_TAG, strdescription);
                    startActivityForResult(intent, 0);
                }
            }
        });

        adjustmoney.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                moneyvalue.setText(progress + Constant.YUAN);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });

        picselector.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, Constant.RESULT_PIC);
            }
        });

        pastentry = (Entry)getIntent().getSerializableExtra(Entry.KEY_ID);
        if(pastentry == null)
        {
            saveentry.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    saveEntry();
                }
            });
        }
        else
        {
            DateFormat format = new SimpleDateFormat(Constant.TIME_FORMAT_DIS);
            nameinput.setText(pastentry.getName());
            addressinput.setText(pastentry.getAddress());
            moneyvalue.setText(pastentry.getMoney() + Constant.YUAN);
            adjustmoney.setProgress(pastentry.getMoney());
            choosetime.setText(format.format(pastentry.getDate()));
            strdescription = pastentry.getDescription();
            bitmap = pastentry.getBitmap();
            date = pastentry.getDate();
            Bitmap image = BitmapFactory.decodeFile(bitmap);
            picselector.setImageBitmap(ThumbnailUtils.extractThumbnail(image, dip2px(Constant.DP_WIDTH_L), dip2px(Constant.DP_HEIGHT_L)));
            dateflag = true;
            descriptionflag = true;

            saveentry.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    saveEntry(pastentry);
                }
            });
        }

        addback.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if(resultCode == Constant.RESULT_DATE)
        {
            DateFormat format = new SimpleDateFormat(Constant.TIME_FORMAT_DIS);
            date = (Date)intent.getSerializableExtra(Constant.DATE_TAG);
            choosetime.setText(format.format(date));
            dateflag = true;
        }
        else if(resultCode == Constant.RESULT_DES)
        {
            strdescription = intent.getStringExtra(Constant.DES_TAG);
            descriptionflag = true;
        }
        else if(requestCode == Constant.RESULT_PIC)
        {
            if(intent == null)
            {
                return;
            }

            Uri image = intent.getData();

            if(image == null)
            {
                return;
            }

            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(image, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            bitmap = picturePath;
            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
            picselector.setImageBitmap(ThumbnailUtils.extractThumbnail(bitmap, picselector.getWidth(), picselector.getHeight()));
        }
    }

    private void saveEntry()
    {
        String name = nameinput.getText().toString();
        String address = addressinput.getText().toString();
        int money = adjustmoney.getProgress();

        if (name.length() == 0)
        {
            Toast.makeText(AddActivity.this, Constant.ADD_HINT, Toast.LENGTH_SHORT);
            finish();
            return;
        }

        if (dateflag == false)
        {
            date = new Date();
        }

        Entry entry = new Entry(name, address, strdescription, date, money);
        entry.setType(getIntent().getIntExtra(Constant.TYPE, 0));
        entry.setBitmap(bitmap);
        entry.saveSelf();

        Intent intent = new Intent();
        intent.putExtra(Constant.ENTRY, entry);
        setResult(Constant.RESULT_ADD, intent);

        finish();
    }

    private void saveEntry(Entry entry)
    {
        String name = nameinput.getText().toString();
        String address = addressinput.getText().toString();
        int money = adjustmoney.getProgress();

        if (name.length() == 0)
        {
            Toast.makeText(AddActivity.this, Constant.ADD_HINT, Toast.LENGTH_SHORT);
            finish();
            return;
        }

        if (dateflag == false)
        {
            date = new Date();
        }

        entry.setName(name);
        entry.setAddress(address);
        entry.setMoney(money);
        entry.setDate(date);
        entry.setDescription(strdescription);
        entry.setBitmap(bitmap);

        entry.updateSelf();

        Intent intent = new Intent();
        intent.putExtra(Constant.ENTRY, entry);
        setResult(Constant.RESULT_UPDATE, intent);

        finish();
    }

    private int dip2px(float dpValue)
    {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
