package com.app.merchanttreatzasia.helpers;

import android.util.Log;
import android.view.View;

import com.app.merchanttreatzasia.R;
import com.app.merchanttreatzasia.activities.DockActivity;
import com.app.merchanttreatzasia.entities.ResponseWrapper;
import com.app.merchanttreatzasia.fragments.HomeFragment;
import com.app.merchanttreatzasia.fragments.ScanIDFragment;
import com.app.merchanttreatzasia.global.WebServiceConstants;
import com.app.merchanttreatzasia.interfaces.webServiceResponseLisener;
import com.app.merchanttreatzasia.retrofit.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created on 7/17/2017.
 */

public class ServiceHelper<T> {
    private webServiceResponseLisener serviceResponseLisener;
    private DockActivity context;
    private WebService webService;

    public ServiceHelper(webServiceResponseLisener serviceResponseLisener, DockActivity conttext, WebService webService) {
        this.serviceResponseLisener = serviceResponseLisener;
        this.context = conttext;
        this.webService = webService;
    }

    public void enqueueCall(Call<ResponseWrapper<T>> call, final String tag) {
        if (InternetHelper.CheckInternetConectivityandShowToast(context)) {
            context.onLoadingStarted();
            call.enqueue(new Callback<ResponseWrapper<T>>() {
                @Override
                public void onResponse(Call<ResponseWrapper<T>> call, Response<ResponseWrapper<T>> response) {
                    context.onLoadingFinished();
                    if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {
                        serviceResponseLisener.ResponseSuccess(response.body().getResult(), tag);
                    } else {
                        if (tag.equals(WebServiceConstants.evaucherdetailBarcode)) {

                            final DialogHelper dialogHelper = new DialogHelper(context);
                            dialogHelper.initRedeemedCouponDialog(R.layout.invalid_barcode_dailog,response.body().getMessage(), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialogHelper.hideDialog();
                                    context.popFragment();
                                }
                            });
                            dialogHelper.showDialog();
                        } else if (tag.equals(WebServiceConstants.evaucherdetail)) {
                            context.addDockableFragment(HomeFragment.newInstance(), "HomeFragment");
                            UIHelper.showShortToastInCenter(context, response.body().getMessage());
                        } else {
                            UIHelper.showShortToastInCenter(context, response.body().getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseWrapper<T>> call, Throwable t) {
                    context.onLoadingFinished();
                    t.printStackTrace();
                    Log.e(ServiceHelper.class.getSimpleName() + " by tag: " + tag, t.toString());
                }
            });
        }
    }
}
