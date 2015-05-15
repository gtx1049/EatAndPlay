package com.gtx.eatandplay;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapThumbnail;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.gtx.model.BitmapUtils;
import com.gtx.model.Constant;
import com.gtx.model.Entry;
import com.squareup.timessquare.CalendarPickerView;

import java.util.Calendar;
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

    private RoundedImageView picselector;

    private String strdescription;
    private String bitmap;

    private Date date;

    private boolean dateflag;
    private boolean descriptionflag;

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

        saveentry.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String name = nameinput.getText().toString();
                String address = addressinput.getText().toString();
                int money = adjustmoney.getProgress();

                Entry entry = new Entry(name, address, strdescription, date, money);
                entry.setType(getIntent().getIntExtra(Constant.TYPE, 0));
                entry.setBitmap(bitmap);
                entry.saveSelf();

                Intent intent = new Intent();
                intent.putExtra(Constant.ENTRY, entry);
                setResult(Constant.RESULT_ADD, intent);

                finish();

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
                moneyvalue.setText(progress + " Y");
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
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if(resultCode == Constant.RESULT_DATE)
        {
            date = (Date)intent.getSerializableExtra(Constant.DATE_TAG);
            choosetime.setText((date.getYear() + 1900) + "." + (date.getMonth() + 1) + "." + date.getDate());
            dateflag = true;
        }
        else if(resultCode == Constant.RESULT_DES)
        {
            strdescription = intent.getStringExtra(Constant.DES_TAG);
            descriptionflag = true;
        }
        else if(requestCode == Constant.RESULT_PIC)
        {
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
            picselector.setImageBitmap(BitmapUtils.decodeSampledBitmapFromFile(picturePath, picselector.getWidth(), picselector.getHeight()));
        }
    }
}
