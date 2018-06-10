package com.liqi;

import com.liqi.http.HttpMethod;
import com.liqi.http.HttpRequest;
import com.liqi.service.Request;

import java.io.IOException;
import java.net.URI;

public interface HttpRequestFactory {

    public HttpRequest createHttpRequest(Request request) throws IOException;
}
