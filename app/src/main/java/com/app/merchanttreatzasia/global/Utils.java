package com.app.merchanttreatzasia.global;

import android.content.Context;
import com.app.merchanttreatzasia.R;
/**
 * Created by khan_muhammad on 10/7/2017.
 */

public class Utils {


    public static String getRemainingAmount(int percent, int totalPrice) {

        int discount = totalPrice * percent/100;
        int remainingAmount = totalPrice - discount;

        return "$" + remainingAmount + "";
    }

    public static String getRemainingAmountForRedeemCoupon(int percent, int totalPrice) {

        int discount = totalPrice * percent/100;
        int remainingAmount = totalPrice - discount;

        return remainingAmount + "";
    }

    public static String getVoucherType(String type, Context context) {

        String vouchertype = "";

        if(type.equalsIgnoreCase("cash_back")){
            vouchertype = context.getString(R.string.cashback);
        }
        else  if(type.equalsIgnoreCase("flat_free")){
            vouchertype = context.getString(R.string.flatfree);
        }
        else  if(type.equalsIgnoreCase("discount")){
            vouchertype = context.getString(R.string.discount);
        }

        return vouchertype;
    }

}
