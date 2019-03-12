package com.app.merchanttreatzasia.fragments;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.SparseArray;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.merchanttreatzasia.R;
import com.app.merchanttreatzasia.fragments.abstracts.BaseFragment;
import com.app.merchanttreatzasia.helpers.UIHelper;
import com.app.merchanttreatzasia.ui.views.TitleBar;
import com.google.android.gms.samples.vision.barcodereader.BarcodeCapture;
import com.google.android.gms.samples.vision.barcodereader.BarcodeGraphic;
import com.google.android.gms.vision.barcode.*;

import java.util.List;

import xyz.belvi.mobilevisionbarcodescanner.BarcodeRetriever;



public class ScanIDFragment extends BaseFragment implements BarcodeRetriever {
    private BarcodeCapture barcodeCapture;
    private View viewParent;

    public static ScanIDFragment newInstance() {
        return new ScanIDFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
        barcodeCapture = (BarcodeCapture) getChildFragmentManager().findFragmentById(R.id.barcode);
        barcodeCapture.setRetrieval(this);
        barcodeCapture.setShowDrawRect(true);
        barcodeCapture.setShouldShowText(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        // View view = inflater.inflate(R.layout.fragment_scan_id, container, false);
        if (viewParent != null) {
            ViewGroup parent = (ViewGroup) viewParent.getParent();
            if (parent != null)
                parent.removeView(viewParent);
        }
        try {
            viewParent = inflater.inflate(R.layout.fragment_scan_id, container, false);
        } catch (InflateException e) {
            getDockActivity().popFragment();
        }
        return viewParent;
    }

    /* @Override
     public void onDestroyView() {
         super.onDestroyView();
         BarcodeCapture f = (BarcodeCapture) getChildFragmentManager()
                 .findFragmentById(R.id.barcode);
         if (f != null)
             getChildFragmentManager().beginTransaction().remove(f).commit();
     }*/
    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getDockActivity().popFragment();
            }
        });
        titleBar.showNotificationButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDockActivity().replaceDockableFragment(NotificationsFragment.newInstance(), NotificationsFragment.class.getSimpleName());
            }
        },prefHelper.getNotificationCount());
        titleBar.setSubHeading(getString(R.string.scan_code));
    }

    @Override
    public void onRetrieved(final Barcode barcode) {
        getMainActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

              /*  AlertDialog.Builder builder = new AlertDialog.Builder(getDockActivity())
                        .setTitle("code retrieved")
                        .setMessage(barcode.displayValue);
                builder.show();*/
               // UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.scanned_complete));
                getDockActivity().replaceDockableFragment(MerchantVarificationFragment.newInstance(barcode.displayValue), MerchantVarificationFragment.class.getSimpleName());
            }
        });
        //  barcodeCapture.stopScanning();
        // UIHelper.showShortToastInCenter(getDockActivity(), barcode.displayValue);
    }

    @Override
    public void onRetrievedMultiple(Barcode closetToClick, List<BarcodeGraphic> barcode) {
    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {
    }

    @Override
    public void onRetrievedFailed(String reason) {
        UIHelper.showShortToastInCenter(getDockActivity(), reason);
    }

    @Override
    public void onPermissionRequestDenied() {
        UIHelper.showShortToastInCenter(getDockActivity(), "User Cancel Permission");
        getDockActivity().popFragment();
    }

    @Override
    public void onPause() {
//        barcodeCapture.stopScanning();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
