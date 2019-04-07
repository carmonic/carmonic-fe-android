package com.camsys.carmonic;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

public class LandingPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main_drawer_page);

        setContentView(R.layout.main_landing);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

     /*   TextView hdrTv = findViewById(R.id.txtVwScreen2Hdr);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceBold.ttf");
        hdrTv.setTypeface(tf);

        TextView subTitleTv = findViewById(R.id.txtVwScreen2SubTitle);
        Typeface tfSub = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf");
        subTitleTv.setTypeface(tfSub);

        TextInputLayout txtInName = findViewById(R.id.txtinputLayName);
        Typeface tfName = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf");
        subTitleTv.setTypeface(tfName);

        TextInputEditText txtEditName = findViewById(R.id.txtEditName);
        Typeface tfEditName = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf");
        txtEditName.setTypeface(tfEditName);


        TextInputLayout txtInPhone = findViewById(R.id.txtinputLayPhone);
        Typeface tfInPhone = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf");
        txtInPhone.setTypeface(tfInPhone);

        TextInputEditText txtEditPhone = findViewById(R.id.txtEditPhone);
        Typeface tfEditPhone = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf");
        txtEditPhone.setTypeface(tfEditPhone);

        TextInputLayout txtInMail = findViewById(R.id.txtinputLayEmail);
        Typeface tfMail = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf");
        txtInMail.setTypeface(tfMail);

        TextInputEditText txtEditMail = findViewById(R.id.txtEditEmail);
        txtEditMail.setTypeface(tfMail);*/


    }

    public void proceedToSignIn(View view) {
        Intent i = new Intent(getApplicationContext(), SignInActivity.class);
        startActivity(i);

    }

    public void proceedToSignUp(View view) {
        //  Intent i = new Intent(getApplicationContext(), MainActivity.class);
        //  startActivity(i);
        Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(i);
    }

    public void onclick_Register(View view) {
        Intent i = new Intent(getApplicationContext(), sign_upActivity.class);
        startActivity(i);

    }

    public void onclick_SignIn(View view) {
        Intent i = new Intent(getApplicationContext(), SignInActivity.class);
        startActivity(i);

    }
}

