package com.camsys.carmonic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.camsys.carmonic.networking.BackEndDAO;
import com.camsys.carmonic.financial.Bill;
import com.camsys.carmonic.principals.Mechanic;
import com.camsys.carmonic.principals.User;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

//ToDo: Merge this class with MapsActivity, there is no need for them to be separate
//ToDo: Remove the websocket concerns from here
public class MapsActivityWithLocationConfirmed extends FragmentActivity implements OnMapReadyCallback {

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private GoogleMap mMap;
    private boolean mLocationPermissionGranted;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private double longitude;
    private double latitude;
    private LatLng customerPosition = null;
    private LatLng mechanicPosition = null;
    private Marker mechanicMarker = null;

    private ConstraintLayout popUpConstraintLayout;
    private ConstraintLayout metadataConstraintLayout;
    private TextView mechanicName;
    private TextView mechanicDistanceMessage;
    private TextView mechanicStarRating;
    private ImageView mechanicImage; //ToDo: fetch mechanic image from backend
    private TextView popUpMessage;
    private ConstraintLayout bottomFrameConstraintLayout;

    private Socket socket;
    private List<Mechanic> mechanicList; //list of closest mechanics to user
    private User user;
    private String token;
    private boolean mechanicJobAccepted; //true if a mechanic has accepted the job
    private Gson gson = new Gson();

    private static int MECHANIC_TIME_OUT = 8000;
    private Timer timer = new Timer();
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_with_location_confirmed);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        popUpConstraintLayout = findViewById(R.id.networkActivityPopUp);
        popUpConstraintLayout.setVisibility(View.INVISIBLE);
        popUpMessage = findViewById(R.id.txtVwScreen2SubTitle);
        mechanicName = findViewById(R.id.mechanicName);
        mechanicDistanceMessage = findViewById(R.id.mechanicDistanceMessage);
        mechanicStarRating = findViewById(R.id.mechanicStarRating);
        metadataConstraintLayout = findViewById(R.id.metadataConstraint);
        metadataConstraintLayout.setVisibility(View.INVISIBLE);
        bottomFrameConstraintLayout = findViewById(R.id.bottomframe);

        longitude = getIntent().getDoubleExtra("longitude", 0.0);
        latitude = getIntent().getDoubleExtra("latitude", 0.0);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        token = preferences.getString("Authorisation", "");
        user = gson.fromJson(preferences.getString("User", ""), User.class);

        mapFragment.getMapAsync(this);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        setupSocket();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        try {
            getLocationPermission();
            if (mLocationPermissionGranted) {
                Task locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            customerPosition = new LatLng(location.getLatitude(), location.getLongitude());
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(customerPosition));
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                        }
                    }
                });
            }

        }  catch(SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    public void onclick_mechanic_request(View view) {
        getMechanics();

        MapsActivityWithLocationConfirmed.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                popUpConstraintLayout.setVisibility(View.VISIBLE);
                metadataConstraintLayout.setVisibility(View.INVISIBLE);
                bottomFrameConstraintLayout.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void getMechanics() {

        BackEndDAO.getMechanics(customerPosition.longitude, customerPosition.latitude, token, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBodyString = response.body().string();
                Gson gson = new Gson();
                mechanicList = gson.fromJson(responseBodyString, new TypeToken<ArrayList<Mechanic>>(){}.getType());
                notifyMechanics();
            }
        });
    }

    private void setupSocket() {
        try {
            OkHttpClient okHttpClient = BackEndDAO.getClient();

            IO.setDefaultOkHttpWebSocketFactory(okHttpClient);
            IO.setDefaultOkHttpCallFactory(okHttpClient);

            IO.Options opts = new IO.Options();
            opts.callFactory = okHttpClient;
            opts.webSocketFactory = okHttpClient;

            socket = IO.socket(BackEndDAO.getBackendURL(), opts);
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    socket.emit("customer_register", gson.toJson(user));
                }

            }).on("job_acc", new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    JSONObject jsonObject = (JSONObject) args[0];
                    Mechanic mechanic = gson.fromJson(jsonObject.toString(), Mechanic.class);
                    mechanicJobAccepted = true;
                    mechanicName.setText(mechanic.getName());
                    BackEndDAO.getEstimatedDistance(mechanic.getLongitude(), mechanic.getLatitude(), customerPosition.longitude, customerPosition.latitude, user.getToken(), new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            mechanicDistanceMessage.setText(generateProximityMessage(mechanic.getName(), response.body().string()));
                            MapsActivityWithLocationConfirmed.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    popUpConstraintLayout.setVisibility(View.INVISIBLE);
                                    bottomFrameConstraintLayout.setVisibility(View.INVISIBLE);
                                    metadataConstraintLayout.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                    });
                }

            }).on("job_con", new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    JSONObject billJsonObject = (JSONObject) args[1];
                    JSONObject mechanicJsonObject = (JSONObject) args[0];
                    Bill bill = gson.fromJson(billJsonObject.toString(), Bill.class);
                    Mechanic mechanic = gson.fromJson(mechanicJsonObject.toString(), Mechanic.class);
                    Intent i = new Intent(getApplicationContext(), BillingActivity.class);
                    i.putExtra("mechanicId", mechanic.getId());
                    i.putExtra("bill", bill);
                    timer.cancel();
                    timer.purge();
                    startActivity(i);
                }

            }).on("update_location", new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    JSONObject jsonObject = (JSONObject) args[0];
                    Mechanic mechanic = gson.fromJson(jsonObject.toString(), Mechanic.class);
                    mechanicPosition = new LatLng(mechanic.getLatitude(), mechanic.getLongitude());
                    MapsActivityWithLocationConfirmed.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mechanicMarker == null) {
                                mechanicMarker = mMap.addMarker(new MarkerOptions().position(mechanicPosition));
                            } else {
                                mechanicMarker.setPosition(mechanicPosition);
                            }
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(mechanicPosition));
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

                            user.setLatitude(customerPosition.latitude);
                            user.setLongitude(customerPosition.longitude);

                            socket.emit("customer_update_location", gson.toJson(mechanic), gson.toJson(user));
                        }
                    });
                }

            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                }

            });
            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    // ToDo: Extract this logic from the class
    private void notifyMechanics() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (i >= mechanicList.size()) {
                    // Run through all the mechanics and none accepted
                    i = 0;
                    MapsActivityWithLocationConfirmed.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            popUpMessage.setText("No mechanics available, try again later");
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    //ToDo: I'm not sure this needs a separate thread
                                    MapsActivityWithLocationConfirmed.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            popUpConstraintLayout.setVisibility(View.INVISIBLE);
                                            popUpMessage.setText("Just give  us a minute\nwe're trying to connect\nyou to nearby mechanic");
                                            bottomFrameConstraintLayout.setVisibility(View.VISIBLE);                                }
                                    });
                                }
                            }, 3000);

                        }
                    });
                    cancel();
                }

                if (!mechanicJobAccepted && i < mechanicList.size()) {
                    socket.emit("customer_request_job", gson.toJson(mechanicList.get(i)), gson.toJson(user));
                    i++;
                } else {
                    // Job accepted
                    // ToDo: update UI with details of mechanic
                    cancel();
                    i = 0;
                    mechanicJobAccepted = false;
                }
            }
        }, 0, MECHANIC_TIME_OUT);
    }

    private String generateProximityMessage(String firstname, String distance) {
        return firstname + " is " + distance + " away";
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
}
