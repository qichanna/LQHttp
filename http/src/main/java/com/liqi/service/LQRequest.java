package com.liqi.service;

import com.liqi.http.HttpMethod;

/**
 * @deprecated
 */
public class LQRequest {

    private String mUrl;

    private HttpMethod mMethod;

    private byte[] mData;

    private LQResponse mResponse;

    private String mContentType;

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public HttpMethod getMethod() {
        return mMethod;
    }

    public void setMethod(HttpMethod method) {
        mMethod = method;
    }

    public byte[] getData() {
        return mData;
    }

    public void setData(byte[] data) {
        mData = data;
    }

    public LQResponse getResponse() {
        return mResponse;
    }

    public void setResponse(LQResponse response) {
        mResponse = response;
    }

    public String getContentType() {
        return mContentType;
    }

    public void setContentType(String contentType) {
        mContentType = contentType;
    }
}
