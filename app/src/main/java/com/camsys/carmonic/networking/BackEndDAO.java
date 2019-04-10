package com.camsys.carmonic.networking;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class BackEndDAO {

    private static OkHttpClient client = new OkHttpClient();
    private static final String BACKEND_URL = "http://192.168.0.10:3000";

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
}
