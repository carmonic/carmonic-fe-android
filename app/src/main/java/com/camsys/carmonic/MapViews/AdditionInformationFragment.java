package com.camsys.carmonic.MapViews;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.camsys.carmonic.R;
import com.camsys.carmonic.model.Car;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import io.socket.client.Socket;

@SuppressLint("ValidFragment")
public class AdditionInformationFragment extends BottomSheetDialogFragment {

    String mString;
    Activity activtiy;
    private BottomSheetBehavior mBehavior;
    Button btnNext =  null;
    EditText txtMyLocation =  null;
    String startAddress;
    LatLng startLongLat =  null;
    TextView cancel_action  =  null;

    EditText txtCarColor;
    EditText txtCarBrand;
    EditText txtCarModel;
    EditText txtModelYear;
    EditText txtIssueWithCar;





    MyDialogFragmentListener listener =  null;
    int minute;
    long min;
    Gson gson  =  null;
    Location mLastLocation =  null;
    double userLat,userLong;
    private Socket socket;

    int PLACE_AUTOCOMPLETE_REQUEST_CODE_TO = 1;




    public static AdditionInformationFragment newInstance( MyDialogFragmentListener listener) {
        AdditionInformationFragment f = new AdditionInformationFragment(listener);
        Bundle args = new Bundle();

        f.setArguments(args);
        return f;
    }

    @SuppressLint("ValidFragment")
    public AdditionInformationFragment(MyDialogFragmentListener listener) {
        this.listener= listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gson =  new Gson();


    }

    @Override
    public void onStart() {
        super.onStart();
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //final Dialog dialog = (Dialog) super.onCreateDialog(savedInstanceState);
        final BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.content_additional_information_layout, null);
        dialog.setContentView(view);

        LinearLayout linearLayout = view.findViewById(R.id.root);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
        params.height = getScreenHeight();
        linearLayout.setLayoutParams(params);

        LinearLayout vLayout =  (LinearLayout) dialog.findViewById(R.id.vLayout);

        //Customer customer1 = gson.fromJson(mString,Customer.class);
      //  User customer = gson.fromJson(mString, User.class);


        txtCarColor = (EditText) dialog.findViewById(R.id.txtCarColor);
        txtCarBrand = (EditText) dialog.findViewById(R.id.txtCarBrand);
        txtCarModel = (EditText) dialog.findViewById(R.id.txtCarModel);
        txtModelYear = (EditText) dialog.findViewById(R.id.txtModelYear);
        txtIssueWithCar = (EditText) dialog.findViewById(R.id.txtIssueWithCar);


        btnNext = (Button) dialog.findViewById(R.id.btnNext);
        cancel_action = (TextView) dialog.findViewById(R.id.cancel_action);
        cancel_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });




        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkCarBrand()){

                    Car car  =  new  Car();
                    car.setBrand(txtCarBrand.getText().toString());
                    car.setColor(txtCarColor.getText().toString());
                    car.setModel(txtCarModel.getText().toString());
                    car.setIsssues(txtIssueWithCar.getText().toString());
                    car.setYear(txtModelYear.getText().toString());

                    listener.onReturnValue(true,car);

                    dialog.dismiss();

                }
            }
        });



        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        return dialog;

    }

    private boolean checkCarBrand () {
        String carBrand = txtCarBrand.getText().toString().trim();
        String carColor = txtCarColor.getText().toString().trim();
        String carModelYear = txtModelYear.getText().toString().trim();
        String carModel = txtCarModel.getText().toString().trim();
        String  issueWithCar = txtIssueWithCar.getText().toString().trim();

        if (carColor.isEmpty() ) {
            txtCarColor.setError("required");
            requestFocus(txtCarColor);
            Toast.makeText(getActivity(), "Car color information required.", Toast.LENGTH_SHORT).show();
            return false;
        }else if (carBrand.isEmpty() ) {
            txtCarBrand.setError("required");
            requestFocus(txtCarBrand);
            Toast.makeText(getActivity(), "Car brand information required.", Toast.LENGTH_SHORT).show();
            return false;
        }else if (carModel.isEmpty() ) {
            txtCarModel.setError("required");
            requestFocus(txtCarModel);
            Toast.makeText(getActivity(), "Car model is required.", Toast.LENGTH_SHORT).show();
            return false;
        }else  if (carModelYear.isEmpty() ) {
            txtModelYear.setError("required");
            requestFocus(txtModelYear);
            Toast.makeText(getActivity(), "Car year is required.", Toast.LENGTH_SHORT).show();
            return false;
        }else if (issueWithCar.isEmpty() ) {
            txtIssueWithCar.setError("required");
            requestFocus(txtIssueWithCar);
            Toast.makeText(getActivity(), "Issue with the car is required.", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return  true;
        }
    }

    private void requestFocus (View view){
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public interface MyDialogFragmentListener {
        void onReturnValue(boolean indicator, Car car);
    }


}
