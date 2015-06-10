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

    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_tuan);

        type = getIntent().getIntExtra(Constant.TYPE, 0);

        ibtnmeituan = (ImageButton)findViewById(R.id.gotomeituan);
        ibtndazhong = (ImageButton)findViewById(R.id.gotodazhong);
        ibtnnuomi = (ImageButton)findViewById(R.id.gotonuomi);

        ibtnmeituan.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ChooseTuan.this, PageContent.class);
                intent.putExtra(Constant.TYPE, type);
                intent.putExtra(Constant.KEY_TUAN, Constant.KEY_MEITUAN);
                startActivityForResult(intent, 0);
            }
        });

        ibtndazhong.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ChooseTuan.this, PageContent.class);
                intent.putExtra(Constant.TYPE, type);
                intent.putExtra(Constant.KEY_TUAN, Constant.KEY_DAZHONG);
                startActivityForResult(intent, 0);
            }
        });

        ibtnnuomi.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ChooseTuan.this, PageContent.class);
                intent.putExtra(Constant.TYPE, type);
                intent.putExtra(Constant.KEY_TUAN, Constant.KEY_NUOMI);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if(resultCode == Constant.RESULT_ADD)
        {
            Entry entry = (Entry)intent.getSerializableExtra(Constant.ENTRY);
            Intent backintent = new Intent();
            backintent.putExtra(Constant.ENTRY, entry);
            setResult(Constant.RESULT_ADD, backintent);

            finish();
        }
    }

}
