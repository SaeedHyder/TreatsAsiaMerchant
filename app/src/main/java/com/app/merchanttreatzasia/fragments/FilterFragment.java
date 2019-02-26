package com.app.merchanttreatzasia.fragments;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.app.merchanttreatzasia.R;
import com.app.merchanttreatzasia.fragments.abstracts.BaseFragment;
import com.app.merchanttreatzasia.global.AppConstants;
import com.app.merchanttreatzasia.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by khan_muhammad on 9/14/2017.
 */

public class FilterFragment extends BaseFragment {


    @BindView(R.id.btnApply)
    Button btnApply;
    Unbinder unbinder;
    @BindView(R.id.rb_latest_to_old)
    RadioButton rbLatestToOld;
    @BindView(R.id.rb_old_to_latest)
    RadioButton rbOldToLatest;
    @BindView(R.id.radioGroupSort)
    RadioGroup radioGroupSort;
    @BindView(R.id.rbFlash_sale)
    RadioButton rbFlashSale;
    @BindView(R.id.rbVoucher)
    RadioButton rbVoucher;
    @BindView(R.id.radioGroupType)
    RadioGroup radioGroupType;

    String sortBy = AppConstants.latest_to_old;
    String voucherType = AppConstants.flash_sale;

    public static FilterFragment newInstance() {
        return new FilterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(prefHelper.getSort().length() > 0){
            if(prefHelper.getSort().equalsIgnoreCase(AppConstants.latest_to_old)){
                rbLatestToOld.setChecked(true);
                rbOldToLatest.setChecked(false);
            }
            else if(prefHelper.getSort().equalsIgnoreCase(AppConstants.old_to_latest)){
                rbLatestToOld.setChecked(false);
                rbOldToLatest.setChecked(true);
            }
        }

        if(prefHelper.getSearch().length() > 0){
            if(prefHelper.getSearch().equalsIgnoreCase(AppConstants.flash_sale)){
                rbFlashSale.setChecked(true);
                rbVoucher.setChecked(false);
            }
            else if(prefHelper.getSearch().equalsIgnoreCase(AppConstants.e_voucher)){
                rbFlashSale.setChecked(false);
                rbVoucher.setChecked(true);
            }
        }

        radioGroupSort.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                if (checkedId == rbLatestToOld.getId()){
                    sortBy = AppConstants.latest_to_old;
                    prefHelper.setSort(sortBy);
                }
                else if (checkedId == rbOldToLatest.getId()){
                    sortBy = AppConstants.old_to_latest;
                    prefHelper.setSort(sortBy);
                }

            }
        });

        radioGroupType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                if (checkedId == rbFlashSale.getId()){
                    voucherType = AppConstants.flash_sale;
                    prefHelper.setSearch(voucherType);
                }
                else if (checkedId == rbVoucher.getId()){
                    voucherType = AppConstants.e_voucher;
                    prefHelper.setSearch(voucherType);
                }
            }
        });
    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.filter));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick(R.id.btnApply)
    public void onViewClicked() {
        getDockActivity().popFragment();
    }
}
