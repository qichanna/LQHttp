package com.liqi.lqhttp;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.liqi.download.db.DownloadHelper;
import com.liqi.download.file.FileStorageManager;
import com.liqi.download.http.HttpManager;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FileStorageManager.getInstance().init(this);
        HttpManager.getInstance().init(this);
        Stetho.initializeWithDefaults(this);
        DownloadHelper.getInstance().init(this);
//
//        DownloadConfig config = new DownloadConfig.Builder()
//                .setCoreThreadSize(2)
//                .setMaxThreadSize(4)
//                .setLocalProgressThreadSize(1)
//                .builder();
//        DownloadManager.getInstance().init(config);
//
//        LeakCanary.install(this);
    }
}
