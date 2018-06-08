package com.liqi;

import com.liqi.service.convert.Convert;
import com.liqi.service.convert.JsonConvert;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class LQClient {
    private String mUrl;
    private HttpRequestProvider mProvider;
    private ExecutorService mExecutor;
    private List<Convert> mConverts;

    public LQClient(Builder builder) {
        this.mUrl = builder.mUrl;
        this.mProvider = builder.mProvider;
        this.mExecutor = builder.mExecutor;
        this.mConverts = builder.mConverts;
    }

    public String getmUrl() {
        return mUrl;
    }

    public HttpRequestProvider getmProvider() {
        return mProvider;
    }

    public ExecutorService getmExecutor() {
        return mExecutor;
    }

    public List<Convert> getmConverts() {
        return mConverts;
    }

    public static class Builder{
        private String mUrl;
        private HttpRequestProvider mProvider;
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
            if(mProvider == null){
                mProvider = new HttpRequestProvider();
            }
            if(mConverts == null){
                mConverts = new ArrayList<>();
                mConverts.add(new JsonConvert());
            }
            return new LQClient(this);
        }

    }
}
