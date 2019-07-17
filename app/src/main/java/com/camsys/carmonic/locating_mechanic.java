package com.camsys.carmonic;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

public class locating_mechanic extends AppCompatActivity {

    private static int TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locating_mechanic);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(locating_mechanic.this, BillingActivity.class));
                finish();
            }
        }, TIME_OUT);
    }


    public void onclick_goto_billing(View view) {
        Intent i = new Intent(getApplicationContext(), BillingActivity.class);
        startActivity(i);
    }
}
