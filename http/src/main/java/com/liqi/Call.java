package com.liqi;

import com.liqi.http.HttpResponse;
import com.liqi.service.Request;

import java.io.IOException;

public interface Call {
    HttpResponse execute(Request request) throws IOException;
}
