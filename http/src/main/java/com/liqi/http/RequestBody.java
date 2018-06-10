package com.liqi.http;

public abstract class RequestBody<T> {

    /**
     * 请求实体的MIME类型
     */
    private String mMediaType;
    /**
     * 请求的实体内容
     */
    private T mData;

    public RequestBody(T mData, String mMediaType) {
        this.mData = mData;
        this.mMediaType = mMediaType;
    }


    public T getData() {
        return mData;
    }

    public void setData(T data) {
        mData = data;
    }

    public String getMediaType() {
        return mMediaType;
    }

    /**
     * 请求实体的长度
     *
     * @return 长度
     */
    public abstract long getContentLength();

    public static class FormBody extends RequestBody<String> {

        public FormBody(String mData, String mMediaType) {
            super(mData, mMediaType);
        }

        @Override
        public long getContentLength() {
            return getData().length();
        }
    }

}
