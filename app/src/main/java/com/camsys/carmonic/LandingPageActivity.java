package com.camsys.carmonic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.view.View;
import android.view.WindowManager;

import com.camsys.carmonic.constants.Constants;
import com.camsys.carmonic.onboarding.SignInActivity;
import com.camsys.carmonic.onboarding.sign_upActivity;
import com.google.firebase.analytics.FirebaseAnalytics;

public class LandingPageActivity extends AppCompatActivity {

    //private FirebaseAnalytics mFirebaseAnalytics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_landing);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);


        //  mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.registerReceiver(receiver, new IntentFilter(Constants.SetAction.REGISTRATION_COMPLETE));


    }

    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String str = intent.getStringExtra("token");
                System.out.println("receiverString :::: " + str);
            } else {
                System.out.println("receiverString :::: 1");
            }
        }
    };

    public void onclick_Register(View view) {
        Intent i = new Intent(getApplicationContext(), sign_upActivity.class);
        startActivity(i);
    }

    public void onclick_SignIn(View view) {
        Intent i = new Intent(getApplicationContext(), SignInActivity.class);
        startActivity(i);
    }
}

