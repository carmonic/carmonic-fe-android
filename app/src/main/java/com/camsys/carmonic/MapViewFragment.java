//package com.camsys.carmonic;
//
//
//import android.Manifest;
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.location.Location;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.ResultReceiver;
//import android.provider.Settings;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.Switch;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatDelegate;
//import androidx.appcompat.widget.AppCompatButton;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import androidx.fragment.app.Fragment;
//
//import com.camsys.carmonic.utilities.SharedData;
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.GooglePlayServicesUtil;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.location.LocationListener;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.maps.CameraUpdate;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.MapView;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
//import com.google.android.gms.maps.model.CameraPosition;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.android.gms.maps.model.PolylineOptions;
//import com.google.gson.Gson;
//
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URISyntaxException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import io.socket.client.IO;
//import io.socket.client.Socket;
//import io.socket.emitter.Emitter;
//import okhttp3.OkHttpClient;
//
//
//public class MapViewFragment extends Fragment  {
//
//
//    boolean  showRequest  =  true  ;
//    // private AddressResultReceiver mResultReceiver;
//    protected LatLng start;
//    protected LatLng mCenterLatLong;
//    protected GoogleApiClient mGoogleApiClient;
//    MapView mMapView;
//    Gson gson = null;
//
//
//
//    public static MapViewFragment newInstance(Intent intent) {
//        MapViewFragment fragment = new MapViewFragment();
//        Bundle args = new Bundle();
//        args.putString("action", intent.getAction());
//        args.putString("message", intent.getStringExtra("message"));
//        args.putString("customerJSON", intent.getStringExtra("customerJSON"));
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.activity_maps, container, false);
//        setHasOptionsMenu(true);
//
//
//
//        return rootView;
//    }
//
//
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
//
//        gson = new Gson();
//
//    }
//
//
//
//
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle item selection
//        switch (item.getItemId()) {
//            case R.id.action_settings:
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//
//
//    @Override
//    public void onResume() {
//        super.onResume();
////        mMapView.onResume();
////        mMapView.getMapAsync(this);
//    }
//
//
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        //mMapView.onStart();
//       // mGoogleApiClient.connect();
//        //mMapView.getMapAsync(this);
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//      //  mMapView.onDestroy();
//    }
//
//
//
//}