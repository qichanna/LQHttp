package com.liqi.lqhttp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.liqi.download.DownloadManager;
import com.liqi.download.file.FileStorageManager;
import com.liqi.download.http.DownloadCallback;
import com.liqi.download.http.HttpManager;
import com.liqi.download.utils.Logger;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private ImageView mImageView;
    private ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) findViewById(R.id.imageView);

        File file = FileStorageManager.getInstance().getFileByName("http://www.lenovo.com.cn");
        Logger.debug("liqi", "file path = " + file.getAbsoluteFile());

        /*HttpManager.getInstance().asyncRequest("https://p3.lefile.cn/product/adminweb/2018/03/23/370898ff-08c1-4bdc-80a8-f4ea56781ebd.jpg", new DownloadCallback() {
            @Override
            public void success(File file) {
                final Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mImageView.setImageBitmap(bitmap);
                    }
                });
                Logger.debug("liqi", "success " + file.getAbsoluteFile());
            }

            @Override
            public void fail(int errorCode, String errorMessage) {
                Logger.debug("liqi", "fail " + errorCode + "  " + errorMessage);
            }

            @Override
            public void progress(int progress) {

            }
        });*/

        DownloadManager.getInstance().download("https://p3.lefile.cn/product/adminweb/2018/03/23/370898ff-08c1-4bdc-80a8-f4ea56781ebd.jpg", new DownloadCallback() {
            @Override
            public void success(File file) {
                final Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mImageView.setImageBitmap(bitmap);
                    }
                });
            }

            @Override
            public void fail(int errorCode, String errorMessage) {

            }

            @Override
            public void progress(int progress) {

            }
        });
    }
}
