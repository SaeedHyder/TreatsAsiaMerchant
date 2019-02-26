package com.app.merchanttreatzasia.fragments;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.merchanttreatzasia.R;
import com.app.merchanttreatzasia.entities.MerchantRedeemHistoryEnt;
import com.app.merchanttreatzasia.fragments.abstracts.BaseFragment;
import com.app.merchanttreatzasia.global.AppConstants;
import com.app.merchanttreatzasia.global.Utils;
import com.app.merchanttreatzasia.global.UtilsGlobal;
import com.app.merchanttreatzasia.global.WebServiceConstants;
import com.app.merchanttreatzasia.helpers.DateHelper;
import com.app.merchanttreatzasia.ui.views.AnyTextView;
import com.app.merchanttreatzasia.ui.views.TitleBar;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RedemptionDetailsFragment extends BaseFragment {

    String eVoucherId = "";
    @BindView(R.id.ivDeal)
    ImageView ivDeal;
    @BindView(R.id.txtPaidType)
    AnyTextView txtPaidType;
    @BindView(R.id.txtFlatePercents)
    AnyTextView txtFlatePercents;
    @BindView(R.id.txtCompanyName)
    AnyTextView txtCompanyName;
    @BindView(R.id.txtTotalRate)
    AnyTextView txtTotalRate;
    @BindView(R.id.txtRemainingRate)
    AnyTextView txtRemainingRate;
    @BindView(R.id.txtRedemptionDate)
    AnyTextView txtRedemptionDate;
    @BindView(R.id.txtRedemptionDateValue)
    AnyTextView txtRedemptionDateValue;

    Unbinder unbinder;
    @BindView(R.id.txtUserIdValue)
    AnyTextView txtUserIdValue;
    @BindView(R.id.txtUserNameValue)
    AnyTextView txtUserNameValue;
    @BindView(R.id.txtRemainingValue)
    AnyTextView txtRemainingValue;
    @BindView(R.id.txtPoints)
    AnyTextView txtPoints;
    private static String redemptionDetailData = "redemptionDetailData";
    private MerchantRedeemHistoryEnt merchantRedeemHistoryEnt;
    private ImageLoader imageLoader;
    Float updatedTotal;
    Float updatedDiscounted;
    Float remaining;

/*    public static RedemptionDetailsFragment newInstance(String evoucherid) {
        Bundle args = new Bundle();
        RedemptionDetailsFragment fragment = new RedemptionDetailsFragment();
        args.putString(AppConstants.EVocherId, evoucherid);
        fragment.setArguments(args);
        return fragment;
    }*/

    public static RedemptionDetailsFragment newInstance() {
        return new RedemptionDetailsFragment();
    }

    public static RedemptionDetailsFragment newInstance(MerchantRedeemHistoryEnt entity) {
        Bundle args = new Bundle();
        RedemptionDetailsFragment fragment = new RedemptionDetailsFragment();
        args.putString(redemptionDetailData, new Gson().toJson(entity));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

/*        String EVocherId = getArguments().getString(AppConstants.EVocherId);
        if (EVocherId != null) {
            eVoucherId = EVocherId;
        }*/
        redemptionDetailData = getArguments().getString(redemptionDetailData);
        if (redemptionDetailData != null) {
            merchantRedeemHistoryEnt = new Gson().fromJson(redemptionDetailData, MerchantRedeemHistoryEnt.class);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_redemption_details, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageLoader = ImageLoader.getInstance();

        //serviceHelper.enqueueCall(webService.getMerchantRedeemDetail(eVoucherId, prefHelper.getMerchant().getToken()), WebServiceConstants.merchantredemptiondetail);

        setDataFromEntity();
    }

    private void setDataFromEntity() {
        imageLoader.displayImage(merchantRedeemHistoryEnt.getEvoucherDetail().getProductDetail().getProductImage(), ivDeal);
        //txtPaidType.setText(Utils.getVoucherType(merchantRedeemHistoryEnt.getEvoucherDetail().getType(),getMainActivity()));

        if (merchantRedeemHistoryEnt.getEvoucherDetail().getType().equals(AppConstants.cash_voucher)) {
            txtPaidType.setText("Cash Voucher");
        } else if (merchantRedeemHistoryEnt.getEvoucherDetail().getType().equals(AppConstants.discount_coupon)) {
            txtPaidType.setText("Discount Coupon");
        } else if (merchantRedeemHistoryEnt.getEvoucherDetail().getType().equals(AppConstants.promo_code)) {
            txtPaidType.setText("Promo Code");
        }
        //txtFlatePercents.setText(getString(R.string.flat) + " " + merchantRedemptionDetailEnt.getEvoucherDetail().getAmount() + getString(R.string.ofon));

        if (prefHelper.getLanguage().length() == 0 || prefHelper.getLanguage().equalsIgnoreCase(AppConstants.Eng))
            txtFlatePercents.setText(merchantRedeemHistoryEnt.getEvoucherDetail().getTitle());
        else if (prefHelper.getLanguage().equalsIgnoreCase(AppConstants.Malai))
            txtFlatePercents.setText(merchantRedeemHistoryEnt.getEvoucherDetail().getMaTitle());
        else if (prefHelper.getLanguage().equalsIgnoreCase(AppConstants.Indo))
            txtFlatePercents.setText(merchantRedeemHistoryEnt.getEvoucherDetail().getInTitle());

        txtTotalRate.setPaintFlags(txtTotalRate.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        txtTotalRate.setText("$" + merchantRedeemHistoryEnt.getEvoucherDetail().getProductDetail().getPrice());
        txtRemainingRate.setText(Utils.getRemainingAmount(Integer.parseInt(merchantRedeemHistoryEnt.getEvoucherDetail().getAmount()), Integer.parseInt(merchantRedeemHistoryEnt.getEvoucherDetail().getProductDetail().getPrice())));

        updatedTotal = Float.valueOf(merchantRedeemHistoryEnt.getEvoucherDetail().getProductDetail().getPrice()) * prefHelper.getConvertedAmount();
        String formattedValuePriceTotal = String.format("%.2f", updatedTotal);
        txtTotalRate.setText(prefHelper.getConvertedAmountCurrrency() + " " + formattedValuePriceTotal + "");

        updatedDiscounted = Float.valueOf(UtilsGlobal.getRemainingAmountWithoutDollar(Integer.parseInt(merchantRedeemHistoryEnt.getEvoucherDetail().getAmount()), Integer.parseInt(merchantRedeemHistoryEnt.getEvoucherDetail().getProductDetail().getPrice()))) * prefHelper.getConvertedAmount();
        String formattedValuePriceDiscounted = String.format("%.2f", updatedDiscounted);
        txtRemainingRate.setText(prefHelper.getConvertedAmountCurrrency() + " " + formattedValuePriceDiscounted + "");

        txtRedemptionDateValue.setText(DateHelper.dateFormat(merchantRedeemHistoryEnt.getUpdatedAt(), DateHelper.DATE_FORMAT2, DateHelper.DATE_TIME_FORMAT));

        txtUserIdValue.setText(merchantRedeemHistoryEnt.getUserId() + "");
        txtUserNameValue.setText(merchantRedeemHistoryEnt.getUserDetail().getFirstName() + " " + merchantRedeemHistoryEnt.getUserDetail().getLastName());
        txtRemainingValue.setText(prefHelper.getConvertedAmountCurrrency() + " " + merchantRedeemHistoryEnt.getRemainingAmount());

        remaining = Float.valueOf(merchantRedeemHistoryEnt.getRemainingAmount()) * prefHelper.getConvertedAmount();
        String formattedValueRemaining = String.format("%.2f", remaining);
        txtRemainingValue.setText(prefHelper.getConvertedAmountCurrrency() + " " + formattedValuePriceDiscounted + "");

        txtPoints.setText(Utils.getRemainingAmount(Integer.parseInt(merchantRedeemHistoryEnt.getEvoucherDetail().getAmount()), Integer.parseInt(merchantRedeemHistoryEnt.getEvoucherDetail().getProductDetail().getPrice())));
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.redemption_detail));
        titleBar.showNotificationButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDockActivity().replaceDockableFragment(NotificationsFragment.newInstance(), "NotificationsFragment");
            }
        }, prefHelper.getNotificationCount());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void setData(MerchantRedeemHistoryEnt merchantRedeemHistoryEnt) {

        imageLoader.displayImage(merchantRedeemHistoryEnt.getEvoucherDetail().getProductDetail().getProductImage(), ivDeal);
        //txtPaidType.setText(Utils.getVoucherType(merchantRedeemHistoryEnt.getEvoucherDetail().getType(),getMainActivity()));

        if (merchantRedeemHistoryEnt.getEvoucherDetail().getType().equals(AppConstants.cash_voucher)) {
            txtPaidType.setText("Cash Voucher");
        } else if (merchantRedeemHistoryEnt.getEvoucherDetail().getType().equals(AppConstants.discount_coupon)) {
            txtPaidType.setText("Discount Coupon");
        } else if (merchantRedeemHistoryEnt.getEvoucherDetail().getType().equals(AppConstants.promo_code)) {
            txtPaidType.setText("Promo Code");
        }
        //txtFlatePercents.setText(getString(R.string.flat) + " " + merchantRedemptionDetailEnt.getEvoucherDetail().getAmount() + getString(R.string.ofon));

        if (prefHelper.getLanguage().length() == 0 || prefHelper.getLanguage().equalsIgnoreCase(AppConstants.Eng))
            txtFlatePercents.setText(merchantRedeemHistoryEnt.getEvoucherDetail().getTitle());
        else if (prefHelper.getLanguage().equalsIgnoreCase(AppConstants.Malai))
            txtFlatePercents.setText(merchantRedeemHistoryEnt.getEvoucherDetail().getMaTitle());
        else if (prefHelper.getLanguage().equalsIgnoreCase(AppConstants.Indo))
            txtFlatePercents.setText(merchantRedeemHistoryEnt.getEvoucherDetail().getInTitle());

        txtTotalRate.setPaintFlags(txtTotalRate.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        txtTotalRate.setText("$" + merchantRedeemHistoryEnt.getEvoucherDetail().getProductDetail().getPrice());
        txtRemainingRate.setText(Utils.getRemainingAmount(Integer.parseInt(merchantRedeemHistoryEnt.getEvoucherDetail().getAmount()), Integer.parseInt(merchantRedeemHistoryEnt.getEvoucherDetail().getProductDetail().getPrice())));

        txtRedemptionDateValue.setText(DateHelper.dateFormat(merchantRedeemHistoryEnt.getUpdatedAt(), DateHelper.DATE_FORMAT2, DateHelper.DATE_TIME_FORMAT));

        txtUserIdValue.setText(merchantRedeemHistoryEnt.getUserId() + "");
        txtUserNameValue.setText(merchantRedeemHistoryEnt.getUserDetail().getFirstName() + " " + merchantRedeemHistoryEnt.getUserDetail().getLastName());
        txtRemainingValue.setText(prefHelper.getConvertedAmountCurrrency() + " " + merchantRedeemHistoryEnt.getRemainingAmount());
        txtPoints.setText(Utils.getRemainingAmount(Integer.parseInt(merchantRedeemHistoryEnt.getEvoucherDetail().getAmount()), Integer.parseInt(merchantRedeemHistoryEnt.getEvoucherDetail().getProductDetail().getPrice())));
    }


    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.merchantredemptiondetail:
                MerchantRedeemHistoryEnt merchantRedeemHistoryEnt = (MerchantRedeemHistoryEnt) result;
                if (merchantRedeemHistoryEnt != null)
                    setData(merchantRedeemHistoryEnt);
                break;
        }
    }
}
