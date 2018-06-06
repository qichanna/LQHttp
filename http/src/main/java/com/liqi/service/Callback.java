package com.liqi.service;

/**
 * 请求接口回调
 * @param <T>
 */
public interface Callback<T> {

    /**
     * 请求成功
     * @param response
     */
    void onSuccess(Response<T> response);

    /**
     * 请求失败
     * @param errorMsg
     */
    void onFailure(String errorMsg);
}
