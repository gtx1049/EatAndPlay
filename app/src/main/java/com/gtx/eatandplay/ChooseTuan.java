package com.gtx.eatandplay;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.gtx.model.Constant;
import com.gtx.model.Entry;


public class ChooseTuan extends ActionBarActivity
{
    private ImageButton ibtnmeituan;
    private ImageButton ibtndazhong;
    private ImageButton ibtnnuomi;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_tuan);

        ibtnmeituan = (ImageButton)findViewById(R.id.gotomeituan);
        ibtndazhong = (ImageButton)findViewById(R.id.gotodazhong);
        ibtnnuomi = (ImageButton)findViewById(R.id.gotonuomi);

        ibtnmeituan.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ChooseTuan.this, PageContent.class);
                //intent.putExtra(Constant.TYPE, Entry.DRINK_TYPE);
                startActivityForResult(intent, 0);
            }
        });

        ibtndazhong.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ChooseTuan.this, PageContent.class);
                //intent.putExtra(Constant.TYPE, Entry.DRINK_TYPE);
                startActivityForResult(intent, 0);
            }
        });

        ibtnnuomi.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ChooseTuan.this, PageContent.class);
                //intent.putExtra(Constant.TYPE, Entry.DRINK_TYPE);
                startActivityForResult(intent, 0);
            }
        });
    }

}
