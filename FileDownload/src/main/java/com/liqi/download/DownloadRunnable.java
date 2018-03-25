package com.liqi.download;

import android.os.Process;


import com.liqi.download.file.FileStorageManager;
import com.liqi.download.http.DownloadCallback;
import com.liqi.download.http.HttpManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.Response;

public class DownloadRunnable implements Runnable {

    private long mStart;

    private long mEnd;

    private String mUrl;

    private DownloadCallback mCallback;

//    private DownloadEntity mEntity;

//    public DownloadRunnable(long mStart, long mEnd, String mUrl, DownloadCallback mCallback, DownloadEntity mEntity) {
//        this.mStart = mStart;
//        this.mEnd = mEnd;
//        this.mUrl = mUrl;
//        this.mCallback = mCallback;
//        this.mEntity = mEntity;
//    }

    public DownloadRunnable(long mStart, long mEnd, String mUrl, DownloadCallback mCallback) {
        this.mStart = mStart;
        this.mEnd = mEnd;
        this.mUrl = mUrl;
        this.mCallback = mCallback;
    }

    @Override
    public void run() {

        android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        Response response = HttpManager.getInstance().syncRequestByRange(mUrl, mStart, mEnd);
        if (response == null && mCallback != null) {
            mCallback.fail(HttpManager.NETWORK_ERROR_CODE, "网络出问题了");
            return;
        }
        File file = FileStorageManager.getInstance().getFileByName(mUrl);

//        long finshProgress = mEntity.getProgress_position() == null ? 0 : mEntity.getProgress_position();
        long progress = 0;
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
            randomAccessFile.seek(mStart);
            byte[] buffer = new byte[1024 * 500];
            int len;
            InputStream inStream = response.body().byteStream();
            while ((len = inStream.read(buffer, 0, buffer.length)) != -1) {
                randomAccessFile.write(buffer, 0, len);
                progress += len;
//                mEntity.setProgress_position(progress);
//                Logger.debug("nate", "progress  ----->" + progress);
            }

//            mEntity.setProgress_position(mEntity.getProgress_position() + finshProgress);
            randomAccessFile.close();
            mCallback.success(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//            DownloadHelper.getInstance().insert(mEntity);
        }

    }
}
