package com.liqi;

import com.liqi.http.HttpRequest;
import com.liqi.http.client.okhttp.OkHttpRequestFactory;
import com.liqi.http.client.origin.OriginHttpRequestFactory;
import com.liqi.service.Request;
import com.liqi.utils.Utills;

import java.io.IOException;

public class HttpRequestProvider {

    private static boolean OKHTTP_REQUEST = Utills.isExist("okhttp3.OkHttpClient", HttpRequestProvider.class.getClassLoader());

    private HttpRequestFactory mHttpRequestFactory;

    public HttpRequestProvider() {
        if (OKHTTP_REQUEST) {
            mHttpRequestFactory = new OkHttpRequestFactory();
        } else {
            mHttpRequestFactory = new OriginHttpRequestFactory();
        }
    }

    public HttpRequest getHttpRequest(Request request) throws IOException {
        return mHttpRequestFactory.createHttpRequest(request);
    }

    public HttpRequestFactory getHttpRequestFactory() {
        return mHttpRequestFactory;
    }

    public void setHttpRequestFactory(HttpRequestFactory httpRequestFactory) {
        mHttpRequestFactory = httpRequestFactory;
    }
}
