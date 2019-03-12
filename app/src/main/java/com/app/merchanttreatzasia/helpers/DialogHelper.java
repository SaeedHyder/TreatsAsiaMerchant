package com.app.merchanttreatzasia.helpers;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.app.merchanttreatzasia.ui.views.AnyTextView;
import com.app.merchanttreatzasia.R;

public class DialogHelper {
    private Dialog dialog;
    private Context context;

    public DialogHelper(Context context) {
        this.context = context;
        this.dialog = new Dialog(context);
    }

    /*public Dialog initForgotPasswordDialog(int layoutID, View.OnClickListener onclicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        this.dialog.setContentView(layoutID);
        AnyTextView closeButton = (AnyTextView) dialog.findViewById(R.id.btnLogin);
        closeButton.setOnClickListener(onclicklistener);
        return this.dialog;
    }*/

    public Dialog initRedeemedCouponDialog(int layoutID, View.OnClickListener onokclicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        this.dialog.setContentView(layoutID);
        AnyTextView btnSubmit = (AnyTextView) dialog.findViewById(R.id.btnOk);
        AnyTextView txtMsg = (AnyTextView) dialog.findViewById(R.id.txtMsg);
        btnSubmit.setOnClickListener(onokclicklistener);

        return this.dialog;
    }
    public Dialog initRedeemedCouponDialog(int layoutID,String text, View.OnClickListener onokclicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        this.dialog.setContentView(layoutID);
        AnyTextView btnSubmit = (AnyTextView) dialog.findViewById(R.id.btnOk);
        AnyTextView txtMsg = (AnyTextView) dialog.findViewById(R.id.txtMsg);
        if(text!=null && !text.equals("") && !text.isEmpty()){
            txtMsg.setText(text);
        }
        btnSubmit.setOnClickListener(onokclicklistener);

        return this.dialog;
    }

   /* public Dialog initContactUsDialog(int layoutID, View.OnClickListener onokclicklistener, View.OnClickListener oncancelclicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        this.dialog.setContentView(layoutID);
        AnyTextView btnSubmit = (AnyTextView) dialog.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(onokclicklistener);
        AnyTextView btnCancel = (AnyTextView) dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(oncancelclicklistener);

        return this.dialog;
    }

    public Dialog initCountryCodeDialog(int layoutID, View.OnClickListener onokclicklistener, View.OnClickListener oncancelclicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        this.dialog.setContentView(layoutID);
        AnyTextView btnSubmit = (AnyTextView) dialog.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(onokclicklistener);
        AnyTextView btnCancel = (AnyTextView) dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(oncancelclicklistener);

        return this.dialog;
    }

    */

    public Dialog initLogoutDialog(int layoutID, View.OnClickListener onyesclicklistener, View.OnClickListener onnoclicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        this.dialog.setContentView(layoutID);
        Button btnYes = (Button) dialog.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(onyesclicklistener);
        Button btnNo = (Button) dialog.findViewById(R.id.btnNo);
        btnNo.setOnClickListener(onnoclicklistener);

        return this.dialog;
    }

    public void showDialog() {
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public void hideDialog() {
        dialog.dismiss();
    }

}
