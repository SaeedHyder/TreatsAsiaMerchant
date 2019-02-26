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



public class BranchDataFilterFragment extends BaseFragment {

    @BindView(R.id.btnApply)
    Button btnApply;
    Unbinder unbinder;
    @BindView(R.id.rb_latest_to_old)
    RadioButton rbLatestToOld;
    @BindView(R.id.rb_old_to_latest)
    RadioButton rbOldToLatest;
    @BindView(R.id.radioGroupSort)
    RadioGroup radioGroupSort;

    String sortBy = AppConstants.latest_to_old;
    String voucherType = AppConstants.flash_sale;

    public static BranchDataFilterFragment newInstance() {
        return new BranchDataFilterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_branch_data_filter, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(prefHelper.getBranchDataSort().length() > 0){
            if(prefHelper.getBranchDataSort().equalsIgnoreCase(AppConstants.latest_to_old)){
                rbLatestToOld.setChecked(true);
                rbOldToLatest.setChecked(false);
            }
            else if(prefHelper.getBranchDataSort().equalsIgnoreCase(AppConstants.old_to_latest)){
                rbLatestToOld.setChecked(false);
                rbOldToLatest.setChecked(true);
            }
        }

        radioGroupSort.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                if (checkedId == rbLatestToOld.getId()){
                    sortBy = AppConstants.latest_to_old;
                    prefHelper.setBranchDataSort(sortBy);
                }
                else if (checkedId == rbOldToLatest.getId()){
                    sortBy = AppConstants.old_to_latest;
                    prefHelper.setBranchDataSort(sortBy);
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
