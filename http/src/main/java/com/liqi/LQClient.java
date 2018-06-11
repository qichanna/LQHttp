package com.liqi;

import com.liqi.service.convert.Convert;
import com.liqi.service.convert.JsonConvert;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class LQClient {
    private String mUrl;
    private HttpClient mHttpClient;
    private ExecutorService mExecutor;
    private List<Convert> mConverts;
    private HashMap<Method, MethodAnnotationProcess> mMethodCache = new HashMap<>();

    public LQClient(Builder builder) {
        this.mUrl = builder.mUrl;
        this.mHttpClient = builder.mHttpClient;
        this.mExecutor = builder.mExecutor;
        this.mConverts = builder.mConverts;
    }

    public String getmUrl() {
        return mUrl;
    }

    public HttpClient getHttpClient() {
        return mHttpClient;
    }

    public ExecutorService getmExecutor() {
        return mExecutor;
    }

    public List<Convert> getmConverts() {
        return mConverts;
    }

    public <T> T create(Class<T> clazz) {
        if (!clazz.isInterface()) {
            throw new RuntimeException("clazz 必须是接口类型");
        }
        for (Method method : clazz.getMethods()) {
            MethodAnnotationProcess process = new MethodAnnotationProcess(method, this);
            process.invoke();
            mMethodCache.put(method, process);
        }
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new MethodInvocation(this, mMethodCache));
    }

    public static class Builder{
        public static final int CONNECTION_TIME_OUT = 3000;
        public static final int READER_TIME_OUT = 3000;
        public static final int WRITE_TIME_OUT = 3000;
        private String mUrl;
        private HttpClient mHttpClient;
//        private HttpRequestProvider mProvider;
        private ExecutorService mExecutor;
        private List<Convert> mConverts;

        public Builder url(String url){
            this.mUrl = url;
            return this;
        }

        public Builder executor(ExecutorService service){
            this.mExecutor = service;
            return this;
        }

        public LQClient builder(){
            List<Convert> converts = new ArrayList<>();
            converts.add(new JsonConvert());
            this.mConverts = converts;
            if(mHttpClient == null) {
                mHttpClient = new HttpClient.Builder().
                        setConnectTimeOut(CONNECTION_TIME_OUT).
                        setReaderTimeOut(READER_TIME_OUT).
                        setWriteTimeout(WRITE_TIME_OUT).
                        builder();
            }
            return new LQClient(this);
        }

    }
}
