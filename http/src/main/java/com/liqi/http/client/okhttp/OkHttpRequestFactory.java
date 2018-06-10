package com.liqi.http.client.okhttp;

import com.liqi.HttpRequestFactory;
import com.liqi.http.HttpRequest;
import com.liqi.service.Request;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.OkHttpClient;

public class OkHttpRequestFactory implements HttpRequestFactory {

    private OkHttpClient mClient;

    public OkHttpRequestFactory() {
        this.mClient = new OkHttpClient();
        // 添加https证书文件
//        setSocketFactory("");
    }

    public OkHttpRequestFactory(OkHttpClient client) {
        this.mClient = client;
    }

    public void setReadTimeOut(int readTimeOut) {
        this.mClient = mClient.newBuilder().
                readTimeout(readTimeOut, TimeUnit.MILLISECONDS).
                build();
    }

    public void setWriteTimeOut(int writeTimeOut) {
        this.mClient = mClient.newBuilder().
                writeTimeout(writeTimeOut, TimeUnit.MILLISECONDS).
                build();
    }

    public void setConnectionTimeOut(int connectionTimeOut) {
        this.mClient = mClient.newBuilder().
                connectTimeout(connectionTimeOut, TimeUnit.MILLISECONDS).
                build();
    }

    public void setSocketFactory(String file){
        try {
            FileInputStream inputStream = new FileInputStream(file);
            this.mClient = mClient.newBuilder()
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    })
                    .sslSocketFactory(getSocketFactory(inputStream)).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public HttpRequest createHttpRequest(Request request) throws IOException {
        return new OkHttpRequest(request,mClient);
    }

    @Override
    public SSLSocketFactory getSocketFactory(InputStream inputStream) throws Exception {
        CertificateFactory factory = CertificateFactory.getInstance("x.509");
        X509Certificate certificate = (X509Certificate) factory.generateCertificate(inputStream);

        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null,null);
        keyStore.setCertificateEntry("ca",certificate);

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);

        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null,trustManagerFactory.getTrustManagers(),new SecureRandom());
        return context.getSocketFactory();
    }
}
