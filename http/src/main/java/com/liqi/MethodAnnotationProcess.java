package com.liqi;

import com.liqi.annotation.DELETE;
import com.liqi.annotation.Field;
import com.liqi.annotation.FieldMap;
import com.liqi.annotation.GET;
import com.liqi.annotation.Header;
import com.liqi.annotation.POST;
import com.liqi.annotation.PUT;
import com.liqi.annotation.Path;
import com.liqi.annotation.Query;
import com.liqi.annotation.QueryMap;
import com.liqi.http.HttpHeader;
import com.liqi.http.HttpMethod;
import com.liqi.process.FieldMapParamAnnotationProcess;
import com.liqi.process.FieldParamAnnotationProcess;
import com.liqi.process.ParamAnnotationProcess;
import com.liqi.process.PathParamAnnotationProcess;
import com.liqi.process.QueryMapParamAnnotationProcess;
import com.liqi.process.QueryParamAnnotationProcess;
import com.liqi.service.Request;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 注解处理类
 */

public class MethodAnnotationProcess {

    private Annotation[] mMethodAnnotation;
    private Annotation[][] mMethodParamAnnotation;
    private LQClient mLqClient;
    private HttpMethod mHttpMethod;
    private String mPathUrl;
    private HttpHeader mHttpHeader;
    private Request.Builder mBuilder = new Request.Builder();
    private String mRealUrl;
    private List<ParamAnnotationProcess> mAnnotationProcess = new ArrayList<>();

    public MethodAnnotationProcess(Method method, LQClient client) {
        this.mMethodAnnotation = method.getAnnotations();
        this.mMethodParamAnnotation = method.getParameterAnnotations();
        this.mLqClient = client;
        this.mRealUrl = client.getmUrl();
    }

    public void invoke() {
        for (Annotation annotation : mMethodAnnotation) {
            processMethodAnnotation(annotation);
        }
        mRealUrl = mRealUrl + mPathUrl;
        for (int i = 0; i < mMethodParamAnnotation.length; i++) {
            Annotation[] paramAnnotation = mMethodParamAnnotation[i];
            if (paramAnnotation == null) {
                throw new RuntimeException("第" + i + "个参数没有注解");
            }
            for (Annotation annotation : paramAnnotation) {
                processMethodParamAnnotation(annotation);
            }
        }

    }

    public Request builderRequest(Object[] args) throws IOException {
        String url = mLqClient.getmUrl() + mPathUrl;
        mBuilder.url(url).
                addHeader(mHttpHeader).
                httpMethod(mHttpMethod);
        for (int i = 0; i < mAnnotationProcess.size(); i++) {
            mAnnotationProcess.get(i).process(args[i]);
        }
        return mBuilder.build();
    }

    /**
     * 处理方法上的注解
     *
     * @param annotation {@link Annotation}
     */
    private void processMethodParamAnnotation(Annotation annotation) {
        ParamAnnotationProcess process = null;
        if (annotation instanceof Field) {
            Field field = (Field) annotation;
            process = new FieldParamAnnotationProcess(mBuilder, field.value(), field.encoded());
        } else if (annotation instanceof Query) {
            Query query = (Query) annotation;
            process = new QueryParamAnnotationProcess(mBuilder, query.value(), query.encoded());
        } else if (annotation instanceof QueryMap) {
            QueryMap query = (QueryMap) annotation;
            process = new QueryMapParamAnnotationProcess(mBuilder, query.value(), query.encoded());
        } else if (annotation instanceof Path) {
            Path path = (Path) annotation;
            process = new PathParamAnnotationProcess(mBuilder, path.value(), mRealUrl);
        } else if (annotation instanceof FieldMap) {
            FieldMap query = (FieldMap) annotation;
            process = new FieldMapParamAnnotationProcess(mBuilder, query.value(), query.encoded());
        }
//        else if (annotation instanceof Body) {
//            process = new BodyParamAnnotationProcess(mBuilder, null);
//        }
        mAnnotationProcess.add(process);
    }

    /**
     * 处理方法参数上的注解
     *
     * @param annotation {@link Annotation}
     */
    private void processMethodAnnotation(Annotation annotation) {
        if (annotation instanceof POST) {
            POST post = (POST) annotation;
            mHttpMethod = HttpMethod.POST;
            mPathUrl = post.value();

        } else if (annotation instanceof GET) {
            GET get = (GET) annotation;
            mHttpMethod = HttpMethod.GET;
            mPathUrl = get.value();

        } else if (annotation instanceof PUT) {
            PUT put = (PUT) annotation;
            mHttpMethod = HttpMethod.PUT;
            mPathUrl = put.value();

        } else if (annotation instanceof DELETE) {
            DELETE delete = (DELETE) annotation;
            mHttpMethod = HttpMethod.DELETE;
            mPathUrl = delete.value();

        } else if (annotation instanceof Header) {
            Header header = (Header) annotation;
            mHttpHeader = new HttpHeader();
            for (String value : header.value()) {
                String[] result = value.split(":");
                mHttpHeader.set(result[0], result[1]);
            }
        } /*else if (annotation instanceof Retry) {
            Retry retry = (Retry) annotation;
            mBuilder.retryCount(retry.value());
        }*/
    }
}
