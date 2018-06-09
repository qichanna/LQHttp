package com.liqi.process;


import com.liqi.service.Request;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author nate
 */

public class FieldMapParamAnnotationProcess extends ParamAnnotationProcess<String, Map<String, String>> {

    private boolean mEncoded;

    public FieldMapParamAnnotationProcess(Request.Builder builder, String annotationValue, boolean encoded) {
        super(builder, annotationValue);
        this.mEncoded = encoded;
    }

    @Override
    public void process(Map<String, String> args) throws UnsupportedEncodingException {
        if (args == null || args.size() == 0) {
            return;
        }
        for (Map.Entry<String, String> entry : args.entrySet()) {
            mBuilder.addFormParam(entry.getKey(), mEncoded ? URLEncoder.encode(entry.getValue()) : entry.getValue());
        }
    }
}
