package com.camsys.carmonic.payment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import com.camsys.carmonic.utilities.DatePickerFragment;
import com.camsys.carmonic.utilities.FourDigitCardFormatWatcher;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.camsys.carmonic.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import co.paystack.android.Paystack;
import co.paystack.android.PaystackSdk;
import co.paystack.android.Transaction;
import co.paystack.android.model.Card;
import co.paystack.android.model.Charge;

public class AddPayment extends AppCompatActivity {

    Context mContext = null;
    private Toolbar toolbar = null;
    public static final long FiveYearsInMillis = 157766400000L;
    TextInputEditText txtCardPan;
    TextInputEditText txtCVV;
    TextInputEditText txtExpDate;
    TextInputEditText txtPIN;
    TextInputLayout inputLayoutPIN = null;
    TextInputLayout inputLayoutCardPan= null;
    TextInputLayout inputLayoutExpDate = null;
    TextInputLayout inputLayoutCVV= null;
    private int expiryMonth;
    private int expiryYear;
    private static final char space = ' ';
    private DatePickerFragment expiryDateFragment;

    AppCompatButton btnSendCardDetail  ;
    View focusView = null;
    LinearLayout cardLayout ;
    LinearLayout waitIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        mContext = AddPayment.this;

        cardLayout = (LinearLayout) findViewById(R.id.cardLayout);
        waitIcon = (LinearLayout) findViewById(R.id.wait_icon);

        cardLayout.setVisibility(View.VISIBLE);
        waitIcon.setVisibility(View.GONE);

        TextView txtTitle = (TextView) toolbar.findViewById(R.id.txtTitle);
        txtTitle.setText("Add Card");


        inputLayoutPIN = findViewById(R.id.txtLayoutPIN);
        Typeface tfPwd = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf");
        inputLayoutPIN.setTypeface(tfPwd);

        txtPIN = findViewById(R.id.txtPIN);
        Typeface tfEditPwd = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf");
        txtPIN.setTypeface(tfEditPwd);


        inputLayoutCardPan = findViewById(R.id.txtLayoutCardPan);
        Typeface tfCardPan = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf");
        inputLayoutPIN.setTypeface(tfCardPan);

         txtCardPan = findViewById(R.id.txtCardPan);
        Typeface tfEditCardPan = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf");
        txtCardPan.setTypeface(tfEditCardPan);


        inputLayoutExpDate = findViewById(R.id.txtLayoutExpDate);
        Typeface tfExpireDate = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf");
        inputLayoutExpDate.setTypeface(tfExpireDate);

        txtExpDate = findViewById(R.id.txtExpDate);
        Typeface tfEditExpDate = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf");
        txtExpDate.setTypeface(tfEditExpDate);


        inputLayoutCVV = findViewById(R.id.txtLayoutCVV);
        Typeface tfCVV = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf");
        inputLayoutCVV.setTypeface(tfCVV);

         txtCVV = findViewById(R.id.txtPIN);
        Typeface tfEditCVV = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf");
        txtCVV.setTypeface(tfEditCVV);

        FourDigitCardFormatWatcher fourDigit = new FourDigitCardFormatWatcher();
        txtCardPan.addTextChangedListener(fourDigit);

        btnSendCardDetail = (AppCompatButton) findViewById(R.id.btnNext);

        txtExpDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (expiryDateFragment == null) {
                    expiryDateFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            expiryMonth = monthOfYear + 1; //values start from 0, so add 1 to levelup
                            expiryYear = year;
                            txtExpDate.setText(getString(R.string._card_expiry_format, expiryMonth, expiryYear));
                        }
                    }, null, null, null);

                    expiryDateFragment.setCallback(new DatePickerFragment.DatePickerCallback() {
                        @Override
                        public void onCreateDialog(DatePickerDialog dialog) {
                            DatePicker picker = dialog.getDatePicker();
                            //minimum date should be today
                            picker.setMinDate(System.currentTimeMillis() - 10000); //subtract seconds to be safe
                            //max date shouldn't be more than 3-5 years from now. let's set five to be safe
                            picker.setMaxDate(System.currentTimeMillis() + FiveYearsInMillis);
                            dialog.setTitle(null);
                            expiryDateFragment.attemptDayVisibilityChangeInPicker(View.GONE);
                        }
                    });
                    expiryDateFragment.show(getSupportFragmentManager(), "expiryDatePicker");
                } else {
                    try {
                        DatePickerDialog dialog = expiryDateFragment.getDatePickerDialog();
                        dialog.updateDate(expiryYear, expiryMonth, 0);
                        //do this to clear the title
                        dialog.setTitle(null);
                    } catch (Exception e) {

                    }
                    expiryDateFragment.getDatePickerDialog().show();
                }
            }
        });

        txtExpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expiryDateFragment == null) {
                    expiryDateFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            expiryMonth = monthOfYear + 1; //values start from 0, so add 1 to levelup
                            expiryYear = year;


                            txtExpDate.setText(getString(R.string._card_expiry_format, expiryMonth, expiryYear));
                        }
                    }, null, null, null);

                    expiryDateFragment.setCallback(new DatePickerFragment.DatePickerCallback() {
                        @Override
                        public void onCreateDialog(DatePickerDialog dialog) {
                            DatePicker picker = dialog.getDatePicker();
                            //minimum date should be today
                            picker.setMinDate(System.currentTimeMillis() - 10000); //subtract seconds to be safe
                            //max date shouldn't be more than 3-5 years from now. let's set five to be safe
                            picker.setMaxDate(System.currentTimeMillis() + FiveYearsInMillis);
                            dialog.setTitle(null);
                            expiryDateFragment.attemptDayVisibilityChangeInPicker(View.GONE);
                        }
                    });
                    expiryDateFragment.show(getSupportFragmentManager(), "expiryDatePicker");
                } else {
                    try {
                        DatePickerDialog dialog = expiryDateFragment.getDatePickerDialog();
                        dialog.updateDate(expiryYear, expiryMonth, 0);
                        //do this to clear the title
                        dialog.setTitle(null);
                    } catch (Exception e) {

                    }
                    expiryDateFragment.getDatePickerDialog().show();
                }
            }
        });


        btnSendCardDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("Date:: ", expiryMonth + "" + expiryYear);
                Log.i("Date:: ", txtCardPan.getText().toString().replace(" ", ""));

               Card card = new Card.Builder(txtCardPan.getText().toString().replace(" ", ""), expiryMonth, expiryYear, txtCVV.getText().toString()).build();
                if (!card.validNumber()) {
                    //  Dialogs.showMessage2(context, "Card Number is invalid", Constants.TITLE);
                    txtCardPan.setError("Card Pan is invalid.");
                    focusView = txtCardPan;
                    return;
                } else if (!card.validCVC()) {
                    txtCVV.setError("CVV is invalid.");
                    focusView = txtCVV;
                    return;
                } else if (!card.validExpiryDate()) {
                    txtExpDate.setError("Expiry Date is invalid.");
                    focusView = txtExpDate;
                    return;
                } else {
                    if (card.isValid()) {
                        cardLayout.setVisibility(View.GONE);
                        waitIcon.setVisibility(View.VISIBLE);
                        performCharge(card);

                    }
                }
            }
        });


    }


    public void performCharge(Card card){
        //create a Charge object
        Charge charge = new Charge();
        charge.setCard(card); //sets the card to charge

        PaystackSdk.chargeCard(AddPayment.this, charge, new Paystack.TransactionCallback() {
            @Override
            public void onSuccess(Transaction transaction) {
                cardLayout.setVisibility(View.GONE);
                waitIcon.setVisibility(View.VISIBLE);
                String paymentReference = transaction.getReference();
                System.out.println("PaymentReference ::: " + paymentReference);
                Toast.makeText(mContext, transaction.getReference(), Toast.LENGTH_LONG).show();

            }
            @Override
            public void beforeValidate(Transaction transaction) {
                cardLayout.setVisibility(View.GONE);
                waitIcon.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(Throwable error, Transaction transaction) {
                //handle error here
                cardLayout.setVisibility(View.GONE);
                waitIcon.setVisibility(View.VISIBLE);
                Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

}
