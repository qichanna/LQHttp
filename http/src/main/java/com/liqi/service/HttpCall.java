package com.liqi.service;

import com.liqi.HttpRequestProvider;
import com.liqi.http.HttpRequest;
import com.liqi.http.HttpResponse;
import com.liqi.service.convert.Convert;
import com.liqi.utils.TypeUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class HttpCall implements HttpEngine{
    private Request mRequest;
    private ExecutorService mExecutorService;
    private List<Convert> mConvert;
    private Callback mCallback;
    private HttpRequestProvider mProvider;

    public HttpCall(Request request,ExecutorService executorService,List<Convert> converts, HttpRequestProvider provider, Callback callback){
        this.mRequest = request;
        this.mExecutorService = executorService;
        this.mConvert = converts;
        this.mProvider = provider;
        this.mCallback = callback;
    }

    @Override
    public HttpResponse execute() throws IOException {
        HttpRequest request;
        request = mProvider.getHttpRequest(URI.create(mRequest.getmUrl()),mRequest.getmMethod());
        request.getBody().write(mRequest.getmData());
        return request.execute();
    }

    public <T> T invoke(Class responseType){
        Type type = TypeUtils.getType(responseType);
        HttpResponse httpResponse;
        T result = null;
        try {
            httpResponse = execute();
            if(!httpResponse.getStatus().isSuccess()){
                onFailure(httpResponse.getStatusMsg());
                return null;
            }
            result = convertResponse(httpResponse,type);
            if(result == null){
                onFailure("result is null");
                return null;
            }
            T response = parseResponse(result,httpResponse);
            onSuccess((Response) response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    private <T> T parseResponse(T result,HttpResponse httpResponse){
        return (T) new Response(result,httpResponse);
    }

    public <T> T convertResponse(HttpResponse response,Type type) throws IOException {
        for (Convert convert : mConvert) {
            return (T) convert.parse(response,type);
        }
        return null;
    }

    public <T> Future<T> invoke(){
        return mExecutorService.submit(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return invoke(mCallback.getClass());
            }
        });
    }

    private void onSuccess(Response response){
        mCallback.onSuccess(response);
    }

    private void onFailure(String errorMsg){
        mCallback.onFailure(errorMsg);
    }
}
