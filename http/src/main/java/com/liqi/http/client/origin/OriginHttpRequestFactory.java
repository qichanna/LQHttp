package com.liqi.http.client.origin;

import com.liqi.HttpRequestFactory;
import com.liqi.http.HttpMethod;
import com.liqi.http.HttpRequest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;

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
    public HttpRequest createHttpRequest(URI uri, HttpMethod method) throws IOException {
        mConnection = (HttpURLConnection) uri.toURL().openConnection();
        return new OriginHttpRequest(mConnection, method, uri.toString());
    }
}
