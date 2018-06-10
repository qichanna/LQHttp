package com.liqi;

import com.liqi.http.HttpMethod;
import com.liqi.http.HttpRequest;
import com.liqi.service.Request;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import javax.net.ssl.SSLSocketFactory;

public interface HttpRequestFactory {

    HttpRequest createHttpRequest(Request request) throws IOException;

    SSLSocketFactory getSocketFactory(InputStream inputStream) throws Exception;
}
