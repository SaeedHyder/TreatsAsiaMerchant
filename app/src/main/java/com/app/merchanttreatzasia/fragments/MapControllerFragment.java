package com.app.merchanttreatzasia.fragments;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.app.merchanttreatzasia.BaseApplication;
import com.app.merchanttreatzasia.R;
import com.app.merchanttreatzasia.entities.GoogleGeoCodeResponse;
import com.app.merchanttreatzasia.entities.GoogleServiceResponse;
import com.app.merchanttreatzasia.fragments.abstracts.BaseFragment;
import com.app.merchanttreatzasia.global.AppConstants;
import com.app.merchanttreatzasia.helpers.CheckGPSAvailibility;
import com.app.merchanttreatzasia.helpers.GPSHelper;
import com.app.merchanttreatzasia.helpers.UIHelper;
import com.app.merchanttreatzasia.interfaces.IGetLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by saeedhyder on 5/9/2017.
 */


public class MapControllerFragment extends BaseFragment {

    String SearchedAddress;
    @BindView(R.id.searchEditText)
    EditText searchEditText;
    @BindView(R.id.btnUseLocation)
    Button btnUseLocation;
    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;
    private boolean mIsLocation = false;
    private LatLng scammerLocation;
    private IGetLocation delegate;

    public static MapControllerFragment newInstance() {
        return new MapControllerFragment();
    }

    public void setDelegate(IGetLocation delegate) {
        this.delegate = delegate;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMapView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mapcontroller, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListener();

    }

    private void setListener() {

        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchLocation();
                    return true;
                }
                return false;
            }
        });

        btnUseLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsLocation = !mIsLocation;
                setLocationButton(mIsLocation);
            }
        });
    }

    private void setMapView() {
        mapFragment = SupportMapFragment.newInstance();
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                MapControllerFragment.this.googleMap = googleMap;

                if (null != googleMap) {

                    googleMap.getUiSettings().setAllGesturesEnabled(false);
                    googleMap.setMyLocationEnabled(true);

                    if (mGpsTracker.getLocation() != null) {
                        moveGoogleMapToLocation(0, 0);
                    } else {
                        if (!CheckGPSAvailibility.checkGPSAvailibility(getDockActivity())) {
                            GPSHelper.showGPSDisabledAlertToUser(getDockActivity());
                            setLocationButton(mIsLocation);
                        }
                    }
                }
            }
        });

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.mapView, mapFragment).commit();
    }

    private void moveGoogleMapToLocation(double lat, double lng) {
        try {

            scammerLocation = new LatLng(lat, lng);

            googleMap.getUiSettings().setZoomControlsEnabled(false);

            googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                @Override
                public void onCameraIdle() {
                    // Cleaning all the markers.
                    if (googleMap != null) {
                        googleMap.clear();
                    }

                    mIsLocation = false;
                    setLocationButton(mIsLocation);
                    scammerLocation = googleMap.getCameraPosition().target;
                    SearchedAddress = getLocation(scammerLocation);
                }
            });




           /* googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                public void onCameraChange(CameraPosition cameraPosition) {

                    mIsLocation = false;
                    setLocationButton(mIsLocation);

                    scammerLocation = cameraPosition.target;

                }
            });*/

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(scammerLocation, AppConstants.zoomIn));
        } catch (Exception e) {
            UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.went_wrong));
        }


    }

    private String getLocation(LatLng scamerLocation) {
        Geocoder geocoder;
        List<Address> addresses;
        try {
            geocoder = new Geocoder(getDockActivity(), Locale.getDefault());

            addresses = geocoder.getFromLocation(scamerLocation.latitude, scamerLocation.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            String address = addresses.get(0).getAddressLine(0) == null ? "" : addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality() == null ? "" : addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea() == null ? "" : addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName() == null ? "" : addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode() == null ? "" : addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName() == null ? "" : addresses.get(0).getFeatureName();
            return address;
        } catch (Exception e) {
            return getString(R.string.no_location_found);
        }


    }

    private void setLocationButton(boolean isLocationSet) {
        if (isLocationSet) {
            if (scammerLocation != null) {
               /* if (delegate == null) {


                } else {*/


                delegate.onLocationSet(scammerLocation, SearchedAddress);
                BaseApplication.getBus().post("true");
                //}
            } else {
                UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.Unable_location));
            }
        } else {

            btnUseLocation.setText(R.string.Use_This_Location);

        }


    }

    private void searchLocation() {
        getMainActivity().onLoadingStarted();
        btnUseLocation.setEnabled(false);
        if (delegate != null) {
            btnUseLocation.setText(R.string.Loading_Location);
        }
        Call<GoogleServiceResponse<List<GoogleGeoCodeResponse>>> request = googleWebService.getLatLongInfo(
                searchEditText.getText().toString(),
                "false");
        request.enqueue(new Callback<GoogleServiceResponse<List<GoogleGeoCodeResponse>>>() {
            @Override
            public void onResponse(Call<GoogleServiceResponse<List<GoogleGeoCodeResponse>>> call, Response<GoogleServiceResponse<List<GoogleGeoCodeResponse>>> listGoogleServiceResponse) {
                getMainActivity().onLoadingFinished();
                try {
                    if (listGoogleServiceResponse.body().getResults().size() > 0) {

                        btnUseLocation.setEnabled(true);
                        hideKeyboard();
                        googleMap.getUiSettings().setAllGesturesEnabled(true);
                        moveGoogleMapToLocation(Double.valueOf(listGoogleServiceResponse.body().getResults().get(0).getGeometry().getLocation().getLat()), Double.valueOf(listGoogleServiceResponse.body().getResults().get(0).getGeometry().getLocation().getLng()));
                        SearchedAddress = listGoogleServiceResponse.body().getResults().get(0).getFormatted_address();
                    } else {
                        UIHelper.showLongToastInCenter(getDockActivity(), "No location found");
                        btnUseLocation.setEnabled(true);
                        setLocationButton(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    UIHelper.showLongToastInCenter(getDockActivity(), "No location found");
                    btnUseLocation.setEnabled(true);
                    setLocationButton(false);
                }
            }

            @Override
            public void onFailure(Call<GoogleServiceResponse<List<GoogleGeoCodeResponse>>> call, Throwable error) {
                getMainActivity().onLoadingFinished();
                //  RetrofitErrorHandler.onServiceFail(getDockActivity(), error);
                setLocationButton(false);
                btnUseLocation.setEnabled(true);
            }
        });

    }

    public LatLng getScammerLocation() {
        return scammerLocation;
    }

}