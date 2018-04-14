package com.liqi.service.convert;

import com.liqi.http.HttpResponse;

import java.io.IOException;
import java.lang.reflect.Type;

public interface Convert {

    Object parse(HttpResponse response, Type type) throws IOException;

    boolean isCanParse(String contentType);

    Object parse(String content, Type type) throws IOException;
}
