package com.camsys.carmonic;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class InputCardDetailsActivity extends AppCompatActivity {

    TextInputLayout txtinputCardNumber = null;
    TextInputLayout txtinputCardName = null;
    TextInputLayout txtinputCardExpiryMonth = null;
    TextInputLayout txtinputCardExpiryYear = null;
    TextInputLayout txtinputCardCVVNumber = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_card_details);

        txtinputCardNumber = findViewById(R.id.txtinputCardNumber);
        txtinputCardNumber.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf"));

        txtinputCardName = findViewById(R.id.txtinputCardName);
        txtinputCardName.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf"));

        txtinputCardExpiryMonth = findViewById(R.id.txtinputCardExpiryMonth);
        txtinputCardExpiryMonth.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf"));

        txtinputCardExpiryYear = findViewById(R.id.txtinputCardExpiryYear);
        txtinputCardExpiryYear.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf"));

        txtinputCardCVVNumber = findViewById(R.id.txtinputCardCVVNumber);
        txtinputCardCVVNumber.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf"));

    }

    public void onclick_regPage1(View view) {
        String firstname = getIntent().getStringExtra("firstname");
        String lastname = getIntent().getStringExtra("lastname");
        String email = getIntent().getStringExtra("email");
        String phoneNumber = getIntent().getStringExtra("phonenumber");
        String cardNumber = getIntent().getStringExtra("txtinputCardNumber");
        String cardName = getIntent().getStringExtra("txtinputCardName");
        String cardExpiryMonth = getIntent().getStringExtra("txtinputCardExpiryMonth");
        String cardCVVNumber = getIntent().getStringExtra("txtinputCardCVVNumber");

        Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
        i.putExtra("firstname", firstname);
        i.putExtra("lastname", lastname);
        i.putExtra("email", email);
        i.putExtra("phonenumber", phoneNumber);
        i.putExtra("cardNumber", cardNumber);
        i.putExtra("cardName", cardName);
        i.putExtra("cardExpiryMonth", cardExpiryMonth);
        i.putExtra("cardCVVNumber", cardCVVNumber);
        startActivity(i);
    }
}
