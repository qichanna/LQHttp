package com.liqi;

import com.liqi.http.HttpDns;
import com.liqi.http.HttpResponse;
import com.liqi.http.client.okhttp.OkHttpClient;
import com.liqi.http.client.origin.OriginHttpClient;
import com.liqi.service.Request;
import com.liqi.utils.Utills;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class HttpClient implements Call{

    private static boolean OKHTTP_REQUEST = Utills.isExist("okhttp3.OkHttpClient", HttpRequestProvider.class.getClassLoader());

    private int mWriteTimeOut;
    private int mReaderTimeOut;
    private int mConnectTimeOut;
    public SSLSocketFactory mSSLSocketFactory;
    private HostnameVerifier mHostnameVerifier;
    private Call mRealHttpClient;
    private HttpDns mHttpDns;

    public HttpClient(Builder builder) {
        mWriteTimeOut = builder.mWriteTimeOut;
        mReaderTimeOut = builder.mReaderTimeOut;
        mConnectTimeOut = builder.mConnectTimeOut;
        mSSLSocketFactory = builder.mSSLSocketFactory;
        mHostnameVerifier = builder.mHostnameVerifier;
        mHttpDns = builder.mHttpDns;
    }

    public HttpDns getHttpDns(){
        return mHttpDns;
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
        public static HostnameVerifier DEFAULT_HOST_NAME_VERIFIER = new DefaultHostnameVerifier();
        public static SSLSocketFactory DEFAULT_SSL_SOCKET_FACTORY = getDefaultSocketFactory();
        private int mWriteTimeOut;
        private int mReaderTimeOut;
        private int mConnectTimeOut;
        public SSLSocketFactory mSSLSocketFactory;
        private HostnameVerifier mHostnameVerifier;
        private HttpDns mHttpDns;

        static class DefaultHostnameVerifier implements HostnameVerifier{

            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        }

        /**
         * 默认的证书管理
         */
        static class DefaultTrustManger implements X509TrustManager {

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }


            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }

        public static SSLSocketFactory getDefaultSocketFactory() {
            SSLContext context = null;
            try {
                context = SSLContext.getInstance("TLS");
                context.init(null, new TrustManager[]{new DefaultTrustManger()}, new SecureRandom());
            } catch (KeyManagementException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return context.getSocketFactory();
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

        public Builder setHttpDns(HttpDns dns){
            this.mHttpDns = dns;
            return this;
        }
        
        public HttpClient builder(){
            proxy();
            return new HttpClient(this);
        }

        private void proxy() {
            String host = System.getProperty("http.proxyHost");
            String port = System.getProperty("http.proxyPort");
            if (host != null && port != null) {
                mHttpDns = null;
            }
        }
    }
}
