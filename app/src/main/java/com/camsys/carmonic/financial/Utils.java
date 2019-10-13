package com.camsys.carmonic.financial;

import android.app.Activity;

import co.paystack.android.Paystack;
import co.paystack.android.PaystackSdk;
import co.paystack.android.model.Card;
import co.paystack.android.model.Charge;

public class Utils {

    public static void performCharge(Card card, String email, int amount, Activity activity, Paystack.TransactionCallback callback) {
        Charge charge = new Charge();
        charge.setCard(card);
        charge.setEmail(email);
        charge.setAmount(amount);

        PaystackSdk.chargeCard(activity, charge, callback);
    }
}
