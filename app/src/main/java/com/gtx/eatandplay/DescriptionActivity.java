package com.gtx.eatandplay;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.gtx.model.Constant;


public class DescriptionActivity extends ActionBarActivity
{
    private BootstrapButton checkdes;

    private EditText editText;
    private String description;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        editText = (EditText)findViewById(R.id.description_input);

        checkdes = (BootstrapButton)findViewById(R.id.check_description);
        checkdes.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();
                description = editText.getText().toString();
                intent.putExtra(Constant.DES_TAG, description);
                setResult(Constant.RESULT_DES, intent);
                finish();
            }
        });

        description = getIntent().getStringExtra(Constant.DES_TAG);
        if(description != null)
        {
            editText.setText(description);
            editText.setSelection(description.length());
        }
    }


}
