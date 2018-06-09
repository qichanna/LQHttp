package com.liqi.process;

import com.liqi.service.Request;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class FieldParamAnnotationProcess extends ParamAnnotationProcess<String, Object> {

    private static final String ENCODING = "utf-8";
    private boolean mEncoded;

    public FieldParamAnnotationProcess(Request.Builder builder, String annotationValue, boolean encoded) {
        super(builder, annotationValue);
        this.mEncoded = encoded;
    }

    @Override
    public void process(Object args) throws UnsupportedEncodingException {
        mBuilder.addFormParam(mAnnotationValue, mEncoded ? URLEncoder.encode(String.valueOf(args), ENCODING) : (String) args);

    }
}
