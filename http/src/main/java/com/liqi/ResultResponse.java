package com.liqi;

import com.google.gson.annotations.SerializedName;

/**
 * @author nate
 */

public class ResultResponse<T> {

    @SerializedName("error_code")
    private int errorCode;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "ResultResponse{" +
                "errorCode='" + errorCode + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", data=" + data +
                '}';
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @SerializedName("error_msg")
    private String errorMsg;

    @SerializedName("data")
    private T data;

}
