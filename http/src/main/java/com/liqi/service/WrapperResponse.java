package com.liqi.service;


import com.liqi.service.convert.Convert;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @deprecated
 */
public class WrapperResponse extends LQResponse<String> {

    private LQResponse mLQResponse;

    private List<Convert> mConvert;

    public WrapperResponse(LQResponse lqResponse, List<Convert> converts) {
        this.mLQResponse = lqResponse;
        this.mConvert = converts;
    }

    @Override
    public void success(LQRequest request, String data) {

        for (Convert convert : mConvert) {
            if (convert.isCanParse(request.getContentType())) {

                try {
                    Object object = convert.parse(data, getType());
                    mLQResponse.success(request, object);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }

        }


    }


    public Type getType() {
        Type type = mLQResponse.getClass().getGenericSuperclass();
        Type[] paramType = ((ParameterizedType) type).getActualTypeArguments();
        return paramType[0];
    }

    @Override
    public void fail(int errorCode, String errorMsg) {

    }
}
