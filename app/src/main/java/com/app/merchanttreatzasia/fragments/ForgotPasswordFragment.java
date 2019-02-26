package com.app.merchanttreatzasia.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.merchanttreatzasia.R;
import com.app.merchanttreatzasia.fragments.abstracts.BaseFragment;
import com.app.merchanttreatzasia.global.WebServiceConstants;
import com.app.merchanttreatzasia.ui.dialogs.DialogFactory;
import com.app.merchanttreatzasia.ui.views.AnyEditTextView;
import com.app.merchanttreatzasia.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgotPasswordFragment extends BaseFragment {
    @BindView(R.id.txt_email)
    AnyEditTextView txtEmail;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    public static ForgotPasswordFragment newInstance() {
        return new ForgotPasswordFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        ButterKnife.bind(this, view);

        return view;

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);

    }


    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        if (validate()) {
            serviceHelper.enqueueCall(webService.forgotpassword(txtEmail.getText().toString()), WebServiceConstants.forgotPassword);

        }

    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag){
            case WebServiceConstants.forgotPassword:
                Dialog dialog = DialogFactory.createMessageDialog(getDockActivity(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        getDockActivity().popFragment();
                    }
                }, getString(R.string.reset_password_message), getString(R.string.reset_password_title), R.drawable.ic_launcher);
                dialog.show();
                break;
        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.reset_password_header));
    }

    private boolean validate() {
        if (txtEmail.getText().toString().trim().equals("") ||
                !Patterns.EMAIL_ADDRESS.matcher(txtEmail.getText().toString()).matches()) {
            txtEmail.setError(getResources().getString(R.string.email_error));
            return false;
        } else {
            return true;
        }
    }
}
