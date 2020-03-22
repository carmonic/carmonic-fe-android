package com.camsys.carmonic;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.camsys.carmonic.constants.Constants;
import com.camsys.carmonic.utilities.SharedData;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.camsys.carmonic.onboarding.SignInActivity;
import com.camsys.carmonic.onboarding.sign_upActivity;
import com.google.gson.Gson;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar = null;
    DrawerLayout drawer = null;
    NavigationView navigationView;

    Gson gson  = null;
    SharedData sharedData =  null;
    int navActiveId;

    Menu drawerMenu;


    String regID;
    TextView txtBack;
    TextView txtNext;
    TextView title;

    private GoogleMap mMap;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    String  TripReq ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedData =  new SharedData(getApplicationContext());
        //String userkey = sharedData.Get(Constants.USER_KEY,"");
        gson  =  new Gson();
//        if(userkey == ""){
//            sharedData.Clear(Constants.USER_KEY);
//            startActivity(new Intent(getApplicationContext(),LandingPage.class));
//            finish();
//            return;
//        }
       // Users user  =  gson.fromJson(userkey,Users.class);
       // System.out.println("------------------- " + userkey);


        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(Color.TRANSPARENT);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Status bar :: Transparent
        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }





        title = (TextView) toolbar.findViewById(R.id.txtTitle);
        title.setTextSize((float) 16.0);
        title.setText("");
        // title.setText(getResources().getString(R.string.app_name));
        txtBack = (TextView) toolbar.findViewById(R.id.txtBack);
        txtNext = (TextView) toolbar.findViewById(R.id.txtNext);
        txtNext.setVisibility(View.INVISIBLE);
        txtBack.setVisibility(View.INVISIBLE);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();



        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorYellow)));
        navigationView.setNavigationItemSelectedListener(this);



        loadFragment(getIntent());
    }

    public void loadFragment(Intent intent) {

        Fragment fragment = MapViewFragment.newInstance(intent);     //new MapViewFragment(TripReq);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mapFrame, fragment);
        ft.commit();

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void HideToolBar() {
        toolbar.setVisibility(View.INVISIBLE);
    }

    public void ShowToolBar() {
        toolbar.setVisibility(View.VISIBLE);
    }

    public void CustomizeToolbar() {
        toolbar.setTitle("");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onclick_Register(View view) {
        Intent i = new Intent(getApplicationContext(), sign_upActivity.class);
        startActivity(i);

    }

    public void onclick_SignIn(View view) {
        Intent i = new Intent(getApplicationContext(), SignInActivity.class);
        startActivity(i);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

          //  ComplementFragment cameraFragment = new ComplementFragment();
          //  transaction.replace(R.id.activityPage, cameraFragment);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {
            HideToolBar();

        } else if (id == R.id.nav_manage) {
            ShowToolBar();

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        transaction.commit();

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    public void GoToHomeFragment() {
//        GoToFragment(new HomeFragment());
//    }
//
//    public void GoToConfirmLocationFragment() {
//        GoToFragment(new ConfirmLocationFragment());
//    }
//
//    public void GoToFeedbackComplementFragment() {
//        GoToFragment(new ComplementFragment());
//    }
//
//    public void GoToFragment(Fragment sampleFragment) {
//
//        FragmentManager manager = getSupportFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//
//        transaction.replace(R.id.activityPage, sampleFragment);
//        transaction.commit();
//    }


}
