package com.app.merchanttreatzasia.fragments;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.app.merchanttreatzasia.R;
import com.app.merchanttreatzasia.entities.EVocherDetailEnt;
import com.app.merchanttreatzasia.fragments.abstracts.BaseFragment;
import com.app.merchanttreatzasia.global.AppConstants;
import com.app.merchanttreatzasia.global.Utils;
import com.app.merchanttreatzasia.global.UtilsGlobal;
import com.app.merchanttreatzasia.global.WebServiceConstants;
import com.app.merchanttreatzasia.helpers.DateHelper;
import com.app.merchanttreatzasia.helpers.DialogHelper;
import com.app.merchanttreatzasia.helpers.UIHelper;
import com.app.merchanttreatzasia.ui.views.AnyEditTextView;
import com.app.merchanttreatzasia.ui.views.AnyTextView;
import com.app.merchanttreatzasia.ui.views.TitleBar;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RedeemCouponFragment extends BaseFragment {

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
    @BindView(R.id.edtAmount)
    AnyEditTextView edtAmount;
    @BindView(R.id.btnNext)
    Button btnNext;
    Unbinder unbinder;
    @BindView(R.id.txtPaidType)
    AnyTextView txtPaidType;
    Float updatedTotal;
    Float updatedDiscounted;
    Float updatedCheckingAmount;

    private ImageLoader imageLoader;
    EVocherDetailEnt eVocherDetailEnt;

    public static RedeemCouponFragment newInstance(EVocherDetailEnt eVocherDetailEnt) {

        Bundle args = new Bundle();
        args.putString(AppConstants.EVocherDetailEnt, new Gson().toJson(eVocherDetailEnt));
        RedeemCouponFragment redeemCouponFragment = new RedeemCouponFragment();
        redeemCouponFragment.setArguments(args);

        return redeemCouponFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String jsonString = getArguments().getString(AppConstants.EVocherDetailEnt);
        if (jsonString != null) {
            eVocherDetailEnt = new Gson().fromJson(jsonString, EVocherDetailEnt.class);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_redeem_coupon, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageLoader = ImageLoader.getInstance();

        txtTotalRate.setPaintFlags(txtTotalRate.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        setData(eVocherDetailEnt);
    }

    public void setData(EVocherDetailEnt eVocherDetailEnt) {

        imageLoader.displayImage(eVocherDetailEnt.getEvoucherDetail().getProductDetail().getProductImage(), ivDeal);
        //txtPaidType.setText(Utils.getVoucherType(eVocherDetailEnt.getEvoucherDetail().getType(),getMainActivity()));
        //txtFlatePercents.setText(getString(R.string.flat) + " " + eVocherDetailEnt.getEvoucherDetail().getAmount() + getString(R.string.ofon));

        if (eVocherDetailEnt.getEvoucherDetail().getType().equals(AppConstants.cash_voucher)){
            txtPaidType.setText("Cash Voucher");
        } else if (eVocherDetailEnt.getEvoucherDetail().getType().equals(AppConstants.discount_coupon)){
            txtPaidType.setText("Discount Coupon");
        } else if (eVocherDetailEnt.getEvoucherDetail().getType().equals(AppConstants.promo_code)){
            txtPaidType.setText("Promo Code");
        }

        if(prefHelper.getLanguage().length() == 0 || prefHelper.getLanguage().equalsIgnoreCase(AppConstants.Eng))
            txtFlatePercents.setText(eVocherDetailEnt.getEvoucherDetail().getTitle());
        else if(prefHelper.getLanguage().equalsIgnoreCase(AppConstants.Malai))
            txtFlatePercents.setText(eVocherDetailEnt.getEvoucherDetail().getMaTitle());
        else if(prefHelper.getLanguage().equalsIgnoreCase(AppConstants.Indo))
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
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.redeem_coupon));
        titleBar.showNotificationButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDockActivity().replaceDockableFragment(NotificationsFragment.newInstance(), "NotificationsFragment");
            }
        },prefHelper.getNotificationCount());
    }



    private boolean validateFields() {
        return edtAmount.testValidity();
    }

    @OnClick(R.id.btnNext)
    public void onViewClicked() {

        if (validateFields()) {
            //serviceHelper.enqueueCall(webService.couponRedeem(eVocherDetailEnt.getUserId()+"",eVocherDetailEnt.getEvoucherId()+"",prefHelper.getMerchantId(), edtAmount.getText().toString()), WebServiceConstants.couponredeem);
            updatedCheckingAmount = Float.valueOf(Utils.getRemainingAmountForRedeemCoupon(Integer.parseInt(eVocherDetailEnt.getEvoucherDetail().getAmount()), Integer.parseInt(eVocherDetailEnt.getEvoucherDetail().getProductDetail().getPrice()))) * prefHelper.getConvertedAmount();
            String formattedValuePriceTotal3 = String.format("%.2f", updatedCheckingAmount);

                String actualAmount=Utils.getRemainingAmountForRedeemCoupon(Integer.parseInt(eVocherDetailEnt.getEvoucherDetail().getAmount()), Integer.parseInt(eVocherDetailEnt.getEvoucherDetail().getProductDetail().getPrice()));
            if (formattedValuePriceTotal3.equals(edtAmount.getText().toString())) {
              /*  serviceHelper.enqueueCall(webService.couponRedeem(eVocherDetailEnt.getEvoucherId() + "", eVocherDetailEnt.getUserId() + "",
                        eVocherDetailEnt.getQrCode(), edtAmount.getText().toString(),
                        prefHelper.getMerchant().getToken()), WebServiceConstants.couponredeem);*/
                serviceHelper.enqueueCall(webService.couponRedeem(eVocherDetailEnt.getEvoucherId() + "", eVocherDetailEnt.getUserId() + "",
                        eVocherDetailEnt.getQrCode(), actualAmount,
                        prefHelper.getMerchant().getToken()), WebServiceConstants.couponredeem);
            } else {
                UIHelper.showShortToastInCenter(getDockActivity(), "Please enter correct remaining amount.");
            }
        }
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.couponredeem:
                final DialogHelper dialogHelper = new DialogHelper(getMainActivity());
                dialogHelper.initRedeemedCouponDialog(R.layout.coupon_redeemed_dailog, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogHelper.hideDialog();
                        getDockActivity().popBackStackTillEntry(0);
                        getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), "HomeFragment");
                    }
                });

                dialogHelper.showDialog();
                break;
        }
    }
}
