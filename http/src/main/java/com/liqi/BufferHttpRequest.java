package com.liqi;

import com.liqi.http.HttpHeader;
import com.liqi.http.HttpResponse;
import com.liqi.service.Request;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public abstract class BufferHttpRequest<T> extends AbstractHttpRequest<T> {

    private ByteArrayOutputStream mByteArray = new ByteArrayOutputStream();

    public BufferHttpRequest(Request request, T client) {
        super(request, client);
    }

    protected OutputStream getBodyOutputStream() {
        return mByteArray;
    }

    protected HttpResponse executeInternal(HttpHeader header) throws IOException {
        byte[] data = mByteArray.toByteArray();
        return executeInternal(header, data);
    }

    protected abstract HttpResponse executeInternal(HttpHeader header, byte[] data) throws IOException;
}
