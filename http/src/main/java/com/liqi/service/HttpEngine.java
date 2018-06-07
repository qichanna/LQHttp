package com.liqi.service;

import com.liqi.http.HttpResponse;

import java.io.IOException;

public interface HttpEngine {
    HttpResponse execute() throws IOException;
}
