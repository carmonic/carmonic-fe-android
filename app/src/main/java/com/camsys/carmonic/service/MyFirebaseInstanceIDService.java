package com.camsys.carmonic.service;//package com.camsys.carmonic.mechanic.Service;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.camsys.carmonic.MapsActivity;
import com.camsys.carmonic.R;
import com.camsys.carmonic.constants.Constants;
import com.camsys.carmonic.onboarding.MainActivity;
import com.camsys.carmonic.utilities.NotificationUtil;
import com.camsys.carmonic.utilities.SharedData;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;


public class MyFirebaseInstanceIDService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();
    public NotificationUtil notificationUtils;


    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);

        storeRegIdInPref(token);
        // sending reg id to your server
        sendRegistrationToServer(token);
        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(Constants.SetAction.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", token);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void sendRegistrationToServer(final String token) {
        // sending gcm token to server
        Log.e(TAG, "sendRegistrationToServer: " + token);
    }

    private void storeRegIdInPref(String token) {
        SharedData sharedData = new SharedData(getApplicationContext());
        sharedData.Set(Constants.SharedDataCst.FCM_REG_TOKEN, token);
    }

    private static void generateNotification(Context context, String message) {
        int icon = R.mipmap.ic_launcher;
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(icon, message, when);
        String title = context.getString(R.string.app_name);
        Intent notificationIntent = new Intent(context, MainActivity.class);  //work  arround it
        // set intent so it does not start a new activity
        notificationIntent.setAction(Intent.ACTION_MAIN);
        notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        // notification.setLatestEventInfo(context, title, message, intent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        // Play default notification sound
        notification.defaults |= Notification.DEFAULT_SOUND;
        // notification.sound = Uri.parse("android.resource://" + context.getPackageName() + "your_sound_file_name.mp3");
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);//.TYPE_ALARM);//.TYPE_NOTIFICATION);
        //Vibrate if vibrate is enabled
        notification.sound = alarmSound;
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(0, notification);
    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());

            // generateNotification(getApplicationContext(),remoteMessage.getNotification().getBody());

        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);

            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void handleNotification(String message) {
        if (!NotificationUtil.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Constants.SetAction.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
        }
    }


    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());
        String message = "";
        String Condition;
        String others;
        try {
            //JSONObject data = json.getJSONObject("data");

            Condition = json.getString("Condition");

            if ("TripReq".contains(Condition)) {

                Log.e(TAG, "TripId TripId: " + json.getString("TripId"));


                Log.e(TAG, "TripId TripId: " + json.getString("TripId"));

                String tripId = json.getString("TripId");
                String RideTypeId = json.getString("RideTypeId");

                double latPoint = json.getDouble("Lat");
                double longPoint = json.getDouble("Long");


                message = "You have a trip Request.";
                Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                resultIntent.putExtra("message", message);
                resultIntent.putExtra("tripId", tripId);
                resultIntent.putExtra("RideTypeId", RideTypeId);
                resultIntent.putExtra("Condition", Condition);
                resultIntent.putExtra("latPoint", latPoint);
                resultIntent.putExtra("longPoint", longPoint);
                showNotificationMessage(getApplicationContext(), getApplicationContext().getString(R.string.app_name), message, "", resultIntent);
                NotificationUtil notificationUtils = new NotificationUtil(getApplicationContext(), "");
                notificationUtils.playNotificationSound();

            } else if ("TripAccept".contains(Condition)) {


                Log.e(TAG, "TripId TripId: " + json.getString("TripId"));

                String tripId = json.getString("TripId");


                message = "Driver Accept";
                Intent resultIntent = new Intent(getApplicationContext(), MapsActivity.class);
                resultIntent.putExtra("message", message);
                resultIntent.putExtra("tripId", tripId);
                showNotificationMessage(getApplicationContext(), getApplicationContext().getString(R.string.app_name), message, "", resultIntent);
                NotificationUtil notificationUtils = new NotificationUtil(getApplicationContext(), "");
                notificationUtils.playNotificationSound();

            } else if ("tripCancel".contains(Condition)) {


            } else if ("RiderLoc".contains(Condition)) {


            }

        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }


    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtil(context, "");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, "");
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtil(context, "");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, imageUrl);
    }
}

