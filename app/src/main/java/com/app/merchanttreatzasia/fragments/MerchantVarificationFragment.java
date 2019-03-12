package com.app.merchanttreatzasia.fragments;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.merchanttreatzasia.R;
import com.app.merchanttreatzasia.entities.EVocherDetailEnt;
import com.app.merchanttreatzasia.entities.MerchantRedeemHistoryEnt;
import com.app.merchanttreatzasia.fragments.abstracts.BaseFragment;
import com.app.merchanttreatzasia.global.AppConstants;
import com.app.merchanttreatzasia.global.Utils;
import com.app.merchanttreatzasia.global.UtilsGlobal;
import com.app.merchanttreatzasia.global.WebServiceConstants;
import com.app.merchanttreatzasia.helpers.DateHelper;
import com.app.merchanttreatzasia.ui.views.AnyEditTextView;
import com.app.merchanttreatzasia.ui.views.AnyTextView;
import com.app.merchanttreatzasia.ui.views.TitleBar;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MerchantVarificationFragment extends BaseFragment {

    @BindView(R.id.ivDeal)
    ImageView ivDeal;
    @BindView(R.id.txtFlatePercents)
    AnyTextView txtFlatePercents;
    @BindView(R.id.txtCompanyName)
    AnyTextView txtCompanyName;
    @BindView(R.id.txtTotalRate)
    AnyTextView txtTotalRate;
    @BindView(R.id.txtRemainingRate)
    AnyTextView txtRemainingRate;
    @BindView(R.id.txtExpiresOn)
    AnyTextView txtExpiresOn;
    @BindView(R.id.txtExpiresDateValue)
    AnyTextView txtExpiresDateValue;
    @BindView(R.id.txtAddress)
    AnyTextView txtAddress;
    @BindView(R.id.btnRescan)
    Button btnRescan;
    @BindView(R.id.edtMerchantId)
    AnyEditTextView edtMerchantId;
    @BindView(R.id.llMainFrame)
    LinearLayout llMainFrame;
    @BindView(R.id.btnNext)
    Button btnNext;
    Unbinder unbinder;
    Float updatedTotal;
    Float updatedDiscounted;

    String barCode = "";
    @BindView(R.id.txtPaidType)
    AnyTextView txtPaidType;

    private ImageLoader imageLoader;

    EVocherDetailEnt eVocherDetailEnt;
    MerchantRedeemHistoryEnt merchantRedeemHistoryEnt;

    public static MerchantVarificationFragment newInstance() {
        return new MerchantVarificationFragment();
    }

    public static MerchantVarificationFragment newInstance(String displayValue) {
        Bundle args = new Bundle();

        MerchantVarificationFragment fragment = new MerchantVarificationFragment();
        args.putString(AppConstants.KEY_Barcode_Value, displayValue);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String code = getArguments().getString(AppConstants.KEY_Barcode_Value);
        if (code != null) {
            barCode = code;
        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.merchant_varification));
        titleBar.showNotificationButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDockActivity().replaceDockableFragment(NotificationsFragment.newInstance(), "NotificationsFragment");
            }
        }, prefHelper.getNotificationCount());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_merchant_varification, container, false);

        imageLoader = ImageLoader.getInstance();

        unbinder = ButterKnife.bind(this, view);

        serviceHelper.enqueueCall(webService.getMerchantEvoucherDetail(barCode, prefHelper.getMerchant().getToken()), WebServiceConstants.evaucherdetailBarcode);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        llMainFrame.setVisibility(View.GONE);
        txtTotalRate.setPaintFlags(txtTotalRate.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    public void setData(EVocherDetailEnt eVocherDetailEnt) {

        imageLoader.displayImage(eVocherDetailEnt.getEvoucherDetail().getProductDetail().getProductImage(), ivDeal);
        if (eVocherDetailEnt.getEvoucherDetail().getType().equals(AppConstants.cash_voucher)) {
            txtPaidType.setText(R.string.type_cash_voucher);
        } else if (eVocherDetailEnt.getEvoucherDetail().getType().equals(AppConstants.discount_coupon)) {
            txtPaidType.setText(R.string.type_discount_coupon);
        } else if (eVocherDetailEnt.getEvoucherDetail().getType().equals(AppConstants.promo_code)) {
            txtPaidType.setText(R.string.type_promo_code);
        }

        //txtPaidType.setText(Utils.getVoucherType(eVocherDetailEnt.getEvoucherDetail().getType(),getMainActivity()));
        //txtFlatePercents.setText(getString(R.string.flat) + " " + eVocherDetailEnt.getEvoucherDetail().getAmount() + getString(R.string.ofon));

        if (prefHelper.getLanguage().length() == 0 || prefHelper.getLanguage().equalsIgnoreCase(AppConstants.Eng))
            txtFlatePercents.setText(eVocherDetailEnt.getEvoucherDetail().getTitle());
        else if (prefHelper.getLanguage().equalsIgnoreCase(AppConstants.Malai))
            txtFlatePercents.setText(eVocherDetailEnt.getEvoucherDetail().getMaTitle());
        else if (prefHelper.getLanguage().equalsIgnoreCase(AppConstants.Indo))
            txtFlatePercents.setText(eVocherDetailEnt.getEvoucherDetail().getInTitle());

        txtTotalRate.setText("$" + eVocherDetailEnt.getEvoucherDetail().getProductDetail().getPrice());
        txtRemainingRate.setText(Utils.getRemainingAmount(Integer.parseInt(eVocherDetailEnt.getEvoucherDetail().getAmount()), Integer.parseInt(eVocherDetailEnt.getEvoucherDetail().getProductDetail().getPrice())));

        updatedTotal = Float.valueOf(eVocherDetailEnt.getEvoucherDetail().getProductDetail().getPrice()) * prefHelper.getConvertedAmount();
        String formattedValuePriceTotal = String.format("%.2f", updatedTotal);
        txtTotalRate.setText(prefHelper.getConvertedAmountCurrrency() + " " + formattedValuePriceTotal + "");

        updatedDiscounted = Float.valueOf(UtilsGlobal.getRemainingAmountWithoutDollar(Integer.parseInt(eVocherDetailEnt.getEvoucherDetail().getAmount()), Integer.parseInt(eVocherDetailEnt.getEvoucherDetail().getProductDetail().getPrice()))) * prefHelper.getConvertedAmount();
        String formattedValuePriceDiscounted = String.format("%.2f", updatedDiscounted);
        txtRemainingRate.setText(prefHelper.getConvertedAmountCurrrency() + " " + formattedValuePriceDiscounted + "");

        txtExpiresDateValue.setText(DateHelper.dateFormat(eVocherDetailEnt.getEvoucherDetail().getExpiryDate(), DateHelper.DATE_FORMAT2, DateHelper.DATE_FORMAT));
        txtAddress.setText(eVocherDetailEnt.getEvoucherDetail().getLocation());
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.evaucherdetailBarcode:
                llMainFrame.setVisibility(View.VISIBLE);
                eVocherDetailEnt = (EVocherDetailEnt) result;
                if (eVocherDetailEnt != null)
                    setData(eVocherDetailEnt);
                break;

            case WebServiceConstants.merchantdasswordcheck:
                if (eVocherDetailEnt != null)
                    getDockActivity().replaceDockableFragment(RedeemCouponFragment.newInstance(eVocherDetailEnt), "RedeemCouponFragment");
                break;
        }
    }


    private boolean validateFields() {
        return edtMerchantId.testValidity();
    }

    @OnClick({R.id.btnRescan, R.id.btnNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.btnRescan:
                //// TODO: 9/16/2017 setOnBackListener
                getDockActivity().popFragment();
                getDockActivity().addDockableFragment(ScanIDFragment.newInstance(), "ScanIDFragment");
                break;

            case R.id.btnNext:

                if (validateFields())
                    serviceHelper.enqueueCall(webService.merchantVerification(edtMerchantId.getText().toString(), prefHelper.getMerchant().getToken()), WebServiceConstants.merchantdasswordcheck);
                break;
        }
    }
}
