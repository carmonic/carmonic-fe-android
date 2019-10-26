package com.camsys.carmonic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

import com.camsys.carmonic.constants.Constants;
import com.camsys.carmonic.financial.Utils;
import com.camsys.carmonic.networking.BackEndDAO;
import com.camsys.carmonic.financial.Bill;
import com.camsys.carmonic.principals.User;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

import co.paystack.android.PaystackSdk;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BillingActivity extends AppCompatActivity {

    User user;
    int mechanicId;
    int starRating = 5;
    String feedback;
    String compliment;
    Bill bill;
    int total = 0;

    SeekBar seekBar;
    TextView starRatingValue;
    private ConstraintLayout billConstraintLayout;
    private ConstraintSet set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PaystackSdk.initialize(getApplicationContext());
        setContentView(R.layout.activity_billing_main);
        starRatingValue = findViewById(R.id.starRatingValue);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(seekBarChangeListener);

        mechanicId = getIntent().getIntExtra("mechanicId", -1);
        feedback = getIntent().getStringExtra("feedback");
        compliment = getIntent().getStringExtra("compliment");
        bill = (Bill) getIntent().getSerializableExtra("bill");

        billConstraintLayout = findViewById(R.id.topconstraint);
        set = new ConstraintSet();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        user = gson.fromJson(preferences.getString("User", ""), User.class);

        updateConstraintLayoutWithBill();
        BackEndDAO.charge(String.valueOf(total * 100), user.getEmail(), user.getToken(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            //ToDo: Show message saying charge was not successful
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                BillingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateConstraintLayoutWithDeductionMessage(total);
                    }
                });
            }
        });
    }

    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // updated continuously as the user slides the thumb
            starRating = progress;
            BillingActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    starRatingValue.setText(String.valueOf(progress));
                }
            });
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    public void onclick_bill_done(View view) {
        BackEndDAO.postFeedback(starRating, compliment, feedback, String.valueOf(user.getId()), String.valueOf(mechanicId), user.getToken(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Intent i = new Intent(getApplicationContext(), MapsActivityWithLocationConfirmed.class);
                startActivity(i);
            }
        });
    }

    public void onclick_edit_complmt(View view) {
        Intent i = new Intent(getApplicationContext(), FeedbackActivity.class);
        i.putExtra("mechanicId", mechanicId);
        startActivity(i);
    }

    private void updateConstraintLayoutWithBill() {
        Field[] fields = bill.getClass().getDeclaredFields();

        for(int i=0; i < fields.length; i++) {
            Field field = fields[i];
            try {
                if (field.getType().isPrimitive() && field.getBoolean(bill)) {
                    int price = getPriceForBillingOption(field.getName());
                    total += price;
                    addEntryToBillConstraintLayout(getNameForBillOption(field.getName()), price, i);
                } else if (field.getName().equals("other")) {
                    Map<String, String> value = (Map<String, String>) field.get(bill);
                    Set<Map.Entry<String, String>> services = value.entrySet();
                    for (Map.Entry<String, String> service : services) {
                        try {
                            int price = Integer.valueOf(service.getValue());
                            total += price;
                            addEntryToBillConstraintLayout(service.getKey(), price, i);
                        } catch (NumberFormatException e) {
                            continue;
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                continue;
            }
        }

        addEntryToBillConstraintLayout("Total", total, fields.length);
    }

    private void updateConstraintLayoutWithDeductionMessage(int total) {
        Resources res = getResources();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String cardLastFourDigits = preferences.getString("cardNumberLastFour", "");

        TextView deductionMessageTextView = new TextView(this);
        deductionMessageTextView.setId(View.generateViewId());
        deductionMessageTextView.setText(String.format(res.getString(R.string.billing_total_amount_msg), total, cardLastFourDigits));
        deductionMessageTextView.setTextColor(Color.parseColor("#EDD170"));
        deductionMessageTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14f);
        Typeface priceTypeface = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf");
        deductionMessageTextView.setTypeface(priceTypeface);
        billConstraintLayout.addView(deductionMessageTextView, 0);

        set.clone(billConstraintLayout);
        set.connect(deductionMessageTextView.getId(), ConstraintSet.BOTTOM, billConstraintLayout.getId(), ConstraintSet.BOTTOM, 80);
        set.applyTo(billConstraintLayout);
    }

    private void addEntryToBillConstraintLayout(String service, int price, int index) {
        int offset = 150;
        int space = 40;
        Resources res = getResources();

        TextView priceTextView = new TextView(this);
        priceTextView.setId(View.generateViewId());
        priceTextView.setText(String.format(res.getString(R.string.price), price));
        priceTextView.setTextColor(Color.parseColor("#B9B9B9"));
        priceTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f);
        Typeface priceTypeface = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf");
        priceTextView.setTypeface(priceTypeface, Typeface.BOLD);
        billConstraintLayout.addView(priceTextView, 0);

        TextView serviceTextView = new TextView(this);
        serviceTextView.setId(View.generateViewId());
        serviceTextView.setText(String.format(res.getString(R.string.service), service));
        serviceTextView.setTextColor(Color.parseColor("#B9B9B9"));
        serviceTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/GlacialIndifferenceRegular.ttf");
        serviceTextView.setTypeface(face, Typeface.BOLD);
        billConstraintLayout.addView(serviceTextView, 0);

        set.clone(billConstraintLayout);
        set.connect(serviceTextView.getId(), ConstraintSet.TOP, billConstraintLayout.getId(), ConstraintSet.TOP, offset + space * index);

        set.connect(priceTextView.getId(), ConstraintSet.TOP, billConstraintLayout.getId(), ConstraintSet.TOP, offset + space * index);
        set.connect(priceTextView.getId(), ConstraintSet.LEFT, billConstraintLayout.getId(), ConstraintSet.LEFT, 600);
        set.applyTo(billConstraintLayout);
    }

    private String getNameForBillOption(String option) {
        switch (option) {
            case "changeTyre" : return Constants.ServiceName.CHANGE_TYRE;
            case "fixEngine" : return Constants.ServiceName.FIX_ENGINE;
            case "changeWindshield" : return Constants.ServiceName.CHANGE_WINDSHIELD;
        }
        return "";
    }

    private int getPriceForBillingOption(String option) {
        switch (option) {
            case "changeTyre" : return Constants.Prices.CHANGE_TYRE;
            case "fixEngine" : return Constants.Prices.FIX_ENGINE;
            case "changeWindshield" : return Constants.Prices.CHANGE_WINDSHIELD;
        }
        return 0;
    }
}
