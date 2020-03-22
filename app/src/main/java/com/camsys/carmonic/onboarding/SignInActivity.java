package com.camsys.carmonic.onboarding;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.camsys.carmonic.MapsActivity;
import com.camsys.carmonic.constants.Constants;
import com.camsys.carmonic.networking.*;
import com.camsys.carmonic.R;
//import com.camsys.carmonic.networking.BackEndDAO;
//import com.camsys.carmonic.model.LoginResponse;
//import com.camsys.carmonic.model.User;
import com.camsys.carmonic.model.LoginResponse;
import com.camsys.carmonic.model.User;
import com.camsys.carmonic.utilities.SharedData;
import com.camsys.carmonic.utilities.Util;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SignInActivity extends AppCompatActivity {

    private static final String SIGN_IN_INSTRUCTION_MESSAGE = "Sign into your account";

    TextInputLayout txtInputLayEmail = null;
    TextInputLayout txtInputLayPassword = null;
    TextView subTitleTv = null;


    EditText txtPassowrd;
    EditText txtEmailAddress;
    TextInputLayout inputLayoutPassword = null;
    TextInputLayout inputLayoutEmailAddress= null;
    LinearLayout wait_icon =  null;
    LinearLayout signlayout =  null;
    private Context mContext;
    private Activity mActivity;
    SharedData sharedData =   null;
    Gson gson =  null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mContext = getApplicationContext();
        mActivity = SignInActivity.this;
        sharedData = new SharedData(getApplicationContext());
        gson = new Gson();


        setContentView(R.layout.activity_sign_in);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        String storedData =  sharedData.Get(Constants.SharedDataCst.USER_KEY,"");
        if(storedData != ""){
            Intent intent  =  new Intent(getApplicationContext(), MapsActivity.class);
            startActivity(intent);
           // overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        }



        TextView hdrTv = findViewById(R.id.txtVwScreen2Hdr);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceBold.ttf");
        hdrTv.setTypeface(tf);
////
////        subTitleTv = findViewById(R.id.txtVwScreen2SubTitle);
////        Typeface tfSub = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf");
////        subTitleTv.setTypeface(tfSub);
////        subTitleTv.setText(SIGN_IN_INSTRUCTION_MESSAGE);
////
        txtInputLayEmail = findViewById(R.id.txtinputLayEmail);
        Typeface tfPwd = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf");
        txtInputLayEmail.setTypeface(tfPwd);

        TextInputEditText txtEditPwd = findViewById(R.id.txtEmailAddress);
        Typeface tfEditPwd = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf");
        txtEditPwd.setTypeface(tfEditPwd);

        txtInputLayPassword = findViewById(R.id.txtinputLayPwd);
        Typeface tfPwd2 = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf");
        txtInputLayPassword.setTypeface(tfPwd2);

        TextInputEditText txtEditPwd2 = findViewById(R.id.txtPassword);
        Typeface tfEditPwd2 = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf");
        txtEditPwd2.setTypeface(tfEditPwd2);


        ImageButton imgButton = (ImageButton) findViewById(R.id.appBackButton);
        final Button btnSubmit = (Button) findViewById(R.id.btn_sign_in);


        txtEmailAddress = (EditText) findViewById(R.id.txtEmailAddress);
        txtPassowrd = (EditText) findViewById(R.id.txtPassword);
        inputLayoutEmailAddress = (TextInputLayout) findViewById(R.id.txtinputLayEmail);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.txtinputLayPwd);

        txtEmailAddress.addTextChangedListener(new MyTextWatcher(txtEmailAddress));
        txtPassowrd.addTextChangedListener(new MyTextWatcher(txtPassowrd));

        wait_icon = (LinearLayout) findViewById(R.id.wait_icon);
        signlayout = (LinearLayout) findViewById(R.id.signlayout);

        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


         btnSubmit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 if (!submitForm()) {
                     Util.hideSoftKeyboard(SignInActivity.this, btnSubmit);
                    // final User user = new User();
                     String  username  =   txtEmailAddress.getText().toString();
                     String  password =  txtPassowrd.getText().toString();
                     Intent i = new Intent(getApplicationContext(), MapsActivity.class);


                     if(!Util.checkConnectivity(getApplicationContext())){

                          Toast.makeText(SignInActivity.this, Constants.Connections.IMTERNET_NOT_AVAILABLE, Toast.LENGTH_LONG).show();
                          return ;

                     }else{



                         signlayout.setVisibility(View.INVISIBLE);
                         wait_icon.setVisibility(View.VISIBLE);


                         BackEndDAO.signIn(username, password, new Callback() {
                             @Override
                             public void onFailure(Call call, IOException e) {
                                 e.printStackTrace();
                          //   subTitleTv.setTextColor(getResources().getColor(R.color.red));
//                                 txtInputLayEmail.getEditText().setText("");
//                                 txtInputLayPassword.getEditText().setText("");

                                 wait_icon.setVisibility(View.INVISIBLE);
                                  signlayout.setVisibility(View.VISIBLE);

                             }

                             @Override
                             public void onResponse(Call call, Response response) throws IOException {

                                 if (!response.isSuccessful()) {

                                     txtInputLayEmail.getEditText().setText("");
                                     txtInputLayPassword.getEditText().setText("");
                                     wait_icon.setVisibility(View.INVISIBLE);
                                     signlayout.setVisibility(View.VISIBLE);

                                 } else {
                                     String  responseBody  =  response.body().string();
                                     Log.d("TAG_TAG",responseBody);
                                     LoginResponse loginResponse = gson.fromJson(responseBody, LoginResponse.class);
                                     User user = loginResponse.getUser();
                                     mActivity.runOnUiThread(new Runnable() {
                                         @Override
                                         public void run() {
                                           //  subTitleTv.setText(loginResponse.getAuthInfo().getMessage());
                                             if (user.getToken() != null) {
                                                 String json = gson.toJson(user);
                                                 sharedData.Set(Constants.SharedDataCst.USER_KEY, json);
                                                 Intent i = new Intent(SignInActivity.this, MainActivity.class);     //MapsActivity.class); //MapsActivity.class);
                                                 startActivity(i);
                                                 //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                                 finish();

                                             } else {

                                                 Toast.makeText(mActivity, loginResponse.getAuthInfo().getMessage(), Toast.LENGTH_LONG).show();
                                                 txtInputLayEmail.getEditText().setText("");
                                                 txtInputLayPassword.getEditText().setText("");
                                                 wait_icon.setVisibility(View.INVISIBLE);
                                                 signlayout.setVisibility(View.VISIBLE);
                                             }
                                         }
                                     });

                                 }
                             }
                         });
                     }


                 }
             }
         });


    }




    private class MyTextWatcher implements TextWatcher {


                private View view;

                private MyTextWatcher(View view) {
                    this.view = view;
                }

                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                public void afterTextChanged(Editable editable) {
                    switch (view.getId()) {
                        case R.id.txtinputLayEmail:
                            validateEmail();
                            break;
                        case R.id.txtinputLayPwd:
                            validatePassword();
                            break;
//                case R.id.input_layout_confirm_password:
//                    validatePassword();
//                    break;

                    }
                }


            }
    private boolean submitForm () {
                boolean cancel = false;

                if (!validateEmail()) {
                    cancel = true;
                }

                if (!validatePassword()) {
                    cancel = true;
                }
                return cancel;
            }
    private boolean validateEmail () {
                String Email = txtEmailAddress.getText().toString().trim();
                if (Email.isEmpty() || !isValidEmail(Email)) {
                    inputLayoutEmailAddress.setError("Invalid Email");
                    requestFocus(txtEmailAddress);
                    return false;
                } else {
                    inputLayoutEmailAddress.setErrorEnabled(false);
                }
                return true;
            }
    private boolean validatePassword () {
                if (txtPassowrd.getText().toString().trim().isEmpty() || txtPassowrd.getText().toString().trim().length() < 6) {
                    inputLayoutPassword.setError("Password must be at least 6 characters long.");
                    requestFocus(txtPassowrd);
                    return false;
                } else {
                    inputLayoutPassword.setErrorEnabled(false);
                }

                return true;
            }
            private static boolean isValidEmail (String email){
                return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
            }
    private void requestFocus (View view){
                if (view.requestFocus()) {
                    this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }



}
