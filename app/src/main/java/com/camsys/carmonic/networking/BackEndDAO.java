package com.camsys.carmonic.networking;

import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class BackEndDAO {

    private static OkHttpClient client = getUnsafeOkHttpClient();
    private static final String BACKEND_URL = "https://192.168.0.10:8443";

    public static OkHttpClient getClient() {
        return client;
    }

    public static String getBackendURL() {
        return BACKEND_URL;
    }

    public static void signUp(String firstName, String lastName, String email, String password, Callback callback) {
        String route = "/signup";

        RequestBody requestBody = new FormBody.Builder()
                .add("firstname", firstName)
                .add("lastname", lastName)
                .add("email", email)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url(BACKEND_URL + route)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(callback);
    }

    public static void signIn(String email, String password, Callback callback) {
        String route = "/login";

        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url(BACKEND_URL + route)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(callback);

    }

    public static void getMechanics(double longitude, double latitude, String token, Callback callback) {
        String route = "/getMechanics";

        HttpUrl.Builder httpBuider = HttpUrl.parse(BACKEND_URL + route).newBuilder();
        httpBuider.addQueryParameter("longitude", Double.toString(longitude));
        httpBuider.addQueryParameter("latitude", Double.toString(latitude));

        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer " + token)
                .url(httpBuider.build())
                .build();

        client.newCall(request).enqueue(callback);
    }

    //ToDo: Configure proper certificate settings when we buy one
    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
