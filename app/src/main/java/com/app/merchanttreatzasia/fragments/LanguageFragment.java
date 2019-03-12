package com.app.merchanttreatzasia.fragments;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.app.merchanttreatzasia.R;
import com.app.merchanttreatzasia.entities.CurrencyRateEntity;
import com.app.merchanttreatzasia.entities.ResponseWrapper;
import com.app.merchanttreatzasia.fragments.abstracts.BaseFragment;
import com.app.merchanttreatzasia.global.AppConstants;
import com.app.merchanttreatzasia.global.WebServiceConstants;
import com.app.merchanttreatzasia.helpers.InternetHelper;
import com.app.merchanttreatzasia.helpers.UIHelper;
import com.app.merchanttreatzasia.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by khan_muhammad on 9/15/2017.
 */

public class LanguageFragment extends BaseFragment {


    @BindView(R.id.btnApply)
    Button btnApply;
    Unbinder unbinder;
    @BindView(R.id.cbEnglish)
    RadioButton cbEnglish;
    @BindView(R.id.cbIndonesian)
    RadioButton cbIndonesian;
    @BindView(R.id.cbMalai)
    RadioButton cbMalai;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;

    public static LanguageFragment newInstance() {
        return new LanguageFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_language, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

/*    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                if(checkedId == cbEnglish.getId()){
                    prefHelper.setLanguage(AppConstants.Eng);
                }
                else if(checkedId == cbMalai.getId()){
                    prefHelper.setLanguage(AppConstants.Malai);
                }
                else if(checkedId == cbIndonesian.getId()){
                    prefHelper.setLanguage(AppConstants.Indo);
                }
            }
        });

    }*/

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (prefHelper.getConvertedAmountCurrrency().equalsIgnoreCase(AppConstants.SGD)) {
            cbEnglish.setChecked(true);
        } else if (prefHelper.getConvertedAmountCurrrency().equalsIgnoreCase(AppConstants.IDR)) {
            cbIndonesian.setChecked(true);
        } else if (prefHelper.getConvertedAmountCurrrency().equalsIgnoreCase(AppConstants.MYR)) {
            cbMalai.setChecked(true);
        } else if (prefHelper.getConvertedAmountCurrrency().equalsIgnoreCase("")) {
            cbEnglish.setChecked(true);
        }
    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.language));
    }




    @OnClick(R.id.btnApply)
    public void onViewClicked() {

        if (cbEnglish.isChecked()) {
            getCurrencyRateSGD();
        } else if (cbIndonesian.isChecked()) {
            getCurrencyRateIDR();
        } else if (cbMalai.isChecked()) {
            getCurrencyRateMYR();
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
                        getDockActivity().popBackStackTillEntry(0);
                        getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), HomeFragment.class.getName());
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
                        getDockActivity().popBackStackTillEntry(0);
                        getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), HomeFragment.class.getName());

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
                        getDockActivity().popBackStackTillEntry(0);
                        getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), HomeFragment.class.getName());
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
