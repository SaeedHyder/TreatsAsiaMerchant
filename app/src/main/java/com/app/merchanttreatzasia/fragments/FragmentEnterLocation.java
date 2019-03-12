package com.app.merchanttreatzasia.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.merchanttreatzasia.R;
import com.app.merchanttreatzasia.fragments.abstracts.BaseFragment;
import com.app.merchanttreatzasia.interfaces.IFragmentChangeListner;
import com.app.merchanttreatzasia.ui.views.TitleBar;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jota.autocompletelocation.AutoCompleteLocation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FragmentEnterLocation extends BaseFragment implements OnMapReadyCallback {


    @BindView(R.id.btnNext)
    Button btnNext;
    @BindView(R.id.btnSkip)
    Button btnSkip;

    Unbinder unbinder;
    @BindView(R.id.edtLocation)
    AutoCompleteLocation edtLocation;

    SupportMapFragment mapFragment;
    // Google Map
    GoogleMap googleMap;
    double latitude;
    double longitude;

    private View view = null;

    public static FragmentEnterLocation newInstance() {
        return new FragmentEnterLocation();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_enter_location, container, false);
        } else {
            return view;
        }

        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initMap();
        edtLocation.setHint(getString(R.string.enter_your_location));
        edtLocation.setAutoCompleteTextListener(new AutoCompleteLocation.AutoCompleteLocationListener() {
            @Override
            public void onTextClear() {

            }

            @Override
            public void onItemSelected(Place selectedPlace) {


                LatLng latLng = selectedPlace.getLatLng();

                if (latLng != null) {
                    latitude = latLng.latitude;
                    longitude = latLng.longitude;

                    moveMap();
                }

            }
        });

    }

    private void initMap() {

        mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private void moveMap() {

        googleMap.clear();
        LatLng latLng = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin))
                .draggable(true));

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        googleMap.getUiSettings().setZoomControlsEnabled(false);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        // googleMap.setMyLocationButtonEnabled(true);
        if (ActivityCompat.checkSelfPermission(getDockActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getDockActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(false);
        googleMap.getUiSettings().setMapToolbarEnabled(false);

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.find_a_lawyer));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();


    }


    @Override
    public void onMapReady(GoogleMap googlemap) {

        googleMap = googlemap;

    }

    private boolean validate() {
        if (edtLocation.getText().length() == 0) {
            edtLocation.setError(getString(R.string.enter_your_location));
            return false;
        }  else {
            return true;
        }

    }

    @OnClick({R.id.btnNext, R.id.btnSkip})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.btnNext:

                if(validate())
                    getDockActivity().replaceDockableFragment(TypeOfLawyerFragment.newInstance(), TypeOfLawyerFragment.class.getSimpleName());

                break;

            case R.id.btnSkip:

                getDockActivity().replaceDockableFragment(TypeOfLawyerFragment.newInstance(), TypeOfLawyerFragment.class.getSimpleName());

                break;
        }
    }
}
