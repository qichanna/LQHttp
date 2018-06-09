package com.liqi.process;


import com.liqi.service.Request;

import java.io.UnsupportedEncodingException;

/**
 * @author nate
 */

public class PathParamAnnotationProcess extends ParamAnnotationProcess<String, Object> {

    private String mUrl;

    public PathParamAnnotationProcess(Request.Builder builder, String annotationValue, String url) {
        super(builder, annotationValue);
        this.mUrl = url;
    }

    @Override
    public void process(Object args) throws UnsupportedEncodingException {
        String value = "{" + mAnnotationValue + "}";
        if (!mUrl.contains(value)) {
            throw new RuntimeException("path 参数不匹配" + value);
        }
        String url = mUrl.replace(value, String.valueOf(args));
        mBuilder.url(url);

    }
}
