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
/*
 * This Sign Up activity is for the first page with firstname, lastname and email
 */
public class sign_upActivity extends AppCompatActivity {

    TextInputLayout txtInName = null;
    TextInputLayout textInputLayoutLastName = null;
    TextInputLayout txtInPhone = null;
    TextInputLayout txtInMail = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        TextView hdrTv = findViewById(R.id.txtVwScreen2Hdr);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceBold.ttf");
        hdrTv.setTypeface(tf);

        TextView subTitleTv = findViewById(R.id.txtVwScreen2SubTitle);
        Typeface tfSub = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf");
        subTitleTv.setTypeface(tfSub);

        txtInName = findViewById(R.id.txtinputLayName);
        Typeface tfName = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf");
        txtInName.setTypeface(tfName);

        TextInputEditText txtEditName = findViewById(R.id.txtEditName);
        Typeface tfEditName = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf");
        txtEditName.setTypeface(tfEditName);

        textInputLayoutLastName = findViewById(R.id.txtinputLayLastName);
        Typeface tfLastName = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf");
        textInputLayoutLastName.setTypeface(tfLastName);

        TextInputEditText txtEditLastName = findViewById(R.id.txtEditLastName);
        Typeface tfEditLastName = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf");
        txtEditLastName.setTypeface(tfEditLastName);

        txtInPhone = findViewById(R.id.txtinputLayPhone);
        Typeface tfInPhone = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf");
        txtInPhone.setTypeface(tfInPhone);

        TextInputEditText txtEditPhone = findViewById(R.id.txtEditPhone);
        Typeface tfEditPhone = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf");
        txtEditPhone.setTypeface(tfEditPhone);

        txtInMail = findViewById(R.id.txtinputLayEmail);
        Typeface tfMail = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf");
        txtInMail.setTypeface(tfMail);

        TextInputEditText txtEditMail = findViewById(R.id.txtEditEmail);
        txtEditMail.setTypeface(tfMail);


        AppCompatImageButton btn =  (AppCompatImageButton)findViewById(R.id.appCompatImageButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

//    public void onclick_back(View view) {
//        Intent i = new Intent(getApplicationContext(), MainActivity.class);
//        startActivity(i);
//    }




    public void onclick_regPage1(View view) {
        String firstname = txtInName.getEditText().getText().toString();
        String lastname = textInputLayoutLastName.getEditText().getText().toString();
        String email = txtInMail.getEditText().getText().toString();
        String phoneNumber = txtInPhone.getEditText().getText().toString();

        Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
        i.putExtra("firstname", firstname);
        i.putExtra("lastname", lastname);
        i.putExtra("email", email);
        i.putExtra("phonenumber", phoneNumber);
        startActivity(i);
    }
}

