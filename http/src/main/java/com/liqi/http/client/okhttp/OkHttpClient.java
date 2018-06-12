package com.liqi.http.client.okhttp;

import com.liqi.Call;
import com.liqi.HttpClient;
import com.liqi.http.HttpResponse;
import com.liqi.service.Request;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Dns;

public class OkHttpClient implements Call{

    private okhttp3.OkHttpClient mClient;

    public OkHttpClient(final HttpClient client){
        okhttp3.OkHttpClient.Builder builder = new okhttp3.OkHttpClient.Builder();
        builder.readTimeout(client.getmReaderTimeOut(), TimeUnit.MILLISECONDS);
        builder.writeTimeout(client.getmWriteTimeOut(), TimeUnit.MILLISECONDS);
        builder.connectTimeout(client.getmConnectTimeOut(), TimeUnit.MILLISECONDS);
        if (client.getSSLSocketFactory() != null) {
            builder.sslSocketFactory(client.getSSLSocketFactory());
        }
        if (client.getHostnameVerifier() != null) {
            builder.hostnameVerifier(client.getHostnameVerifier());
        }

        if (client.getHttpDns() != null) {
            builder.dns(new Dns() {
                @Override
                public List<InetAddress> lookup(String hostname) throws UnknownHostException {
                    List<InetAddress> host = client.getHttpDns().lookup(hostname);
                    if (host != null) {
                        return host;
                    }
                    return Dns.SYSTEM.lookup(hostname);
                }
            });
        }
        mClient = builder.build();
    }

    @Override
    public HttpResponse execute(Request request) throws IOException {
        return new OkHttpRequest(request, mClient).execute();
    }
}
