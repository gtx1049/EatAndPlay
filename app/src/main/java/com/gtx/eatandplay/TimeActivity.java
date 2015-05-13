package com.gtx.eatandplay;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.gtx.model.Constant;
import com.squareup.timessquare.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;


public class TimeActivity extends ActionBarActivity
{
    private BootstrapButton checktime;

    private CalendarPickerView calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.MONTH, 6);

        calendar = (CalendarPickerView) findViewById(R.id.calendar_view);

        Date pre = (Date)getIntent().getSerializableExtra(Constant.DATE_TAG);
        Date today = new Date();
        if(pre == null)
        {
            calendar.init(today, nextYear.getTime()).withSelectedDate(today);
        }
        else
        {
            calendar.init(today, nextYear.getTime()).withSelectedDate(pre);
        }

        checktime = (BootstrapButton)findViewById(R.id.check_time);
        checktime.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();

                Date selecteddate = calendar.getSelectedDate();
                intent.putExtra(Constant.DATE_TAG, selecteddate);

                setResult(Constant.RESULT_DATE, intent);
                finish();
            }
        });
    }


}
