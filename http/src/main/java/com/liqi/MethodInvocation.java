package com.liqi;


import com.liqi.service.Callback;
import com.liqi.service.HttpCall;
import com.liqi.service.Request;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;


public class MethodInvocation implements InvocationHandler {

    private LQClient mLqClient;
    private HashMap<Method, MethodAnnotationProcess> mMethodCache;

    public MethodInvocation(LQClient lqClient, HashMap<Method, MethodAnnotationProcess> methodCache) {
        this.mLqClient = lqClient;
        this.mMethodCache = methodCache;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (args == null || args.length == 0) {
            throw new IllegalArgumentException("args 不能空");
        }
        if (!(args[args.length - 1] instanceof Callback)) {
            throw new IllegalArgumentException("最后一个参数必须是Callback类型");
        }
        execute(args, mMethodCache.get(method), (Callback) args[args.length - 1]);
        return null;
    }

    private void execute(Object[] args, MethodAnnotationProcess process, Callback callback) throws IOException, ExecutionException, InterruptedException {
        Request request = process.builderRequest(args);
        HttpCall httpCall = new HttpCall(request, mLqClient, callback);
        httpCall.invoke().get();
    }
}
