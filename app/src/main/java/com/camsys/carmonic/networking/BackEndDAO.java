package com.camsys.carmonic.networking;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BackEndDAO {

    private OkHttpClient client = new OkHttpClient();
    private String BACKEND_URL = "http://localhost:3000";

    public Response signUp(String firstName, String lastName, String email, String password) {

        String route = "/signup";

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(firstName, firstName)
                .addFormDataPart("lastName", lastName)
                .addFormDataPart("email", email)
                .addFormDataPart("password", password)
                .build();

        Request request = new Request.Builder()
                .url(BACKEND_URL + route)
                .post(requestBody)
                .build();

        Response response = null;

        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }
}
