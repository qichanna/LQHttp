package com.liqi.service;

/**
 * @deprecated
 */
public abstract class LQResponse<T> {

    public abstract void success(LQRequest request, T data);

    public abstract void fail(int errorCode, String errorMsg);

}
