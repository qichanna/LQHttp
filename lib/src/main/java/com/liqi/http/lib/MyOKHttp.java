package com.liqi.http.lib;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyOKHttp {
    public static void main(String args[]) {
        OkHttpClient client = new OkHttpClient();

//        RequestBody body=RequestBody.create(null,new byte[0]);
        Request request = new Request.Builder().
                url("http://www.baidu.com").method("GET",null).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                System.out.println(response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
