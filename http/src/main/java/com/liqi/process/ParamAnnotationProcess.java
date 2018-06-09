package com.liqi.process;

import com.liqi.service.Request;
import java.io.UnsupportedEncodingException;

public abstract class ParamAnnotationProcess<T, K> {
    /**
     * 网络请求构建者
     */
    protected Request.Builder mBuilder;
    /**
     * Annotation的值
     */
    protected T mAnnotationValue;

    public ParamAnnotationProcess(Request.Builder builder, T annotationValue) {
        this.mBuilder = builder;
        this.mAnnotationValue = annotationValue;
    }

    public abstract void process(K args) throws UnsupportedEncodingException;

}
