package com.liqi;

import com.liqi.http.HttpResponse;
import com.liqi.http.client.okhttp.OkHttpClient;
import com.liqi.http.client.origin.OriginHttpClient;
import com.liqi.service.Request;
import com.liqi.utils.Utills;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class HttpClient implements Call{

    private static boolean OKHTTP_REQUEST = Utills.isExist("okhttp3.OkHttpClient", HttpRequestProvider.class.getClassLoader());

    private int mWriteTimeOut;
    private int mReaderTimeOut;
    private int mConnectTimeOut;
    public SSLSocketFactory mSSLSocketFactory;
    private HostnameVerifier mHostnameVerifier;
    private Call mRealHttpClient;

    public HttpClient(Builder builder) {
        mWriteTimeOut = builder.mWriteTimeOut;
        mReaderTimeOut = builder.mReaderTimeOut;
        mConnectTimeOut = builder.mConnectTimeOut;
        mSSLSocketFactory = builder.mSSLSocketFactory;
        mHostnameVerifier = builder.mHostnameVerifier;
    }

    public int getmReaderTimeOut(){
        return mReaderTimeOut;
    }

    public int getmWriteTimeOut(){
        return mWriteTimeOut;
    }

    public int getmConnectTimeOut(){
        return mConnectTimeOut;
    }

    public SSLSocketFactory getSSLSocketFactory(){
        return mSSLSocketFactory;
    }

    public HostnameVerifier getHostnameVerifier(){
        return mHostnameVerifier;
    }

    @Override
    public HttpResponse execute(Request request) throws IOException {
//        OKHTTP_REQUEST = false;
        if (OKHTTP_REQUEST) {
            mRealHttpClient = new OkHttpClient(this);
        } else {
            mRealHttpClient = new OriginHttpClient(this);
        }
        return mRealHttpClient.execute(request);
    }

    public static class Builder{
        private int mWriteTimeOut;
        private int mReaderTimeOut;
        private int mConnectTimeOut;
        public SSLSocketFactory mSSLSocketFactory;
        private HostnameVerifier mHostnameVerifier;
        public static HostnameVerifier DEFAULT_HOSTNAMEVERIFER = new DefaultHostnameVerifier();

        static class DefaultHostnameVerifier implements HostnameVerifier{

            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        }

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

        public Builder setWriteTimeout(int timeOut){
            this.mWriteTimeOut = timeOut;
            return this;
        }

        public Builder setReaderTimeOut(int timeOut){
            this.mReaderTimeOut = timeOut;
            return this;
        }

        public Builder setConnectTimeOut(int timeOut){
            this.mConnectTimeOut = timeOut;
            return this;
        }

        public Builder setSSLSocketFactory(SSLSocketFactory factory){
            this.mSSLSocketFactory = factory;
            return this;
        }

        public Builder setHostnameVerifier(HostnameVerifier verifier){
            this.mHostnameVerifier = verifier;
            return this;
        }
        
        public HttpClient builder(){
            return new HttpClient(this);
        }
    }
}
