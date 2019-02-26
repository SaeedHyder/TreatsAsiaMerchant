package com.app.merchanttreatzasia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.app.merchanttreatzasia.R;
import com.app.merchanttreatzasia.fragments.abstracts.BaseFragment;
import com.app.merchanttreatzasia.global.AppConstants;
import com.app.merchanttreatzasia.global.WebServiceConstants;
import com.app.merchanttreatzasia.helpers.UIHelper;
import com.app.merchanttreatzasia.ui.views.AnyTextView;
import com.app.merchanttreatzasia.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SettingFragment extends BaseFragment {

    @BindView(R.id.txtNotification)
    AnyTextView txtNotification;
    @BindView(R.id.toggleNotification)
    ToggleButton toggleNotification;
    @BindView(R.id.llLanguage)
    LinearLayout llLanguage;
    @BindView(R.id.llAboutApp)
    LinearLayout llAboutApp;
    Unbinder unbinder;
    @BindView(R.id.llOurBranches)
    LinearLayout llOurBranches;

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (prefHelper.getNotifiationSatus().equals(AppConstants.notificationOn)) {
            toggleNotification.setChecked(true);
        } else {
            toggleNotification.setChecked(false);
        }

        if (prefHelper.getMerchant().getUserType()!=null)
        if (prefHelper.getMerchant().getUserType().equalsIgnoreCase(AppConstants.MERCHANT)) {
            llOurBranches.setVisibility(View.VISIBLE);
        } else {
            llOurBranches.setVisibility(View.GONE);
        }

        toggleNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    serviceHelper.enqueueCall(webService.changepushnotificationstatus(1 + "", prefHelper.getMerchant().getToken()), WebServiceConstants.notificationonoff);
                    prefHelper.setNotifiationSatus(AppConstants.notificationOn);
                } else {
                    serviceHelper.enqueueCall(webService.changepushnotificationstatus(0 + "", prefHelper.getMerchant().getToken()), WebServiceConstants.notificationonoff);
                    prefHelper.setNotifiationSatus(AppConstants.notificationOff);
                }
            }
        });
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.settings));
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

    @OnClick({R.id.llLanguage, R.id.llAboutApp, R.id.llOurBranches})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llLanguage:
                getDockActivity().replaceDockableFragment(LanguageFragment.newInstance(), "LanguageFragment");
                break;
            case R.id.llAboutApp:
                getDockActivity().replaceDockableFragment(AboutAppFragment.newInstance(), "AboutAppFragment");
                break;

            case R.id.llOurBranches:
                getDockActivity().replaceDockableFragment(BranchesEditListFragmnet.newInstance(), "BranchesEditListFragmnet");
                break;
        }
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.notificationonoff:
                UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.notification_settings_message));
                break;
        }
    }
}
