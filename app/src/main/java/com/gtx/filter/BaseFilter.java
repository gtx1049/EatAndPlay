package com.gtx.model;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Administrator on 2015/5/19.
 */
public class BaseFilter
{
    private static final int BUFF_LEN = 1024;
    private static final int RES_SUC = 200;

    protected Context context;

    public BaseFilter(Context context)
    {
        this.context = context;
    }

    public Entry getEntry(String url)
    {
        return null;
    };

    protected void writeToFile(String filename, byte[] filecontent)
    {
        try{

            FileOutputStream fout = context.openFileOutput(filename, context.MODE_PRIVATE);

            fout.write(filecontent);

            fout.close();

            Toast.makeText(context, "Write!", Toast.LENGTH_SHORT);

        }

        catch(Exception e){

            e.printStackTrace();

        }
    }

    protected void writeToFileEx(String filename, byte[] filecontent)
    {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);

        if(sdCardExist)
        {
            sdDir = Environment.getExternalStorageDirectory();
        }
        String root =  sdDir.toString();
        root += "/EatAndPlay/";

        try
        {
            File directory = new File(root);
            if(!directory.exists())
            {
                directory.mkdirs();
            }

            File jpg = new File(root + filename);
            jpg.createNewFile();

            FileOutputStream fos = new FileOutputStream(jpg);
            fos.write(filecontent, 0, filecontent.length);
            fos.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    protected void savePic(String url, final String path)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
            {
                Toast.makeText(context, "Success!", Toast.LENGTH_SHORT);
                writeToFileEx(path, responseBody);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
            {
                Toast.makeText(context, "Failure!", Toast.LENGTH_SHORT);
            }

            @Override
            public void onStart()
            {
                Toast.makeText(context, "Start!", Toast.LENGTH_SHORT);
                // called before request is started
            }
        });
    }
}
