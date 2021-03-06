package com.camsys.carmonic.networking;

import com.camsys.carmonic.constants.Constants;

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


    public static OkHttpClient getClient() {
        return client;
    }

    public static String getBackendURL() {
        return Constants.EndPoint.Base_URL_remote;
    }

    public static void signUp(String firstName,
                              String lastName,
                              String email,
                              String password,
                              String phoneNumber,
                              String paymentReference,
                              Callback callback) {
        String route = "/signup";

        RequestBody requestBody = new FormBody.Builder()
                .add("firstname", firstName)
                .add("lastname", lastName)
                .add("email", email)
                .add("password", password)
                .add("phoneNumber", phoneNumber)
                .add("paymentReference", paymentReference)
                .build();

        Request request = new Request.Builder()
                .url(getBackendURL() + route)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(callback);
    }


    public static void getHistoryWithPost(int customerId, String token, Callback callback) {
        String route = "/history";

        RequestBody requestBody = new FormBody.Builder()
                .add("customerId", "86")
                .build();

        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer " + token)
                .url(getBackendURL() + route)
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
                .url(getBackendURL() + route)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(callback);

    }

    public static void getHistory(int userId, String token, Callback callback) {
        String route = "/history";
        HttpUrl.Builder httpBuider = HttpUrl.parse(getBackendURL() + route).newBuilder();
        httpBuider.addQueryParameter("customerId", "86");  //userId + "");
        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer " + token)
                .url(httpBuider.build())
                .build();

        client.newCall(request).enqueue(callback);
    }


    public static void getMechanics(double longitude, double latitude,String token, Callback callback) {
        String route = "/getMechanics";
        HttpUrl.Builder httpBuider = HttpUrl.parse(getBackendURL() + route).newBuilder();
        httpBuider.addQueryParameter("longitude", Double.toString(longitude));
        httpBuider.addQueryParameter("latitude", Double.toString(latitude));
        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer " + token)
                .url(httpBuider.build())
                .build();

        client.newCall(request).enqueue(callback);
    }

    public static void initiateJob(String customerId, double longitude, double latitude, String token, String fcmToken, Callback callback) {
        String route = "/initiateJob";
        HttpUrl.Builder httpBuider = HttpUrl.parse(getBackendURL() + route).newBuilder();
        httpBuider.addQueryParameter("longitude", Double.toString(longitude));
        httpBuider.addQueryParameter("latitude", Double.toString(latitude));
        httpBuider.addQueryParameter("customerId", customerId);
        httpBuider.addQueryParameter("fcmtoken", fcmToken);
        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer " + token)
                .url(httpBuider.build())
                .build();

        client.newCall(request).enqueue(callback);
    }


    public static void setCustomerStatusChange(int customerId, double longitude, double latitude, String fcmToken, String accessToken, Callback callback) {
        String route = "/custStatusChange";
        HttpUrl.Builder httpBuider = HttpUrl.parse(getBackendURL() + route).newBuilder();
        httpBuider.addQueryParameter("customerId", String.valueOf(customerId));
        httpBuider.addQueryParameter("longitude", Double.toString(longitude));
        httpBuider.addQueryParameter("latitude", Double.toString(latitude));
        httpBuider.addQueryParameter("fcmToken", fcmToken);
        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer " + accessToken)
                .url(httpBuider.build())
                .build();

        System.out.println("fcmToken::: " + fcmToken);
        System.out.println("customerId::: " + customerId);
        System.out.println("longitude::: " + longitude);
        System.out.println("latitude::: " + latitude);

        client.newCall(request).enqueue(callback);
    }


    public static void charge(String amount, String email, String token, Callback callback) {
        String route = "/charge";

        HttpUrl.Builder httpBuider = HttpUrl.parse(getBackendURL() + route).newBuilder();
        httpBuider.addQueryParameter("amount", amount);
        httpBuider.addQueryParameter("email", email);

        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer " + token)
                .url(httpBuider.build())
                .build();

        client.newCall(request).enqueue(callback);
    }

    public static void postFeedback(int starRating, String compliment, String feedback, String customerId, String mechanicId, String token, Callback callback) {
        String route = "/mechanicFeedback";

        feedback = feedback != null ? feedback : "";
        compliment = compliment != null ? compliment : "";

        RequestBody requestBody = new FormBody.Builder()
                .add("customerId", customerId)
                .add("mechanicId", mechanicId)
                .add("feedback", feedback)
                .add("compliment", compliment)
                .add("starRating", Integer.toString(starRating))
                .build();

        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer " + token)
                .url(getBackendURL() + route)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(callback);
    }

    public static void getEstimatedDistance(double fromLongitude, double fromLatitude, double toLongitude, double toLatitude, String token, Callback callback) {
        String route = "/getEstimatedDistance";

        HttpUrl.Builder httpBuider = HttpUrl.parse(getBackendURL() + route).newBuilder();
        httpBuider.addQueryParameter("fromLongitude", Double.toString(fromLongitude));
        httpBuider.addQueryParameter("fromLatitude", Double.toString(fromLatitude));
        httpBuider.addQueryParameter("toLongitude", Double.toString(toLongitude));
        httpBuider.addQueryParameter("toLatitude", Double.toString(toLatitude));

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
