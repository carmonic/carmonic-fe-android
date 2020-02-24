package com.camsys.carmonic.MapViews;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.camsys.carmonic.R;
import com.camsys.carmonic.adapters.AutoCompleteAdapter;
import com.camsys.carmonic.model.Car;
import com.camsys.carmonic.utilities.Util;
import com.google.gson.Gson;
import io.socket.client.Socket;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place.Field;

import java.util.Arrays;
import java.util.List;

@SuppressLint("ValidFragment")
public class ConfirmCustomerLocationFragment extends BottomSheetDialogFragment {

    String mString;
    Activity activtiy;
    private BottomSheetBehavior mBehavior;
    Button btnNext =  null;
    AutoCompleteTextView txtMyLocation =  null;
    String startAddress;
    LatLng startLongLat =  null;


    MyDialogFragmentListener listener =  null;
    int minute;
    long min;
    Gson gson  =  null;
    Location mLastLocation =  null;
    double userLat,userLong;
    private Socket socket;

    int PLACE_AUTOCOMPLETE_REQUEST_CODE_TO = 1;
    AutoCompleteAdapter mAdapter =  null;

    double  longitude  ,latitude;
     String  address;

    public static ConfirmCustomerLocationFragment newInstance(String address, MyDialogFragmentListener listener) {
        ConfirmCustomerLocationFragment f = new ConfirmCustomerLocationFragment(listener);
        Bundle args = new Bundle();
        args.putString("address", address);

        f.setArguments(args);
        return f;
    }

    @SuppressLint("ValidFragment")
    public ConfirmCustomerLocationFragment(MyDialogFragmentListener listener) {
        this.listener= listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mString = getArguments().getString("address");
        gson =  new Gson();


    }

    @Override
    public void onStart() {
        super.onStart();
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

//    private void  setUpAutoCompleteTextView() {
//        txtMyLocation .setThreshold(1);
//        txtMyLocation.onItemClickListener = autocompleteClickListener
//        mAdapter = AutoCompleteAdapter(this, placesClient)
//        autocomplete.setAdapter(mAdapter)
//    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //final Dialog dialog = (Dialog) super.onCreateDialog(savedInstanceState);
        final BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.content_confirm_location_layout, null);
        dialog.setContentView(view);

        LinearLayout linearLayout = view.findViewById(R.id.root);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
        params.height = getScreenHeight();
        linearLayout.setLayoutParams(params);
        LinearLayout vLayout =  (LinearLayout) dialog.findViewById(R.id.vLayout);

        //Customer customer1 = gson.fromJson(mString,Customer.class);
      //  User customer = gson.fromJson(mString, User.class);


        Places.initialize(getActivity(), getResources().getString(R.string.google_map_key));
        PlacesClient placesClient = Places.createClient(getActivity());
        txtMyLocation = (AutoCompleteTextView) dialog.findViewById(R.id.txtMyLocation);
        //txtMyLocation.setText(address != ""?address:"");


        btnNext = (Button) dialog.findViewById(R.id.btnNext);
        TextView cancel_action = (TextView) dialog.findViewById(R.id.cancel_action);
        cancel_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
//        vLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    Intent intent =
//                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
//                                    .build(getActivity());
//                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE_TO);
//
//
//
//                } catch (GooglePlayServicesRepairableException e) {
//                    e.printStackTrace();
//                } catch (GooglePlayServicesNotAvailableException e) {
//                    e.printStackTrace();
//                }
//            }
//        });



        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                final AdditionInformationFragment myBottomSheet = AdditionInformationFragment.newInstance(new AdditionInformationFragment.MyDialogFragmentListener() {
                    @Override
                    public void onReturnValue(boolean indicator, Car car) {


                        //listener.onReturnValue(true,txtMyLocation.getText().toString(),userLat,userLong,car);
                       listener.onReturnValue(true,txtMyLocation.getText().toString(),3.000,5.85420,car);

                         dialog.dismiss();
                    }
                });
                myBottomSheet.show(getActivity().getSupportFragmentManager(), myBottomSheet.getTag());




            }
        });



        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        // change the state of the bottom sheet


        txtMyLocation.setThreshold(1);
        txtMyLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 AutocompletePrediction item = mAdapter.getItem(position);
                 String placeID = item.getPlaceId();
                 List<Field> placeFields = Arrays.asList(Field.ID, Field.NAME, Field.ADDRESS, Field.LAT_LNG);
                  FetchPlaceRequest request = null;
                    if(placeID!=null){
                        request = FetchPlaceRequest.builder(placeID, placeFields).build();
                    }

                    if(request!=null){
                        placesClient.fetchPlace(request).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
                            @Override
                            public void onSuccess(FetchPlaceResponse fetchPlaceResponse) {
                              //
                                StringBuilder  stringBuilder = new  StringBuilder();
                                stringBuilder.append("Name:" + fetchPlaceResponse.getPlace().getName() +"\n");

                                stringBuilder.append("Latitude:" +  fetchPlaceResponse.getPlace().getLatLng().latitude +"\n");
                                stringBuilder.append("Longitude:" +  fetchPlaceResponse.getPlace().getLatLng().longitude +"\n");
                                stringBuilder.append("Address:" +  fetchPlaceResponse.getPlace().getAddress() +"\n");
                                txtMyLocation.setText(stringBuilder);
                                userLat = fetchPlaceResponse.getPlace().getLatLng().latitude ;
                                userLong = fetchPlaceResponse.getPlace().getLatLng().longitude;
                                address = fetchPlaceResponse.getPlace().getAddress();
                                Log.i("TAG", stringBuilder.toString());

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@androidx.annotation.NonNull Exception e) {
                               e.printStackTrace();
                                txtMyLocation.setText("Lagos - Ibadan Express Road");
                                userLat = .6086734;
                                userLong = 3.3714108;
                                address = "Lagos - Ibadan Express Rd, Ikosi Ketu, Lagos, Nigeria";
                            }
                        });


                        Util.hideSoftKeyboard(getActivity(),txtMyLocation);
                }
            }
        });

        mAdapter =  new  AutoCompleteAdapter(getActivity(), placesClient);
        txtMyLocation.setAdapter(mAdapter);

        return dialog;

    }



    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public interface MyDialogFragmentListener {
        void onReturnValue(boolean indicator, String address, double latitude, double longitude, Car car);
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE_TO) {
//            if (resultCode == RESULT_OK) {
//                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
//                Log.i("Carmonic", "Place: " + place.getName());
//
//                txtMyLocation.setText(place.getAddress());
//                startLongLat = place.getLatLng();
//                Log.i("getAddress", place.getAddress() + "");
//
//            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
//                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
//                // TODO: Handle the error.
//                Log.i("Carmonic", status.getStatusMessage());
//
//                Toast.makeText(getActivity(), status.getStatusMessage(), Toast.LENGTH_LONG).show();
//
//            } else if (resultCode == RESULT_CANCELED) {
//                // The user canceled the operation.
//            }
//        }
//    }

}
