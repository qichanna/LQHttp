package com.liqi.process;


import com.google.gson.Gson;
import com.liqi.http.MediaType;
import com.liqi.http.RequestBody;
import com.liqi.service.Request;

import java.io.UnsupportedEncodingException;

public class BodyParamAnnotationProcess extends ParamAnnotationProcess<String, Object> {

    public BodyParamAnnotationProcess(Request.Builder builder, String annotationValue) {
        super(builder, annotationValue);
    }

    @Override
    public void process(Object args) throws UnsupportedEncodingException {
        Gson gson = new Gson();
        String data = gson.toJson(args);
        mBuilder.requestBody(new RequestBody.FormBody(data, MediaType.APPLICATION_JSON_VALUE));
    }
}



