package com.app.merchanttreatzasia.ui.binders;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.merchanttreatzasia.R;
import com.app.merchanttreatzasia.activities.DockActivity;
import com.app.merchanttreatzasia.entities.MerchantRedeemHistoryEnt;
import com.app.merchanttreatzasia.fragments.RedemptionDetailsFragment;
import com.app.merchanttreatzasia.global.AppConstants;
import com.app.merchanttreatzasia.global.Utils;
import com.app.merchanttreatzasia.global.UtilsGlobal;
import com.app.merchanttreatzasia.helpers.BasePreferenceHelper;
import com.app.merchanttreatzasia.helpers.DateHelper;
import com.app.merchanttreatzasia.ui.viewbinders.abstracts.ViewBinder;
import com.app.merchanttreatzasia.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BinderMyHistoryItem extends ViewBinder<MerchantRedeemHistoryEnt> {


    private DockActivity dockActivity;
    private ImageLoader imageLoader;
    BasePreferenceHelper prefHelper;
    Float updatedTotal;
    Float updatedDiscounted;

    public BinderMyHistoryItem(DockActivity dockActivity, BasePreferenceHelper prefHelper) {
        super(R.layout.fragment_my_history_item);
        this.dockActivity =dockActivity;
        imageLoader = ImageLoader.getInstance();
        this.prefHelper = prefHelper;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(final MerchantRedeemHistoryEnt entity, int position, int grpPosition, View view, Activity activity) {

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        viewHolder.txtTotalRate.setPaintFlags(viewHolder.txtTotalRate.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        if (entity != null
                && entity.getMerchantDetail() != null
                && entity.getEvoucherDetail() != null
                && entity.getEvoucherDetail().getProductDetail() != null
                && entity.getUserDetail() != null) {
            imageLoader.displayImage(entity.getEvoucherDetail().getProductDetail().getProductImage(), viewHolder.ivDeal);

       /* String faltePercent = "<font color=#3e3e3e>"+ context.getString(R.string.flat) +"</font> <b><font color=#494949>" + entity.getEvoucherDetail().getAmount() + "%</font></b> <font color=#3e3e3e>"+ context.getString(R.string.offon) +"</font>";
        viewHolder.txtFlatePercents.setText(Html.fromHtml(faltePercent));*/


            if (prefHelper.getLanguage().equalsIgnoreCase(AppConstants.Eng)) {
                viewHolder.txtFlatePercents.setText(entity.getEvoucherDetail().getTitle() + "");
            } else if (prefHelper.getLanguage().equalsIgnoreCase(AppConstants.Malai)) {
                viewHolder.txtFlatePercents.setText(entity.getEvoucherDetail().getMaTitle() + "");
            } else if (prefHelper.getLanguage().equalsIgnoreCase(AppConstants.Indo)) {
                viewHolder.txtFlatePercents.setText(entity.getEvoucherDetail().getInTitle() + "");
            } else {
                viewHolder.txtFlatePercents.setText(entity.getEvoucherDetail().getTitle() + "");
            }


            viewHolder.txtTotalRate.setText("$" + entity.getEvoucherDetail().getProductDetail().getPrice());
            if (entity.getEvoucherDetail().getType().equals(AppConstants.cash_voucher)) {
                viewHolder.txtPaidType.setText("Cash Voucher");
            } else if (entity.getEvoucherDetail().getType().equals(AppConstants.discount_coupon)) {
                viewHolder.txtPaidType.setText("Discount Coupon");
            } else if (entity.getEvoucherDetail().getType().equals(AppConstants.promo_code)) {
                viewHolder.txtPaidType.setText("Promo Code");
            }
            viewHolder.txtRemainingRate.setText(Utils.getRemainingAmount(Integer.parseInt(entity.getEvoucherDetail().getAmount()), Integer.parseInt(entity.getEvoucherDetail().getProductDetail().getPrice())));

            updatedTotal = Float.valueOf(entity.getEvoucherDetail().getProductDetail().getPrice()) * prefHelper.getConvertedAmount();
            String formattedValuePriceTotal = String.format("%.2f", updatedTotal);
            viewHolder.txtTotalRate.setText(prefHelper.getConvertedAmountCurrrency() + " " + formattedValuePriceTotal + "");

            updatedDiscounted = Float.valueOf(UtilsGlobal.getRemainingAmountWithoutDollar(Integer.parseInt(entity.getEvoucherDetail().getAmount()), Integer.parseInt(entity.getEvoucherDetail().getProductDetail().getPrice()))) * prefHelper.getConvertedAmount();
            String formattedValuePriceDiscounted = String.format("%.2f", updatedDiscounted);
            viewHolder.txtRemainingRate.setText(prefHelper.getConvertedAmountCurrrency() + " " + formattedValuePriceDiscounted + "");

            viewHolder.txtRedemptionDateValue.setText(DateHelper.dateFormat(entity.getUpdatedAt(), DateHelper.DATE_FORMAT2, DateHelper.DATE_TIME_FORMAT));
            viewHolder.txtPoints.setText(entity.getPoint());

            viewHolder.ll_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dockActivity.replaceDockableFragment(RedemptionDetailsFragment.newInstance(entity), "RedemptionDetailsFragment");
                }
            });
        }
    }

    /**
     * @return index of pattern in s or -1, if not found
     */
    public static int startIndexOf(Pattern pattern, String s) {
        Matcher matcher = pattern.matcher(s);
        return matcher.find() ? matcher.start() : -1;
    }

    /**
     * @return index of pattern in s or -1, if not found
     */
    public static int endIndexOf(Pattern pattern, String s) {
        Matcher matcher = pattern.matcher(s);
        return matcher.find() ? matcher.end() : -1;
    }

    static class ViewHolder extends BaseViewHolder{
        @BindView(R.id.ivDeal)
        ImageView ivDeal;
        @BindView(R.id.txtPaidType)
        AnyTextView txtPaidType;
        @BindView(R.id.txtFlatePercents)
        AnyTextView txtFlatePercents;
        @BindView(R.id.txtCompanyName)
        AnyTextView txtCompanyName;
        @BindView(R.id.llDetail)
        LinearLayout llDetail;
        @BindView(R.id.txtTotalRate)
        AnyTextView txtTotalRate;
        @BindView(R.id.txtRemainingRate)
        AnyTextView txtRemainingRate;
        @BindView(R.id.txtPoints)
        AnyTextView txtPoints;
        @BindView(R.id.imageView2)
        ImageView imageView2;
        @BindView(R.id.txtRedemptionDate)
        AnyTextView txtRedemptionDate;
        @BindView(R.id.txtRedemptionDateValue)
        AnyTextView txtRedemptionDateValue;
        @BindView(R.id.ll_main)
        LinearLayout ll_main;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}


