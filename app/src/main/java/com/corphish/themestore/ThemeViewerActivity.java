package com.corphish.themestore;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.corphish.themestore.R;
import com.corphish.themestore.Theme;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.io.File;

public class ThemeViewerActivity extends AppCompatActivity {

    long downloadReferenceId;
    Theme theme;

    DownloadManager downloadManager;
    BroadcastReceiver downloadReciever;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_viewer);

        Intent intent = getIntent();
        theme = (Theme)intent.getSerializableExtra("Theme");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);


        downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);

        setTitle(theme.getName());

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse(theme.getDownloadUrl());
                DownloadManager.Request request = new DownloadManager.Request(uri);

                request.setTitle(theme.getName());
                request.setDescription("StryFlex ROM Theme");

                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);

                request.setDestinationInExternalFilesDir(ThemeViewerActivity.this, Environment.DIRECTORY_DOWNLOADS,theme.getPackageName() + ".apk");

                downloadReferenceId = downloadManager.enqueue(request);

                // TODO: To update FAB when download has started, like disable the icon (grey out it).
                fab.setClickable(false);
            }
        });

        ImageView imageView = (ImageView)findViewById(R.id.banner_image);
        UrlImageViewHelper.setUrlDrawable(imageView,theme.getBannerUrl(),R.drawable.back);

        TextView textView_name = (TextView)findViewById(R.id.theme_name);
        textView_name.setTypeface(null, Typeface.BOLD);
        textView_name.setText(theme.getName());

        TextView textView_desc = (TextView)findViewById(R.id.theme_desc);
        textView_desc.setGravity(Gravity.CENTER);
        textView_desc.setText(theme.getDescription());

        downloadReciever = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if(downloadReferenceId == referenceId) {
                    String action = intent.getAction();
                    if(DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                        String filePath = Environment.getExternalStorageDirectory() + "/Android/data/" + getApplicationContext().getPackageName() + "/files/Download/" + theme.getPackageName() + ".apk";
                        Intent promptInstall = new Intent(Intent.ACTION_VIEW)
                                .setDataAndType(Uri.fromFile(new File(filePath)),
                                        "application/vnd.android.package-archive");
                        Log.i(Constants.LOG_TAG,"To install - " + Uri.fromFile(new File(filePath)));
                        startActivity(promptInstall);
                    }
                }
            }
        };

        registerReceiver(downloadReciever, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(downloadReciever);
    }
}
