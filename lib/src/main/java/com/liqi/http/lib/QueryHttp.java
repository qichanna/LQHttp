
package com.liqi.http.lib;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QueryHttp {

    public static void main(String args[]) {

        OkHttpClient client = new OkHttpClient();
        HttpUrl httpUrl = HttpUrl.parse("http://localhost:8080/HttpServer/HelloServlet").
                newBuilder().
                addQueryParameter("usernmae", "liqi").
                addQueryParameter("password", "12345").
                build();
        String url = httpUrl.toString();
        System.out.println(httpUrl.toString());
        Request request = new Request.Builder().url(url).build();
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