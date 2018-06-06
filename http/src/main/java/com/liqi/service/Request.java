package com.liqi.service;

import com.liqi.http.HttpHeader;
import com.liqi.http.HttpMethod;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 上层的业务请求对象
 */
public class Request {
    private static final String ENCODING = "utf-8";
    private String mUrl;
    private HttpMethod mMethod;
    private HttpHeader mHeader;
    private byte[] mData;

    public String getmUrl() {
        return mUrl;
    }

    public HttpMethod getmMethod() {
        return mMethod;
    }

    public HttpHeader getmHeader() {
        return mHeader;
    }

    public byte[] getmData() {
        return mData;
    }

    public Request(Builder builder){
        this.mUrl = builder.mUrl;
        this.mMethod = builder.mMethod;
        this.mHeader = builder.mHeader;
        this.mData = encodeParam(builder.mFormParams);
    }

    public static byte[] encodeParam(Map<String, String> value) {
        if (value == null || value.size() == 0) {
            return null;
        }
        StringBuffer buffer = new StringBuffer();
        int count = 0;
        try {
            for (Map.Entry<String, String> entry : value.entrySet()) {

                buffer.append(URLEncoder.encode(entry.getKey(), ENCODING)).append("=").
                        append(URLEncoder.encode(entry.getValue(), ENCODING));
                if (count != value.size() - 1) {
                    buffer.append("&");
                }
                count++;

            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return buffer.toString().getBytes();
    }

    public static class Builder{
        private String mUrl;
        private HttpMethod mMethod;
        private HttpHeader mHeader;
        private Map<String,String> mFormParams = new HashMap<>();
        private Map<String,String> mQueryParams = new HashMap<>();

        public Builder url(String url){
            this.mUrl = url;
            return this;
        }

        public Builder httpMethod(HttpMethod method){
            this.mMethod = method;
            return this;
        }

        public Builder addFormParam(String key,String value){
            mFormParams.put(key,value);
            return this;
        }

        public Builder addQueryParam(String key,String value){
            mQueryParams.put(key,value);
            return this;
        }

        public Builder addHeader(HttpHeader httpHeader){
            this.mHeader = httpHeader;
            return this;
        }

        public Request build(){
            return new Request(this);
        }
    }
}
