package com.camsys.carmonic.onboarding;

import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.camsys.carmonic.History.HistoryActivity;

import com.camsys.carmonic.MapViews.MapViewFragment;
import com.camsys.carmonic.R;
import com.camsys.carmonic.constants.Constants;

import com.camsys.carmonic.model.User;
import com.camsys.carmonic.utilities.SharedData;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.util.List;

public class MainActivity extends AppCompatActivity
implements NavigationView.OnNavigationItemSelectedListener
                           {

    protected OnBackPressedListener onBackPressedListener;
                               Gson  gson  =  null;
                               User user  =  null;
    SharedData sharedData =  null;
    int navActiveId;
    DrawerLayout drawer;
    Menu drawerMenu;
    NavigationView navigationView;
    String regID;
    TextView txtBack;
    TextView txtNext;
    TextView title;
    String  TripReq ;
    private GoogleMap mMap;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        sharedData =  new SharedData(getApplicationContext());

        gson  =  new Gson();
        sharedData =  new SharedData(getApplicationContext());
        gson  =  new Gson();
        String userkey = sharedData.Get(Constants.SharedDataCst.USER_KEY,"");
        user  =  gson.fromJson(userkey, User.class);

        System.out.println("Firstname ::: " + user.getFirstname());

        setContentView(R.layout.activity_amazon_test);
        Toolbar toolbar = findViewById(R.id.toolbar);
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


        title = toolbar.findViewById(R.id.txtTitle);
        title.setTextSize((float) 16.0);
        title.setText("");
        // title.setText(getResources().getString(R.string.app_name));
        txtBack = toolbar.findViewById(R.id.txtBack);
        txtNext = toolbar.findViewById(R.id.txtNext);
        txtNext.setVisibility(View.INVISIBLE);
        txtBack.setVisibility(View.INVISIBLE);


        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        navigationView = findViewById(R.id.nav_view);
        navigationView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorYellow)));
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.inflateHeaderView(R.layout.nav_header);
        ImageView imageView = headerView.findViewById(R.id.user_picture);
        TextView txtFName = headerView.findViewById(R.id.fName);
        TextView txtUsername = headerView.findViewById(R.id.username);



System.out.println("===getACtion====" + getIntent().getAction());

        loadFragment(getIntent());


    }

    public void loadFragment(Intent intent) {

        Fragment fragment = com.camsys.carmonic.MapViews.MapViewFragment.newInstance(intent);   //MapViewFragment.newInstance(intent);     //new MapViewFragment(TripReq);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mapFrame, fragment);
        ft.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        //check if home fragment is at top
        //if not, show home fragment
        //if yes, allow to close

        boolean showHome = true;

        try {
            FragmentManager fragmentManager = getSupportFragmentManager();

            List<Fragment> fragmentList = fragmentManager.getFragments();
            for (Fragment fragment : fragmentList) {
                if (fragment != null && fragment.isVisible()) {
                    if (fragment instanceof MapViewFragment) {
                        showHome = false;
                    } else if (fragment.getChildFragmentManager().getBackStackEntryCount() > 0) {
                        //popchildbackstack
                        fragment.getChildFragmentManager().popBackStackImmediate();
                        return;
                    }
                }
            }

            if (showHome) {
                //check if there are pending reverse actions on activity
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    showHome = false;
                }
            } else {

                Toast.makeText(this, "Back", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
        }

//        if (showHome) {
//            displayDrawerItem(R.id.nav_dashboard);
//            return;
//        }

        //   super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.landing_page, menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;

    }

    @Override
    protected void onNewIntent(Intent intent) {

        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the

//        int id = item.getItemId();
//        if (id == R.id.p) {
//            //Toast.makeText(getApplicationContext()," iti sfrom the main activty ",Toast.LENGTH_LONG).show();
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Menu menu = navigationView.getMenu();
        navActiveId = item.getItemId();
        displayDrawerItem(navActiveId);
        return true;
    }

    public void displayDrawerItem(int menuItemId) {
        Fragment fragment = null;
        Intent intent =   null;
        String title = "";
        //boolean hasFab = false;
        boolean noTitle = false;
        switch (menuItemId) {
            case R.id.nav_history:
                  intent = new Intent (MainActivity.this, HistoryActivity.class);
                 startActivity(intent);
              //  overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                 break;

            case R.id.nav_profle:
               //  intent = new Intent (MainActivity.this, ProfileActivity.class);
               // startActivity(intent);
               // overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
                break;

//            case R.id.nav_account: {
////                fragment = AccountFragment.newInstance();
////                title="Account";
//                break;
//            }
//

//
//            case R.id.nav_help: {
////                fragment = HelpFragment.newInstance();
////                title="Account";
//                break;
//            }

            default: {
            // fragment = new MapFragment();
            break;
        }
        }


        //check the menu directly
        if (drawerMenu == null) {
            drawerMenu = navigationView.getMenu();
        }

        MenuItem menuItem = drawerMenu.findItem(menuItemId);

        if (TextUtils.isEmpty(title)) {
            try {
                CharSequence titleChars = menuItem.getTitle();

                if (!TextUtils.isEmpty(titleChars)) {
                    title = titleChars.toString();
                }
            } catch (Exception e) {

            }
        }

        if (!noTitle && !TextUtils.isEmpty(title)) {
            // set the toolbar title
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(title);
            }
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mapFrame, fragment);
            ft.commit();

            /*if(hasFab){
                //fabSearch.show();
                fab.show();
            }*/
        }

        //just in case it was called manually
        menuItem.setChecked(true);  //.setCheckedItem(menuItemId);

        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    @Override
    protected void onDestroy() {
        onBackPressedListener = null;
        super.onDestroy();
    }



    public interface OnBackPressedListener {
        void doBack();
    }


}
