package com.liqi.http.client.origin;

import com.liqi.HttpRequestFactory;
import com.liqi.http.HttpMethod;
import com.liqi.http.HttpRequest;
import com.liqi.service.Request;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;

import javax.net.ssl.SSLSocketFactory;

public class OriginHttpRequestFactory implements HttpRequestFactory {

    private HttpURLConnection mConnection;

    public OriginHttpRequestFactory() {

    }

    public void setReadTimeOut(int readTimeOut) {
        mConnection.setReadTimeout(readTimeOut);
    }

    public void setConnectionTimeOut(int connectionTimeOut) {
        mConnection.setConnectTimeout(connectionTimeOut);
    }

    @Override
    public HttpRequest createHttpRequest(Request request) throws IOException {
        return null;
    }

    @Override
    public SSLSocketFactory getSocketFactory(InputStream inputStream) throws Exception {
        return null;
    }
}
