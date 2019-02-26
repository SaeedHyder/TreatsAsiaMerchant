package com.app.merchanttreatzasia.fragments;

import android.os.Bundle;
import android.provider.Contacts;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.merchanttreatzasia.R;
import com.app.merchanttreatzasia.entities.ProfileEnt;
import com.app.merchanttreatzasia.fragments.abstracts.BaseFragment;
import com.app.merchanttreatzasia.global.AppConstants;
import com.app.merchanttreatzasia.global.WebServiceConstants;
import com.app.merchanttreatzasia.helpers.InternetHelper;
import com.app.merchanttreatzasia.helpers.TokenUpdater;
import com.app.merchanttreatzasia.helpers.UIHelper;
import com.app.merchanttreatzasia.interfaces.IGetLocation;
import com.app.merchanttreatzasia.ui.dialogs.DialogFactory;
import com.app.merchanttreatzasia.ui.views.AnyEditTextView;
import com.app.merchanttreatzasia.ui.views.AnyTextView;
import com.app.merchanttreatzasia.ui.views.TitleBar;
import com.google.android.gms.maps.model.LatLng;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileFragment extends BaseFragment implements IGetLocation, View.OnClickListener {
    @BindView(R.id.edt_storename)
    AnyEditTextView edtStorename;
    @BindView(R.id.edt_storeaddress)
    AnyTextView edtStoreaddress;
    @BindView(R.id.btn_update)
    Button btnUpdate;
    private LatLng location;
    String  address;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtStoreaddress.setOnClickListener(this);
        location = new LatLng(0, 0);
        serviceHelper.enqueueCall(webService.getMerchantProfile(prefHelper.getMerchant().getToken()), WebServiceConstants.getProfile);
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.getProfile:
                bindData((ProfileEnt) result);
                break;
            case WebServiceConstants.updateProfile:
                TokenUpdater.getInstance().UpdateToken(getDockActivity(),
                        prefHelper.getFirebase_TOKEN(),
                        AppConstants.Device_Type,
                        prefHelper.getMerchant().getToken());
                UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.profile_updated));
                break;
        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showNotificationButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDockActivity().replaceDockableFragment(NotificationsFragment.newInstance(), NotificationsFragment.class.getSimpleName());
            }
        }, prefHelper.getNotificationCount());

        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.my_profile));
    }

    private void bindData(ProfileEnt result) {
        if (result != null) {
            edtStorename.setText(result.getFirstName());
            if (!result.getLatitude().equals("") && !result.getLongitude().equals("")) {
                location = new LatLng(Double.parseDouble(result.getLatitude()), Double.parseDouble(result.getLongitude()));
                edtStoreaddress.setText(result.getLocation());
            } else {
                edtStoreaddress.setText(AppConstants.PleaseSelectLocation);
            }
        }
    }

    @OnClick(R.id.btn_update)
    public void onViewClicked() {
        if (validate()) {
            if (!edtStoreaddress.getText().toString().equalsIgnoreCase(AppConstants.PleaseSelectLocation)) {
                serviceHelper.enqueueCall(webService.merchantUpdateProfile(
                        edtStorename.getText().toString(),
                        edtStoreaddress.getText().toString(),
                        location.latitude + "",
                        location.longitude + "",
                        prefHelper.getMerchant().getToken()),
                        WebServiceConstants.updateProfile);
            }else {
                UIHelper.showShortToastInCenter(getDockActivity(), "Please select location to proceed.");
            }
        }
    }

    private boolean validate() {
        if (edtStorename.getText().toString().trim().equals("")) {
            edtStorename.setError(getString(R.string.store_error));
            return false;
        } else if (edtStoreaddress.getText().toString().trim().equals("")) {
            edtStoreaddress.setError(getString(R.string.store_address_error));
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onLocationSet(LatLng location, String formattedAddress) {
        edtStoreaddress.setText(formattedAddress + "");
        this.location = location;
    }

    @Override
    public void onClick(View v) {
        if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
            MapControllerFragment mapControllerFragment = MapControllerFragment.newInstance();
            mapControllerFragment.setDelegate(this);

            DialogFactory.showMapControllerDialog(getDockActivity(), mapControllerFragment);
        }
    }
}
