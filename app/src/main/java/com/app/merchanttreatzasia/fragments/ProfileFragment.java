package com.app.merchanttreatzasia.fragments;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.Settings;
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
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends BaseFragment implements IGetLocation, View.OnClickListener {
    @BindView(R.id.edt_storename)
    AnyEditTextView edtStorename;
    @BindView(R.id.edt_storeaddress)
    AnyTextView edtStoreaddress;
    @BindView(R.id.btn_update)
    Button btnUpdate;
    private LatLng location;
    String  address;
    private int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

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

       /* if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
            MapControllerFragment mapControllerFragment = MapControllerFragment.newInstance();
            mapControllerFragment.setDelegate(this);

            DialogFactory.showMapControllerDialog(getDockActivity(), mapControllerFragment);
        }*/
        requestLocationPermission();
    }

    private void requestLocationPermission() {
        Dexter.withActivity(getDockActivity())
                .withPermissions(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {

                        if (report.areAllPermissionsGranted()) {
                          /*  if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
                                edtEmail.setCursorVisible(false);
                                MapControllerFragment mapControllerFragment = MapControllerFragment.newInstance();
                                mapControllerFragment.setDelegate(ProfileFragment.this);

                                DialogFactory.showMapControllerDialog(getDockActivity(), mapControllerFragment);
                            }*/
                            openLocationSelector();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            requestLocationPermission();

                        } else if (report.getDeniedPermissionResponses().size() > 0) {
                            requestLocationPermission();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        UIHelper.showShortToastInCenter(getDockActivity(), "Grant LocationEnt Permission to processed");
                        openSettings();
                    }
                })

                .onSameThread()
                .check();


    }

    private void openSettings() {

        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        Uri uri = Uri.fromParts("package", getDockActivity().getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

    private void openLocationSelector() {

        try {
           /* Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(getDockActivity());*/
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            startActivityForResult(builder.build(getDockActivity()), PLACE_AUTOCOMPLETE_REQUEST_CODE);
            //this.startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (Exception e) {

            e.printStackTrace();
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                Place place = PlaceAutocomplete.getPlace(getDockActivity(), data);
                if (place != null) {
                    UIHelper.hideSoftKeyboard(getDockActivity(), getView());
                    this.location = place.getLatLng();
                    edtStoreaddress.setText(getMainActivity().getCurrentAddress(place.getLatLng().latitude,place.getLatLng().longitude));

                }

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getDockActivity(), data);

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }

    }
}
