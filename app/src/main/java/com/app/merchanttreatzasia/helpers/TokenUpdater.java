package com.app.merchanttreatzasia.helpers;

import android.content.Context;
import android.util.Log;


import com.app.merchanttreatzasia.entities.ResponseWrapper;
import com.app.merchanttreatzasia.global.WebServiceConstants;
import com.app.merchanttreatzasia.retrofit.WebService;
import com.app.merchanttreatzasia.retrofit.WebServiceFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TokenUpdater {
    private static final TokenUpdater tokenUpdater = new TokenUpdater();
    private WebService webservice;

    private TokenUpdater() {
    }

    public static TokenUpdater getInstance() {
        return tokenUpdater;
    }

    public void UpdateToken(Context context, String deviceToken, String DeviceType, final String token) {
        if (deviceToken.isEmpty()) {
            Log.e("Token Updater", "Token is Empty");
        }
        webservice = WebServiceFactory.getWebServiceInstanceWithCustomInterceptor(context,
                WebServiceConstants.Local_SERVICE_URL);

        Call<ResponseWrapper> call = webservice.updateToken(deviceToken,DeviceType, token);
        call.enqueue(new Callback<ResponseWrapper>() {
            @Override
            public void onResponse(Call<ResponseWrapper> call, Response<ResponseWrapper> response) {
                if (response.body()!=null)
                Log.i("UPDATETOKEN", response.body().getResponse() + "" + token);
            }

            @Override
            public void onFailure(Call<ResponseWrapper> call, Throwable t) {
                Log.e("UPDATETOKEN", t.toString());
            }
        });
    }

}
