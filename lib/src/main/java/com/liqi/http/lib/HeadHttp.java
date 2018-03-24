package com.liqi.http.lib;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HeadHttp {

    public static void main(String args[]) {
        String str = "1234";

        System.out.println(str.substring(0, str.length() - 3));

        int a;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().
                url("http://www.baidu.com").
                addHeader("User-Agent", "from liqi").
                addHeader("Range", "bytes=2-").
                addHeader("Accept-Encoding", "identity").
                build();
        try {
            Response response = client.newCall(request).execute();
//            System.out.println(response.body().string());
            System.out.println("size=" + response.body().contentLength());
            System.out.println("type=" + response.body().contentType());
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