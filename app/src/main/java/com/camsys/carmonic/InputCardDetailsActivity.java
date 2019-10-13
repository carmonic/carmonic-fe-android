package com.camsys.carmonic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import co.paystack.android.PaystackSdk;
import co.paystack.android.model.Card;

public class InputCardDetailsActivity extends AppCompatActivity {

    TextView txtVwScreen2SubTitle = null;
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

        txtVwScreen2SubTitle = findViewById(R.id.txtVwScreen2SubTitle);

        PaystackSdk.initialize(getApplicationContext());

    }

    public void onclick_regPage1(View view) {
        String firstname = getIntent().getStringExtra("firstname");
        String lastname = getIntent().getStringExtra("lastname");
        String email = getIntent().getStringExtra("email");
        String phoneNumber = getIntent().getStringExtra("phonenumber");
        String cardNumber = txtinputCardNumber.getEditText().getText().toString();
        String cardName = txtinputCardName.getEditText().getText().toString();
        String cardExpiryMonth = txtinputCardExpiryMonth.getEditText().getText().toString();
        String cardExpiryYear = txtinputCardExpiryYear.getEditText().getText().toString();
        String cardCVVNumber = txtinputCardCVVNumber.getEditText().getText().toString();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("cardNumberLastFour", cardNumber.substring(cardNumber.length() - 5, cardNumber.length() - 1));
        editor.apply();

        Card card = new Card(cardNumber, Integer.valueOf(cardExpiryMonth), Integer.valueOf(cardExpiryYear), cardCVVNumber);

        Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
        i.putExtra("firstname", firstname);
        i.putExtra("lastname", lastname);
        i.putExtra("email", email);
        i.putExtra("phonenumber", phoneNumber);
        i.putExtra("cardNumber", cardNumber);
        i.putExtra("cardName", cardName);
        i.putExtra("cardExpiryMonth", cardExpiryMonth);
        i.putExtra("cardExpiryYear", cardExpiryYear);
        i.putExtra("cardCVVNumber", cardCVVNumber);
        if (card.isValid()) {
            startActivity(i);
        } else {
            InputCardDetailsActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    txtVwScreen2SubTitle.setText("Invalid Card Details");
                    txtVwScreen2SubTitle.setTextColor(Color.RED);
                }
            });
        }
    }
}
