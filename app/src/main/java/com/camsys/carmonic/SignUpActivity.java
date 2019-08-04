package com.camsys.carmonic;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
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

public class SignUpActivity extends AppCompatActivity {

    private TextInputLayout txtInputLayPwd = null;
    private TextInputLayout txtInputLayPwd2 = null;
    private TextView subTitleTv = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        TextView hdrTv = findViewById(R.id.txtVwScreen2Hdr);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceBold.ttf");
        hdrTv.setTypeface(tf);

        subTitleTv = findViewById(R.id.txtVwScreen2SubTitle);
        Typeface tfSub = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf");
        subTitleTv.setTypeface(tfSub);

        txtInputLayPwd = findViewById(R.id.txtinputLayPwd);
        Typeface tfPwd = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf");
        txtInputLayPwd.setTypeface(tfPwd);

        TextInputEditText txtEditPwd = findViewById(R.id.txtEditPwd);
        Typeface tfEditPwd = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf");
        txtEditPwd.setTypeface(tfEditPwd);


        txtInputLayPwd2 = findViewById(R.id.txtinputLayPwd2);
        Typeface tfPwd2 = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf");
        txtInputLayPwd2.setTypeface(tfPwd2);

        TextInputEditText txtEditPwd2 = findViewById(R.id.txtEditPwd2);
        Typeface tfEditPwd2 = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf");
        txtEditPwd2.setTypeface(tfEditPwd2);

        AppCompatImageButton btn =  (AppCompatImageButton)findViewById(R.id.appCompatImageButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void onclick_back2(View view) {
//        Intent i = new Intent(getApplicationContext(), sign_upActivity.class);
//        startActivity(i);
        finish();
    }

    public void onclick_regPage1(View view) {
        String firstname = getIntent().getStringExtra("firstname");
        String lastname = getIntent().getStringExtra("lastname");
        String email = getIntent().getStringExtra("email");
        String password = txtInputLayPwd.getEditText().getText().toString();
        String confirmPassword = txtInputLayPwd2.getEditText().getText().toString();

        Intent i = new Intent(getApplicationContext(), MapsActivity.class);
        i.putExtra("firstname", firstname);
        i.putExtra("lastname", lastname);

        if (password.equals(confirmPassword)) {
            BackEndDAO.signUp(firstname, lastname, email, password, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
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

                        SignUpActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                subTitleTv.setText(loginResponse.getAuthInfo().getMessage());
                                if (user.getToken() != null) {
                                    subTitleTv.setTextColor(Color.GREEN);
                                    i.putExtra("firstname", user.getFirstname());
                                    i.putExtra("lastname", user.getLastname());
                                    startActivity(i);
                                } else {
                                    subTitleTv.setTextColor(Color.RED);
                                    txtInputLayPwd.getEditText().setText("");
                                    txtInputLayPwd2.getEditText().setText("");
                                }
                            }
                        });
                    }
                }
            });
        }
    }
}
