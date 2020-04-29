package com.camsys.carmonic.utilities;

import android.app.Activity;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class FireabseAnalyticsLog {

    private FirebaseAnalytics mFirebaseAnalytics;

    public FireabseAnalyticsLog(Activity activity) {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(activity);
    }


    public void logEvent(String logItem, String className, String eventName) {
        Bundle bundle = new Bundle();
        bundle.putString("LOG", logItem);
        bundle.putString("CLASSNAME", className);
        bundle.putString("EVENT", eventName);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

    }
}
