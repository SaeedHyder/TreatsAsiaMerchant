package com.app.merchanttreatzasia.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.merchanttreatzasia.R;
import com.app.merchanttreatzasia.entities.CurrencyRateEntity;
import com.app.merchanttreatzasia.entities.ResponseWrapper;
import com.app.merchanttreatzasia.fragments.abstracts.BaseFragment;
import com.app.merchanttreatzasia.global.AppConstants;
import com.app.merchanttreatzasia.global.WebServiceConstants;
import com.app.merchanttreatzasia.helpers.DialogHelper;
import com.app.merchanttreatzasia.helpers.InternetHelper;
import com.app.merchanttreatzasia.helpers.UIHelper;
import com.app.merchanttreatzasia.ui.views.AnyTextView;
import com.app.merchanttreatzasia.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends BaseFragment {


    Unbinder unbinder;
    @BindView(R.id.txtWelcome)
    AnyTextView txtWelcome;
    @BindView(R.id.txtCompanyName)
    AnyTextView txtCompanyName;
    @BindView(R.id.ivScanCode)
    ImageView ivScanCode;
    @BindView(R.id.txtScanCode)
    AnyTextView txtScanCode;
    @BindView(R.id.llScanCode)
    LinearLayout llScanCode;
    @BindView(R.id.ivMyHistory)
    ImageView ivMyHistory;
    @BindView(R.id.txtMyHistory)
    AnyTextView txtMyHistory;
    @BindView(R.id.llMyHistory)
    LinearLayout llMyHistory;
    @BindView(R.id.ivProfile)
    ImageView ivProfile;
    @BindView(R.id.txtProfile)
    AnyTextView txtProfile;
    @BindView(R.id.llProfile)
    LinearLayout llProfile;
    @BindView(R.id.ivSettings)
    ImageView ivSettings;
    @BindView(R.id.txtSettings)
    AnyTextView txtSettings;
    @BindView(R.id.llSettings)
    LinearLayout llSettings;
    @BindView(R.id.ivLogout)
    ImageView ivLogout;
    @BindView(R.id.txtLogout)
    AnyTextView txtLogout;
    @BindView(R.id.llLogout)
    LinearLayout llLogout;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getCurrencyAndLanguage();
        txtCompanyName.setText(prefHelper.getMerchant().getFirstName());
    }

    private void getCurrencyAndLanguage() {

        if (prefHelper.getConvertedAmountCurrrency().equalsIgnoreCase(AppConstants.SGD)) {
            getCurrencyRateSGD();
        } else if (prefHelper.getConvertedAmountCurrrency().equalsIgnoreCase(AppConstants.IDR)) {
            getCurrencyRateIDR();
        } else if (prefHelper.getConvertedAmountCurrrency().equalsIgnoreCase(AppConstants.MYR)) {
            getCurrencyRateMYR();
        } else if (prefHelper.getConvertedAmountCurrrency().equalsIgnoreCase("")) {
            getCurrencyRateSGD();
        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading(getString(R.string.home));
        titleBar.showNotificationButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDockActivity().replaceDockableFragment(NotificationsFragment.newInstance(), "NotificationsFragment");
            }
        },prefHelper.getNotificationCount());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.llScanCode, R.id.llMyHistory, R.id.llProfile, R.id.llSettings, R.id.llLogout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llScanCode:
                getDockActivity().replaceDockableFragment(ScanIDFragment.newInstance(), "ScanIDFragment");
                break;

            case R.id.llMyHistory:
                getDockActivity().replaceDockableFragment(BranchesListFragment.newInstance(), "BranchesListFragment");
                break;

            case R.id.llProfile:
                getDockActivity().replaceDockableFragment(ProfileFragment.newInstance(), "ProfileFragment");
                break;

            case R.id.llSettings:
                getDockActivity().replaceDockableFragment(SettingFragment.newInstance(), "SettingFragment");
                break;

            case R.id.llLogout:

                final DialogHelper dialogHelper = new DialogHelper(getMainActivity());
                dialogHelper.initLogoutDialog(R.layout.logout_dailog, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logout();
                        dialogHelper.hideDialog();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogHelper.hideDialog();
                    }
                });

                dialogHelper.showDialog();

                break;
        }
    }

    private void logout() {
        serviceHelper.enqueueCall(webService.logout(prefHelper.getMerchant().getToken()), WebServiceConstants.logout);
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.logout:
                getDockActivity().popBackStackTillEntry(0);
                getDockActivity().addDockableFragment(LoginFragment.newInstance(), "LoginFragment");
                break;
        }
    }

    public void getCurrencyRateSGD() {
        if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
            loadingStarted();
            Call<ResponseWrapper<CurrencyRateEntity>> call = withoutHeaderWebService.getCurrencyRate(

                    AppConstants.USD,
                    AppConstants.SGD
            );

            call.enqueue(new Callback<ResponseWrapper<CurrencyRateEntity>>() {
                @Override
                public void onResponse(Call<ResponseWrapper<CurrencyRateEntity>> call, Response<ResponseWrapper<CurrencyRateEntity>> response) {
                    loadingFinished();
                    if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {

                        prefHelper.setConvertedAmount(response.body().getResult().getRate());
                        prefHelper.setConvertedAmountCurrency(AppConstants.SGD);

                    } else {
                        UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ResponseWrapper<CurrencyRateEntity>> call, Throwable t) {
                    loadingFinished();
                    UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());
                }
            });
        }
    }

    public void getCurrencyRateMYR() {
        if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
            loadingStarted();
            Call<ResponseWrapper<CurrencyRateEntity>> call = withoutHeaderWebService.getCurrencyRate(

                    AppConstants.USD,
                    AppConstants.MYR
            );

            call.enqueue(new Callback<ResponseWrapper<CurrencyRateEntity>>() {
                @Override
                public void onResponse(Call<ResponseWrapper<CurrencyRateEntity>> call, Response<ResponseWrapper<CurrencyRateEntity>> response) {
                    loadingFinished();
                    if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {

                        prefHelper.setConvertedAmount(response.body().getResult().getRate());
                        prefHelper.setConvertedAmountCurrency(AppConstants.MYR);

                    } else {
                        UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ResponseWrapper<CurrencyRateEntity>> call, Throwable t) {
                    loadingFinished();
                    UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());
                }
            });
        }
    }

    public void getCurrencyRateIDR() {
        if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
            loadingStarted();
            Call<ResponseWrapper<CurrencyRateEntity>> call = withoutHeaderWebService.getCurrencyRate(

                    AppConstants.USD,
                    AppConstants.IDR
            );

            call.enqueue(new Callback<ResponseWrapper<CurrencyRateEntity>>() {
                @Override
                public void onResponse(Call<ResponseWrapper<CurrencyRateEntity>> call, Response<ResponseWrapper<CurrencyRateEntity>> response) {
                    loadingFinished();
                    if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {

                        prefHelper.setConvertedAmount(response.body().getResult().getRate());
                        prefHelper.setConvertedAmountCurrency(AppConstants.IDR);

                    } else {
                        UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ResponseWrapper<CurrencyRateEntity>> call, Throwable t) {
                    loadingFinished();
                    UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());
                }
            });
        }
    }
}

