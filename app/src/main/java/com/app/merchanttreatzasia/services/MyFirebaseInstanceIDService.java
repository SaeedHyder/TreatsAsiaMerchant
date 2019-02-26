package com.app.merchanttreatzasia.services;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.app.merchanttreatzasia.global.AppConstants;
import com.app.merchanttreatzasia.helpers.BasePreferenceHelper;
import com.app.merchanttreatzasia.helpers.TokenUpdater;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

    protected BasePreferenceHelper preferenceHelper;

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        preferenceHelper = new BasePreferenceHelper(getApplicationContext());
        // Saving reg id to shared preferences
        storeRegIdInPref(refreshedToken);

        // sending reg id to your server
        sendRegistrationToServer(refreshedToken);

        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(AppConstants.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void sendRegistrationToServer(final String token) {
        // sending gcm token to server
        Log.e(TAG, "sendRegistrationToServer: " + token);
        if (preferenceHelper.getMerchant() != null && preferenceHelper.getMerchant().getToken() != null)
            TokenUpdater.getInstance().UpdateToken(getApplicationContext(),
                    token,
                    AppConstants.Device_Type,
                    preferenceHelper.getMerchant().getToken());
    }


    private void storeRegIdInPref(String token) {

        preferenceHelper.setFirebase_TOKEN(token);
    }
}
