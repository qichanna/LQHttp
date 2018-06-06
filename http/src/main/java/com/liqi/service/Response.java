package com.liqi.service;

import com.liqi.http.HttpResponse;

/**
 * 上层业务的response
 */
public class Response<T> {

    private T mBody;
    private HttpResponse mResponse;

    public Response(T body, HttpResponse response){
        this.mResponse = response;
        this.mBody = body;
    }

    public boolean isSuccess(){
        return mResponse.getStatus().isSuccess();
    }

    public T body(){
        return mBody;
    }

}
