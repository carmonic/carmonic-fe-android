package com.camsys.carmonic;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Handler;
import android.os.ResultReceiver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.camsys.carmonic.History.HistoryActivity;
import com.camsys.carmonic.MapViews.ConfirmCustomerLocationFragment;
import com.camsys.carmonic.constants.Constants;
import com.camsys.carmonic.financial.Bill;
import com.camsys.carmonic.model.Car;
import com.camsys.carmonic.model.Mechanic;
import com.camsys.carmonic.model.User;
import com.camsys.carmonic.networking.BackEndDAO;
import com.camsys.carmonic.payment.PaymentActivity;
import com.camsys.carmonic.service.FetchAddressIntentService;
import com.camsys.carmonic.state.JobStatus;
import com.camsys.carmonic.utilities.SharedData;
import com.camsys.carmonic.utilities.Util;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import static com.camsys.carmonic.constants.Constants.MAP_TYPES;

public class MapsActivity extends FragmentActivity implements  OnMapReadyCallback,
        GoogleMap.OnCameraMoveStartedListener,
        GoogleMap.OnCameraMoveListener,
        GoogleMap.OnCameraMoveCanceledListener,
        GoogleMap.OnCameraIdleListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    private boolean mLocationPermissionGranted;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private LatLng customerPosition;
    private DrawerLayout mDrawerLayout;
    SharedData sharedData =  null;
    Gson  gson  =  null;
    User user  =  null;
    private AddressResultReceiver mResultReceiver;
    protected String mAddressOutput;
    protected String mAreaOutput;
    protected String mCityOutput;
    protected String mStateOutput;
    EditText txtMyLocation ;
    LinearLayout layout_overlay =  null;
    private JobStatus mechanicJobStatus = JobStatus.IDLE;


    private Socket socket;
    private ArrayList<Mechanic> mechanicList; //list of closest mechanics to user
    private Mechanic mechanic;
    private String token;
    private static int MECHANIC_TIME_OUT = 300000;
    private Timer timer = new Timer();
    String  car = null;
   // ArrayList<Mechanic> mechanicList =  null;


    protected GoogleApiClient mGoogleApiClient;
    SupportMapFragment mapFragment ;
    GoogleMap mMap;
    protected LatLng start;
    protected LatLng mCenterLatLong;
    Location mLastLocation ;
    LocationRequest mLocationRequest;


    int navActiveId;
    DrawerLayout drawer;
    Menu drawerMenu;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedData =  new SharedData(getApplicationContext());
        gson  =  new Gson();
        String userkey = sharedData.Get(Constants.SharedDataCst.USER_KEY,"");
        user  =  gson.fromJson(userkey,User.class);

        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        checkPlayService(this);
        mapFragment.getMapAsync(this);


        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mDrawerLayout = findViewById(R.id.drawerMain);

        txtMyLocation = (EditText) findViewById(R.id.txtMyLocation);
        mResultReceiver = new AddressResultReceiver(new Handler());

        layout_overlay = (LinearLayout) findViewById(R.id.layout_overlay);
        layout_overlay.setVisibility(View.GONE);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nv);
        navigationView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorYellow)));
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);

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



    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        int id = item.getItemId();

        if (id == R.id.nav_profle) {
            Toast.makeText(this, "I am camera", Toast.LENGTH_SHORT).show();

        }else if(id ==  R.id.nav_history){
             startActivity(new Intent(this,HistoryActivity.class));
        }else if(id == R.id.nav_account){
            startActivity(new Intent(this, PaymentActivity.class));
        }

        transaction.commit();

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getMechanics(double latitude,double  longitude,String  token) {

        //customerPosition.longitude, customerPosition.latitude
        BackEndDAO.getMechanics(longitude,latitude, token, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBodyString = response.body().string();
                System.out.println("responseBodyString  " + responseBodyString);
                Gson gson = new Gson();
                 mechanicList = gson.fromJson(responseBodyString, new TypeToken<ArrayList<Mechanic>>(){}.getType());
                if(mechanicList !=  null){
                    //show  marker on the  map
                    createMarker(mechanicList);

                }else{
                    System.out.println("No mechanic available");
                }
            }
        });
    }

    public void onclick_mechanic_request(View view) {




        final ConfirmCustomerLocationFragment myBottomSheet = ConfirmCustomerLocationFragment.newInstance(txtMyLocation.getText().toString(),  new ConfirmCustomerLocationFragment.MyDialogFragmentListener() {
            @Override
            public void onReturnValue(boolean indicator, String address, double latitude, double longitude, Car car) {
                String  gsonCar = gson.toJson(car);

                layout_overlay.setVisibility(View.VISIBLE);
               // if(mechanicList != null){
                    notifyMechanics(mechanicList,gsonCar);
//                }else{
//                    Toast.makeText(MapsActivity.this, "No mechanic available", Toast.LENGTH_SHORT).show();
//                }


//                Intent i = new Intent(getApplicationContext(), MapsActivityWithLocationConfirmed.class);
//                i.putExtra("longitude", latitude==0.0?customerPosition.latitude:latitude);
//                i.putExtra("latitude", longitude==0.0?customerPosition.longitude:longitude);
//                i.putExtra("Address",address==null?txtMyLocation.getText().toString():address);
//                i.putExtra("car",gsonCar);
//                startActivity(i);

            }
        });
        myBottomSheet.show(getSupportFragmentManager(), myBottomSheet.getTag());

    }





    public void onClickMenuImage(View view) {
        System.out.println("Aaayeeee ");
        mDrawerLayout.openDrawer(Gravity.START);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        String firstname = getIntent().getStringExtra("firstname");
        String lastname = getIntent().getStringExtra("lastname");

        try {
            getLocationPermission();
            if (mLocationPermissionGranted) {
                Task locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            customerPosition = new LatLng(location.getLatitude(), location.getLongitude());
                            mMap.addMarker(new MarkerOptions()
                                           .position(customerPosition)
                                           .icon(BitmapDescriptorFactory.fromResource(R.drawable.baseline_commute_black))
                                           .title(firstname));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(customerPosition));
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

                            getMechanics(location.getLatitude(),location.getLongitude(),user.getToken());
                            startIntentService(location);
                            //    socketLocationUpdate(mLastLocation);
                        }
                    }
                });
            }

            mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                @Override
                public void onCameraChange(CameraPosition cameraPosition) {
                    Log.d("Camera postion change" + "", cameraPosition + "");
                    mMap.clear();
                    try {
                        Location mLocation = new Location("");// Location("");
                        mLocation.setLatitude(cameraPosition.target.latitude);
                        mLocation.setLongitude(cameraPosition.target.longitude);
                        startIntentService(mLocation);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch(SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    private  void  createMarker(ArrayList<Mechanic> mecho){
        for(Mechanic item : mecho){
            LatLng mechanicPosition  =  new LatLng(item.getLatitude(),item.getLongitude());
            mMap.addMarker(new MarkerOptions()
                               .position(mechanicPosition)
                               .icon(BitmapDescriptorFactory.fromResource(R.drawable.baseline_commute_black))
                               .title(item.getName()));

        }
    }

    /**
     * Creates an intent, adds location data to it as an extra, and starts the intent service for
     * fetching an address.
     */
    protected void startIntentService(Location mLocation) {
        // Create an intent for passing to the intent service responsible for fetching the address.
        try {

            Intent intent = new Intent(getApplicationContext(), FetchAddressIntentService.class);
            intent.putExtra(Constants.LocationConstants.RECEIVER, mResultReceiver);
            intent.putExtra(Constants.LocationConstants.LOCATION_DATA_EXTRA, mLocation);
            startService(intent);

        } catch (Exception ex) {
            System.out.println("Exception:::::" + ex.toString());
        }

    }


    private void getMechanicsMecha(double longitude, double latitude,String  carObjectString) {
        mechanicJobStatus = JobStatus.REQUESTING;
        //customerPosition.longitude, customerPosition.latitude
        BackEndDAO.getMechanics(longitude,latitude,car, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBodyString = response.body().string();
                Gson gson = new Gson();
                mechanicList = gson.fromJson(responseBodyString, new TypeToken<ArrayList<Mechanic>>(){}.getType());

                notifyMechanics(mechanicList,carObjectString);
            }
        });
    }



    private String generateProximityMessage(String firstname, String distance) {
            return firstname + " is " + distance + " away";
        }


    // ToDo: Extract this logic from the class
    private void notifyMechanics(ArrayList<Mechanic> mechList, String  carObjectString) {
        for (int i=0; i<mechList.size() && mechanicJobStatus != JobStatus.ACCEPTED; i++) {
            System.out.println("Mechanic:::: " +gson.toJson(mechList.get(i)));
            System.out.println("User::: " +gson.toJson(user));
            System.out.println("Car::: " +carObjectString);
            socket.emit("customer_request_job", gson.toJson(mechList.get(i)), gson.toJson(user),carObjectString);
        }

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (mechanicJobStatus == JobStatus.REQUESTING) {
                    mechanicJobStatus = JobStatus.IDLE;
                    MapsActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                           // popUpMessage.setText("No mechanics available, try again later");
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    MapsActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                        layout_overlay.setVisibility(View.VISIBLE);                               }
                                    });
                                }
                            }, 7000);
                        }
                    });
                }
            }
        }, MECHANIC_TIME_OUT);
    }




    /**
     * Receiver for data sent from FetchAddressIntentService.
     */
    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        /**
         * Receives data sent from FetchAddressIntentService and updates the UI in MainActivity.
         */
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            // Display the address string or an error message sent from the intent service.
            mAddressOutput = resultData.getString(Constants.LocationConstants.RESULT_DATA_KEY);
            mAreaOutput = resultData.getString(Constants.LocationConstants.LOCATION_DATA_AREA);
            mCityOutput = resultData.getString(Constants.LocationConstants.LOCATION_DATA_CITY);
            mStateOutput = resultData.getString(Constants.LocationConstants.LOCATION_DATA_STREET);
            // Show a toast message if an address was found.
            if (resultCode == Constants.LocationConstants.SUCCESS_RESULT) {
                txtMyLocation.setText(mStateOutput);
            }
        }
    }


    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        Constants.PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                //finish();
            }
            return false;
        }
        return true;
    }
    private void checkPlayService(Context context) {

        if (checkPlayServices()) {
            // If this check succeeds, proceed with normal processing.
            // Otherwise, prompt user to get valid Play Services APK.
            if (!Util.isLocationEnabled(this)) {
                // notify user
                android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(context);
                dialog.setMessage("Location not enabled!");
                dialog.setPositiveButton("Open location settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        // TODO Auto-generated method stub

                    }
                });
                dialog.show();
            }
            buildGoogleApiClient();
        } else {
            Toast.makeText(context, "Location not supported in this device", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        mapFragment.onResume();
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mapFragment.onPause();
        //stop location updates when Activity is no longer active
        try {
            if (mGoogleApiClient != null) {
//                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (com.google.android.gms.location.LocationListener) this);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mapFragment.onStart();
        mGoogleApiClient.connect();
        //mMapView.getMapAsync(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapFragment.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapFragment.onLowMemory();
    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

//    @Override
//    public void onConnected(Bundle bundle) {
//        try {
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return;
//            }
//
//            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
//                    mGoogleApiClient);
//            if (mLastLocation != null) {
//                // changeMap(mLastLocation);
//                initCamera(mLastLocation);
//                startIntentService(mLastLocation);
//
//
//            } else
//                try {
//                    LocationServices.FusedLocationApi.removeLocationUpdates(
//                            mGoogleApiClient, (com.google.android.gms.location.LocationListener) this);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            try {
//                mLocationRequest = new LocationRequest();
//                mLocationRequest.setInterval(60000);
//                mLocationRequest.setFastestInterval(10000);
//                mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//                LocationServices.FusedLocationApi.requestLocationUpdates(
//                        mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);
//                //Toast.makeText(getActivity(),"Longitude = " +mLastLocation.getLongitude()+ " Latyitude = " + mLastLocation.getLatitude() + "" ,Toast.LENGTH_LONG).show();
//                startIntentService(mLastLocation);
//                //    socketLocationUpdate(mLastLocation);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//
//        }
//
//    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        System.out.println("---------------------------");
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        initCamera(mLastLocation);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    @Override
    public void onCameraMoveStarted(int reason) {
        Log.i("mGoogleMap", "onCameraMoveStarted(" + "" + ")");
        //  addCameraTargetToPath();
    }

    @Override
    public void onCameraMove() {
        // When the camera is moving, add its target to the current path we'll draw on the map.
        // if (currPolylineOptions != null) {
        addCameraTargetToPath();
        // }
        Log.i("mGoogleMap", "onCameraMove");
    }

    @Override
    public void onCameraMoveCanceled() {
        Log.i("mGoogleMap", "onCameraMoveCancelled");
    }

    @Override
    public void onCameraIdle() {

    }

    private void addCameraTargetToPath() {
        mCenterLatLong = mMap.getCameraPosition().target;
        // currPolylineOptions.add(mCenterLatLong);
        // mGoogleMap.clear();
        try {

            //System.out.println("mCenterLatLong:: " + mCenterLatLong.latitude + "mCenterLatLong:: " + mCenterLatLong.longitude);
            Location mLocation = new Location("");// Location("");
            mLocation.setLatitude(mCenterLatLong.latitude);
            mLocation.setLongitude(mCenterLatLong.longitude);
            //startIntentService(mLocation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initCamera(Location location) {

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        CameraPosition position = CameraPosition.builder()
                .target(latLng)
                .zoom(15.5f)
                .bearing(300)
                .tilt(50)
                .build();

        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(position), null);

        markerOptions.position(latLng);
        markerOptions.title("Pick Up Point");
        markerOptions.draggable(true).visible(true);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        // mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        mMap.setMapType(MAP_TYPES[1]);
        mMap.getUiSettings().setZoomControlsEnabled(true);

    }


}