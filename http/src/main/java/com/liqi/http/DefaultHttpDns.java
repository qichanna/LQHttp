package com.liqi.http;

import com.liqi.HttpClient;
import com.liqi.service.Request;
import com.liqi.utils.Utills;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.liqi.LQClient.Builder.CONNECTION_TIME_OUT;
import static com.liqi.LQClient.Builder.READER_TIME_OUT;
import static com.liqi.LQClient.Builder.WRITE_TIME_OUT;

/**
 * 默认的HttpDns实现
 */

public class DefaultHttpDns implements HttpDns {
    private Map<String, List<InetAddress>> mCache = new HashMap<>();
    private ExecutorService mExecutor = Executors.newCachedThreadPool();

    @Override
    public List<InetAddress> lookup(final String hostname) throws UnknownHostException {
        if (mCache.get(hostname) != null) {
            System.out.println(hostname + ":" + mCache.get(hostname));
            return mCache.get(hostname);
        }
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                HttpClient httpClient = new HttpClient.Builder().
                        setConnectTimeOut(CONNECTION_TIME_OUT).
                        setReaderTimeOut(READER_TIME_OUT).
                        setWriteTimeout(WRITE_TIME_OUT).
                        builder();

                Request request = new Request.Builder().httpMethod(HttpMethod.GET).url("http://119.29.29.29/d?dn=" + hostname).build();
                try {
                    HttpResponse response = httpClient.execute(request);
                    String responseData = Utills.response(response.getBody());
                    System.out.println(responseData);
                    String[] host = null;
                    if (responseData != null) {
                        host = responseData.split(";");
                    }
                    if (host != null) {
                        ArrayList<InetAddress> address = new ArrayList<>();
                        for (String ip : host) {
                            address.add(InetAddress.getByName(ip));
                        }
                        if (mCache.get(hostname) == null) {
                            mCache.put(hostname, address);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return null;
    }
}
