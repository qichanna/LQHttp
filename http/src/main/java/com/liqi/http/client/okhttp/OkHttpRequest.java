package com.liqi.http.client.okhttp;

import com.liqi.BufferHttpRequest;
import com.liqi.http.HttpHeader;
import com.liqi.http.HttpMethod;
import com.liqi.http.HttpResponse;

import java.io.IOException;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpRequest extends BufferHttpRequest<OkHttpClient> {

//    private OkHttpClient mClient;
//    private HttpMethod mMethod;
//    private String mUrl;

    public OkHttpRequest(com.liqi.service.Request request, OkHttpClient client) {
        super(request, client);
    }

    @Override
    protected HttpResponse executeInternal(HttpHeader header, byte[] data) throws IOException {
        boolean isBody = getMethod() == HttpMethod.POST;
        RequestBody requestBody = null;
        if (isBody) {
            requestBody = RequestBody.create(MediaType.parse(mRequestBody.getMediaType()), mRequestBody.getData().toString());
        }
        Request.Builder builder = new Request.Builder().url(getUri()).method(getMethod().name(), requestBody);

        for (Map.Entry<String, String> entry : header.entrySet()) {
            builder.addHeader(entry.getKey(), entry.getValue());
        }
        Response response = mClient.newCall(builder.build()).execute();

        System.out.println("length " + response.body().contentLength());

        return new OkHttpResponse(response);
    }

//    @Override
//    public HttpMethod getMethod() {
//        return mMethod;
//    }
//
//    @Override
//    public String getUri() {
////        return URI.create(mUrl);
//        return mUrl;
//    }
}
