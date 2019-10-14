package com.example.caosd.videocompressor;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.File;

import fileSelect.FileChooser;
import handler.MyInvocationHandler;

public class MainActivity extends AppCompatActivity {

    private MyInvocationHandler compress;
    private int PICK_VIDEO_REQUEST = 1;
    private ShareActionProvider mShareActionProvider;
    private Uri uriPath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if(android.os.Build.VERSION.SDK_INT > 22) {
            checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectVideo();
            }
        });
        compress = new MyInvocationHandler(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menushareitem = (MenuItem) menu.findItem(R.id.menu_item_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menushareitem);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("video/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, uriPath);
        mShareActionProvider.setShareIntent(shareIntent);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == PICK_VIDEO_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                Uri uri = data.getData();
                uriPath = uri;
                try {
                    File file = new File (FileChooser.getPath(getApplicationContext(), uri));
                    compress.compress(file, getApplicationContext(), (TextView) findViewById(R.id.main_text));
                } catch (Exception e) {
                    android.util.Log.d("VideoCompressor", e.getMessage());
                }
            }
        }

    public void selectVideo() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Video"), PICK_VIDEO_REQUEST);

    }

    public void checkPermission(String permission){
        if (ContextCompat.checkSelfPermission(this,
                permission)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    permission)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{permission},0);
            }
        }
    }

    public void shareFile(View v) {
        compress.compress(null,null, null);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("video/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, uriPath);
        mShareActionProvider.setShareIntent(shareIntent);

    }


}
