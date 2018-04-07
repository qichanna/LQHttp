package com.liqi;

import com.liqi.http.HttpMethod;
import com.liqi.http.HttpRequest;

import java.io.IOException;
import java.net.URI;

public interface HttpRequestFactory {

    HttpRequest createHttpRequest(URI uri, HttpMethod method) throws IOException;
}
