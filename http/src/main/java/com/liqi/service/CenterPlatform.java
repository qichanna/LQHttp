package com.liqi.service;

import com.liqi.HttpRequestProvider;
import com.liqi.http.HttpRequest;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * @deprecated
 */
public class CenterPlatform {

    private static final int MAX_REQUEST_SIZE = 60;

    private static final ThreadPoolExecutor sThreadPool = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new ThreadFactory() {

        private AtomicInteger index = new AtomicInteger();

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("http thread name is " + index.getAndIncrement());
            return thread;
        }
    });


    private Deque<LQRequest> mRunning = new ArrayDeque<>();

    private Deque<LQRequest> mCache = new ArrayDeque<>();

    private HttpRequestProvider mRequestProvider;

    public CenterPlatform() {
        mRequestProvider = new HttpRequestProvider();
    }

    public void add(LQRequest request) {

        if (mRunning.size() > MAX_REQUEST_SIZE) {
            mCache.add(request);
        } else {
            doHttpRequest(request);
            mRunning.add(request);
        }

    }


    public void doHttpRequest(LQRequest request) {
        HttpRequest httpRequest = null;
        /*try {
            httpRequest = mRequestProvider.getHttpRequest(URI.create(request.getUrl()), request.getMethod());
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        sThreadPool.execute(new HttpRunnable(httpRequest, request, this));
    }


    public void finish(LQRequest request) {
        mRunning.remove(request);
        if (mRunning.size() > MAX_REQUEST_SIZE) {
            return;
        }

        if (mCache.size() == 0) {
            return;
        }

        Iterator<LQRequest> iterator = mCache.iterator();

        while (iterator.hasNext()) {
            LQRequest next = iterator.next();
            mRunning.add(next);
            iterator.remove();
            doHttpRequest(next);
        }

    }
}
