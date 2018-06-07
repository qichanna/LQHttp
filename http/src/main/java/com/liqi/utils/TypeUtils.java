package com.liqi.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class TypeUtils {
    public static Type getType(Class responseType){
        Type[] interfaceType = responseType.getGenericInterfaces();
        if(interfaceType == null || interfaceType.length == 0){
            throw new IllegalArgumentException("responseType is null");
        }
        ParameterizedType parameterizedTypes = (ParameterizedType) interfaceType[0];
        Type[] paramType = parameterizedTypes.getActualTypeArguments();
        if(paramType == null || paramType.length == 0){
            throw new IllegalArgumentException("responseType is null");
        }
        return paramType[0];
    }
}
