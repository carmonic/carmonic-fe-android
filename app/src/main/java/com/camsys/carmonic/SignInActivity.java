package com.camsys.carmonic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.camsys.carmonic.networking.BackEndDAO;
import com.camsys.carmonic.networking.LoginResponse;
import com.camsys.carmonic.principals.User;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SignInActivity extends AppCompatActivity {

    private static final String SIGN_IN_INSTRUCTION_MESSAGE = "Sign into your account";

    TextInputLayout txtInputLayEmail = null;
    TextInputLayout txtInputLayPassword = null;
    TextView subTitleTv = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        TextView hdrTv = findViewById(R.id.txtVwScreen2Hdr);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceBold.ttf");
        hdrTv.setTypeface(tf);

        subTitleTv = findViewById(R.id.txtVwScreen2SubTitle);
        Typeface tfSub = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf");
        subTitleTv.setTypeface(tfSub);
        subTitleTv.setText(SIGN_IN_INSTRUCTION_MESSAGE);

        txtInputLayEmail = findViewById(R.id.txtinputLayEmail);
        Typeface tfPwd = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf");
        txtInputLayEmail.setTypeface(tfPwd);

        TextInputEditText txtEditPwd = findViewById(R.id.txtEditPwd);
        Typeface tfEditPwd = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf");
        txtEditPwd.setTypeface(tfEditPwd);

        txtInputLayPassword = findViewById(R.id.txtinputLayPwd2);
        Typeface tfPwd2 = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf");
        txtInputLayPassword.setTypeface(tfPwd2);

        TextInputEditText txtEditPwd2 = findViewById(R.id.txtEditPwd2);
        Typeface tfEditPwd2 = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf");
        txtEditPwd2.setTypeface(tfEditPwd2);
    }

    public void onclick_sign_in_back(View view) {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }

    public void onclick_back2(View view) {
        Intent i = new Intent(getApplicationContext(), sign_upActivity.class);
        startActivity(i);
    }

    public void onclick_regPage1(View view) {
        String email = txtInputLayEmail.getEditText().getText().toString();
        String password = txtInputLayPassword.getEditText().getText().toString();

        Intent i = new Intent(getApplicationContext(), MapsActivityWithLocationConfirmed.class);

        BackEndDAO.signIn(email, password, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // SHow a network error popup
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    String responseBodyString = response.body().string();
                    Gson gson = new Gson();
                    LoginResponse loginResponse = gson.fromJson(responseBodyString, LoginResponse.class);
                    User user = loginResponse.getUser();

                    SignInActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            subTitleTv.setText(loginResponse.getAuthInfo().getMessage());
                            if (user.getToken() != null) {
                                subTitleTv.setTextColor(Color.GREEN);
                                i.putExtra("firstname", user.getFirstname());
                                i.putExtra("lastname", user.getLastname());

                                // Storing this in SharedPreferences because we need the authToken later on
                                // ToDo: Long term, we need a more secure way to store this, or to use another mechanism for authentication
                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("Authorisation", user.getToken());
                                editor.putString("User", gson.toJson(user));
                                editor.apply();

                                startActivity(i);
                            } else {
                                subTitleTv.setTextColor(Color.RED);
                                txtInputLayEmail.getEditText().setText("");
                                txtInputLayPassword.getEditText().setText("");
                            }
                        }
                    });
                }
            }
        });
    }
}
