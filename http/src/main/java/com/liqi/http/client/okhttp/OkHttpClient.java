package com.liqi.http.client.okhttp;

import com.liqi.Call;
import com.liqi.HttpClient;
import com.liqi.http.HttpResponse;
import com.liqi.service.Request;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class OkHttpClient implements Call{

    private okhttp3.OkHttpClient mClient;

    public OkHttpClient(HttpClient client){
        okhttp3.OkHttpClient.Builder builder = new okhttp3.OkHttpClient.Builder();
        builder.readTimeout(client.getmReaderTimeOut(), TimeUnit.MILLISECONDS);
        builder.writeTimeout(client.getmWriteTimeOut(), TimeUnit.MILLISECONDS);
        builder.connectTimeout(client.getmConnectTimeOut(), TimeUnit.MILLISECONDS);
        if (client.getSSLSocketFactory() != null) {
            builder.sslSocketFactory(client.getSSLSocketFactory());
        }
        if (client.getHostnameVerifier() != null) {
            builder.hostnameVerifier(client.getHostnameVerifier());
        }
        mClient = builder.build();
    }

    @Override
    public HttpResponse execute(Request request) throws IOException {
        return new OkHttpRequest(request, mClient).execute();
    }
}
