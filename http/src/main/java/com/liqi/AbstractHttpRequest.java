package com.liqi;

import com.liqi.http.HttpHeader;
import com.liqi.http.HttpMethod;
import com.liqi.http.HttpRequest;
import com.liqi.http.HttpResponse;
import com.liqi.http.RequestBody;
import com.liqi.service.Request;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipOutputStream;

public abstract class AbstractHttpRequest<T> implements HttpRequest {

    private static final String GZIP = "gzip";

    private HttpHeader mHeader = new HttpHeader();

    private ZipOutputStream mZip;

//    private boolean executed;

    private String mUrl;
    private HttpMethod mHttpMethod;
    protected RequestBody mRequestBody;
    protected T mClient;

    public AbstractHttpRequest(Request request, T client) {
        this.mClient = client;
        this.mUrl = request.getmUrl();
        this.mHttpMethod = request.getmMethod();
        this.mRequestBody = request.getBody();
        if (request.getmHeader() != null) {
            this.mHeader = request.getmHeader();
        }
    }

    @Override
    public HttpMethod getMethod() {
        return mHttpMethod;
    }

    @Override
    public String getUri() {
//        return URI.create(mUrl);
        return mUrl;
    }

    @Override
    public void setHeaders(HttpHeader httpHeader){
        this.mHeader = httpHeader;
    }

    @Override
    public HttpHeader getHeaders() {
        return mHeader;
    }

    @Override
    public OutputStream getBody() {
        OutputStream body = getBodyOutputStream();
        if (isGzip()) {

            return getGzipOutStream(body);
        }
        return body;
    }

    private OutputStream getGzipOutStream(OutputStream body) {
        if (this.mZip == null) {
            this.mZip = new ZipOutputStream(body);
        }
        return mZip;
    }

    private boolean isGzip() {

        String contentEncoding = getHeaders().getContentEncoding();
        if (GZIP.equals(contentEncoding)) {
            return true;
        }
        return false;
    }

    @Override
    public HttpResponse execute() throws IOException {
        if (mZip != null) {
            mZip.close();
        }
        HttpResponse response = executeInternal(mHeader);
//        executed = true;
        return response;
    }

    protected abstract HttpResponse executeInternal(HttpHeader mHeader) throws IOException;

    protected abstract OutputStream getBodyOutputStream();
}
