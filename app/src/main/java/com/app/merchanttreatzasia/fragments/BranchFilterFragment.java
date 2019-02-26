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
 * Created by khan_muhammad on 11/10/2017.
 */

public class BranchFilterFragment extends BaseFragment {


    @BindView(R.id.radioGroupSort)
    RadioGroup radioGroupSort;
    @BindView(R.id.btnApply)
    Button btnApply;
    Unbinder unbinder;

    @BindView(R.id.rb_a_to_z)
    RadioButton rbAToZ;
    @BindView(R.id.rb_z_to_a)
    RadioButton rbZToA;

    String sortBy = AppConstants.a_to_z;

    public static BranchFilterFragment newInstance() {
        return new BranchFilterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.branch_filter, container, false);


        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (prefHelper.getBranchSort().length() > 0) {
            if (prefHelper.getBranchSort().equalsIgnoreCase(AppConstants.a_to_z)) {
                rbAToZ.setChecked(true);
                rbZToA.setChecked(false);
            } else if (prefHelper.getBranchSort().equalsIgnoreCase(AppConstants.z_to_a)) {
                rbAToZ.setChecked(false);
                rbZToA.setChecked(true);
            }
        }

        radioGroupSort.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                if (checkedId == rbAToZ.getId()) {
                    sortBy = AppConstants.a_to_z;
                    prefHelper.setBranchSort(sortBy);
                } else if (checkedId == rbZToA.getId()) {
                    sortBy = AppConstants.z_to_a;
                    prefHelper.setBranchSort(sortBy);
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
