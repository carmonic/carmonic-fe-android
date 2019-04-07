package com.camsys.carmonic;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);


        TextView hdrTv = findViewById(R.id.txtVwScreen2Hdr);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceBold.ttf");
        hdrTv.setTypeface(tf);

        TextView subTitleTv = findViewById(R.id.txtVwScreen2SubTitle);
        Typeface tfSub = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf");
        subTitleTv.setTypeface(tfSub);

        TextInputLayout txtInputLayPwd = findViewById(R.id.txtinputLayPwd);
        Typeface tfPwd = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf");
        txtInputLayPwd.setTypeface(tfPwd);

        TextInputEditText txtEditPwd = findViewById(R.id.txtEditPwd);
        Typeface tfEditPwd = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf");
        txtEditPwd.setTypeface(tfEditPwd);


        TextInputLayout txtInputLayPwd2 = findViewById(R.id.txtinputLayPwd2);
        Typeface tfPwd2 = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf");
        txtInputLayPwd2.setTypeface(tfPwd2);

        TextInputEditText txtEditPwd2 = findViewById(R.id.txtEditPwd2);
        Typeface tfEditPwd2 = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf");
        txtEditPwd2.setTypeface(tfEditPwd2);


    }

    public void onclick_back2(View view) {
        Intent i = new Intent(getApplicationContext(), sign_upActivity.class);
        startActivity(i);
    }

    public void onclick_regPage1(View view) {
        Intent i = new Intent(getApplicationContext(), home_screen.class);
        startActivity(i);
    }
}
