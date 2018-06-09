package com.liqi.lqhttp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.liqi.HttpRequestProvider;
import com.liqi.download.DownloadManager;
import com.liqi.download.file.FileStorageManager;
import com.liqi.download.http.DownloadCallback;
import com.liqi.download.utils.Logger;
import com.liqi.http.HttpMethod;
import com.liqi.service.Callback;
import com.liqi.service.HttpCall;
import com.liqi.service.LQApiProvider;
import com.liqi.service.LQRequest;
import com.liqi.service.LQResponse;
import com.liqi.service.convert.Convert;
import com.liqi.service.convert.JsonConvert;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private ImageView mImageView;
    private ProgressBar mProgress;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) findViewById(R.id.imageView);
        mProgress = (ProgressBar) findViewById(R.id.progress);

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

        /*DownloadManager.getInstance().download("https://p3.lefile.cn/product/adminweb/2018/03/23/370898ff-08c1-4bdc-80a8-f4ea56781ebd.jpg", new DownloadCallback() {
            @Override
            public void success(File file) {
                Log.d("liqi7","111 ");
                if (count < 1) {
                    count++;
                    return;
                }
                Log.d("liqi7","222");
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
                mProgress.setProgress(progress);
            }
        });*/

        Map<String, String> map = new HashMap<>();
        map.put("username", "liqi");
        map.put("password", "abcdefg");
//
       LQApiProvider.helloWorld("http://192.168.1.4:8080/HttpServer/HelloServlet", map, new LQResponse<User>() {

            @Override
            public void success(LQRequest request, final User data) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,data.toString(),500).show();
                    }
                });

                Logger.debug("liqi","success: " + data.toString());

            }

            @Override
            public void fail(int errorCode, String errorMsg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,"fail",500).show();
                    }
                });
                Logger.debug("liqi","fail: " + errorMsg);
            }
        });
    }
}
