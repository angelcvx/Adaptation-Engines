package com.example.caosd.videocompressor;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.File;

import compress.CompressInterface;
import fileSelect.FileChooser;
import generator.Generator;

public class MainActivity extends AppCompatActivity {

    private int PICK_VIDEO_REQUEST = 1;
    private CompressInterface myProxy;
    private static File optimizedFile;
    private ShareActionProvider mShareActionProvider;
    private Uri uriPath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectVideo();
            }
        });
        myProxy = (CompressInterface) new Generator().generateObjectsFunction(this);
        optimizedFile = getApplication().getCacheDir();
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
                myProxy.compress(file, getApplicationContext(), (TextView) findViewById(R.id.main_text), true);
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

    public File getOptimizedFile() {
        return optimizedFile;
    }

    public void shareFile(View v) {
        myProxy.compress(null,null,null,false);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("video/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, uriPath);
        mShareActionProvider.setShareIntent(shareIntent);

    }
}
