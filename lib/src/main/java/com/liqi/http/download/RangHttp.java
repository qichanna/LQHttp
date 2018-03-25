package com.liqi.http.download;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RangHttp {

    public static void main(String args[]) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://p3.lefile.cn/product/adminweb/2018/03/23/370898ff-08c1-4bdc-80a8-f4ea56781ebd.jpg").
//        Request request = new Request.Builder().url("http://www.lenovo.com.cn").
                addHeader("Accept-Encoding", "identity").
                addHeader("Range", "bytes=0-").
                build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println("content-length : " + response.body().contentLength());
            if (response.isSuccessful()) {
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    System.out.println(headers.name(i) + " : " + headers.value(i));
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
