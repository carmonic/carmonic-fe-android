package com.olukayodeesho.carmonics;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class home_screen extends AppCompatActivity {

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);


        dl = findViewById(R.id.drawerMain);






    }

    public void onclick_mechanic_request(View view) {

// go to tentatively billing
        TextView locationAddress = findViewById(R.id.txVwHomeHdr);
        Intent i = new Intent(getApplicationContext(), confirm_location.class);
        String value = "";
        if ("".equals(locationAddress.getText())) {
            value = locationAddress.getHint().toString();
            // Log.d("msg0", "not get value ");

            //Log.d("msg1", value);
        } else {
            //Log.d("msg1", value);
            value = locationAddress.getText().toString();

        }
        i.putExtra("locationAddress", value);
        startActivity(i);
    }
}
