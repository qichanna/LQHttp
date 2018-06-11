package com.liqi.http.client.origin;

import com.liqi.Call;
import com.liqi.HttpClient;
import com.liqi.http.HttpResponse;
import com.liqi.service.Request;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class OriginHttpClient implements Call {
    private HttpClient mClient;
    private HttpURLConnection mConnection;

    public OriginHttpClient(HttpClient client) {
        this.mClient = client;
    }

    @Override
    public HttpResponse execute(Request request) throws IOException {
        URL url = new URL(request.getmUrl());
        if (request.getmUrl().startsWith("https")) {
            mConnection = (HttpsURLConnection) url.openConnection();
            if (mClient.getSSLSocketFactory() != null) {
                ((HttpsURLConnection) mConnection).setSSLSocketFactory(mClient.getSSLSocketFactory());
            }
            if (mClient.getHostnameVerifier() != null) {
                ((HttpsURLConnection) mConnection).setHostnameVerifier(mClient.getHostnameVerifier());
            }
        } else {
            mConnection = (HttpURLConnection) url.openConnection();
        }
        mConnection.setRequestMethod(request.getmMethod().name());
        mConnection.setReadTimeout(mClient.getmReaderTimeOut());
        mConnection.setConnectTimeout(mClient.getmConnectTimeOut());
        return new OriginHttpRequest(request, mConnection).execute();
    }
}
