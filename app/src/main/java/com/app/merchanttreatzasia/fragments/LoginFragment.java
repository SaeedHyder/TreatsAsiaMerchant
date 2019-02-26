package com.app.merchanttreatzasia.fragments;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.merchanttreatzasia.R;
import com.app.merchanttreatzasia.entities.MerchantEnt;
import com.app.merchanttreatzasia.fragments.abstracts.BaseFragment;
import com.app.merchanttreatzasia.global.AppConstants;
import com.app.merchanttreatzasia.global.WebServiceConstants;
import com.app.merchanttreatzasia.helpers.TokenUpdater;
import com.app.merchanttreatzasia.ui.views.AnyEditTextView;
import com.app.merchanttreatzasia.ui.views.AnyTextView;
import com.app.merchanttreatzasia.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginFragment extends BaseFragment {


    @BindView(R.id.edt_email)
    AnyEditTextView edtEmail;
    @BindView(R.id.edt_password)
    AnyEditTextView edtPassword;
    @BindView(R.id.btn_forgot_password)
    AnyTextView btnForgotPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;


    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);


    }

    @OnClick({R.id.btn_forgot_password, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_forgot_password:
                getDockActivity().replaceDockableFragment(ForgotPasswordFragment.newInstance(), ForgotPasswordFragment.class.getSimpleName());
                break;
            case R.id.btn_login:
                if (validated()) {
                    serviceHelper.enqueueCall(webService.loginMerchant(edtEmail.getText().toString(), edtPassword.getText().toString()), WebServiceConstants.loginMerchant);
                }
                break;
        }
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.loginMerchant:
                MerchantEnt ent = (MerchantEnt) result;
                prefHelper.putMerchant(ent);
                prefHelper.setmerchantId(ent.getId() + "");
                TokenUpdater.getInstance().UpdateToken(getDockActivity(),
                        prefHelper.getFirebase_TOKEN(),
                        AppConstants.Device_Type,
                        prefHelper.getMerchant().getToken());
                prefHelper.setLoginStatus(true);
                getDockActivity().popBackStackTillEntry(0);
                getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), "HomeFragmnet");
                break;
        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        // TODO Auto-generated method stub
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
    }

    private boolean validated() {
        if (edtEmail.getText().toString().trim().equals("") ||
                !Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()) {
            edtEmail.setError(getString(R.string.email_error));
            return false;
        } else if (edtPassword.getText().toString().trim().equals("") || edtPassword.getText().toString().length() < 6) {
            edtPassword.setError(getString(R.string.password_error));
            return false;
        } else {
            return true;
        }
    }
}
