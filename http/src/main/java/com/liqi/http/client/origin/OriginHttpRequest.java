package com.liqi.http.client.origin;

import com.liqi.BufferHttpRequest;
import com.liqi.http.HttpHeader;
import com.liqi.http.HttpResponse;
import com.liqi.service.Request;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Map;

public class OriginHttpRequest extends BufferHttpRequest<HttpURLConnection> {

    public OriginHttpRequest(Request request, HttpURLConnection client) {
        super(request, client);
    }

//    public OriginHttpRequest(HttpURLConnection connection, HttpMethod method, String url) {
//        this.mConnection = connection;
//        this.mUrl = url;
//        this.mMethod = method;
//    }

    @Override
    protected HttpResponse executeInternal(HttpHeader header, byte[] data) throws IOException {

        for (Map.Entry<String, String> entry : header.entrySet()) {
            mClient.addRequestProperty(entry.getKey(), entry.getValue());
        }
        mClient.setDoOutput(true);
        mClient.setDoInput(true);
        mClient.setRequestMethod(getMethod().name());
        mClient.connect();
        if (data != null && data.length > 0) {
            OutputStream out = mClient.getOutputStream();
            out.write(data, 0, data.length);
            out.close();
        }
        OriginHttpResponse response = new OriginHttpResponse(mClient);
        return response;
    }


//    @Override
//    public HttpMethod getMethod() {
//        return mMethod;
//    }
//
//    @Override
//    public URI getUri() {
//        return URI.create(mUrl);
//    }
}
