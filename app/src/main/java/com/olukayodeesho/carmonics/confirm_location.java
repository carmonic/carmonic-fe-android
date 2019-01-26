package com.olukayodeesho.carmonics;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

public class confirm_location extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_location);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        EditText confirmLocation = findViewById(R.id.editTextConfirmLocation);
        confirmLocation.setText(getIntent().getStringExtra("locationAddress"));

    }

    public void onclick_locating_mechanic(View view) {

// go to tentatively billing
        Intent i = new Intent(getApplicationContext(), locating_mechanic.class);
        startActivity(i);
    }

    public void onclick_cancel_location_confirmation(View view) {

// go to tentatively billing
        Intent i = new Intent(getApplicationContext(), home_screen.class);
        startActivity(i);
    }
}
