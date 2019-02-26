package com.app.merchanttreatzasia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.merchanttreatzasia.R;
import com.app.merchanttreatzasia.entities.BranchEnt;
import com.app.merchanttreatzasia.fragments.abstracts.BaseFragment;
import com.app.merchanttreatzasia.global.AppConstants;
import com.app.merchanttreatzasia.global.WebServiceConstants;
import com.app.merchanttreatzasia.helpers.UIHelper;
import com.app.merchanttreatzasia.ui.views.AnyEditTextView;
import com.app.merchanttreatzasia.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;



public class CreateNewBranchFragment extends BaseFragment {


    @BindView(R.id.edtBranchName)
    AnyEditTextView edtBranchName;
    @BindView(R.id.edtEmail)
    AnyEditTextView edtEmail;
    @BindView(R.id.edtEnterPassword)
    AnyEditTextView edtEnterPassword;
    @BindView(R.id.edtConfirmPassword)
    AnyEditTextView edtConfirmPassword;
    @BindView(R.id.btnNext)
    Button btnNext;

    Unbinder unbinder;

    private String branchId = "";
    private boolean isEdit = false;

    public static CreateNewBranchFragment newInstance(String branchId, boolean isEdit) {

        Bundle args = new Bundle();

        CreateNewBranchFragment fragment = new CreateNewBranchFragment();
        args.putString(AppConstants._branchId, branchId);
        args.putBoolean(AppConstants._isEdit, isEdit);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        branchId = getArguments().getString(AppConstants._branchId);
        isEdit = getArguments().getBoolean(AppConstants._isEdit);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_new_branch, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (isEdit) {
            serviceHelper.enqueueCall(webService.getBranchProfile(branchId, prefHelper.getMerchant().getToken()), WebServiceConstants.getBranchData);
            edtEmail.setEnabled(false);
        } else {
            edtEmail.setEnabled(true);
        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        if (isEdit){
            titleBar.setSubHeading(getString(R.string.update_branch_account));
        }else{
            titleBar.setSubHeading(getString(R.string.create_new_branch_account));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private boolean validated() {

        if (edtBranchName.getText().toString().trim().equals("")) {
            edtBranchName.setError(getString(R.string.enter_branch_name));
            return false;
        } else if (edtEmail.getText().toString().trim().equals("") ||
                !Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()) {
            edtEmail.setError(getString(R.string.email_error));
            return false;
        } else if (edtEnterPassword.getText().toString().trim().equals("") || edtEnterPassword.getText().toString().length() < 6) {
            edtEnterPassword.setError(getString(R.string.password_error));
            return false;
        } else if (edtConfirmPassword.getText().toString().trim().equals("") || edtConfirmPassword.getText().toString().length() < 6) {
            edtConfirmPassword.setError(getString(R.string.password_error));
            return false;
        } else if (!edtEnterPassword.getText().toString().equals(edtConfirmPassword.getText().toString())) {
            edtConfirmPassword.setError(getString(R.string.confirm_pas_not_match));
            return false;
        } else {
            return true;
        }

    }


    @OnClick(R.id.btnNext)
    public void onViewClicked() {

        if (validated()) {

            if (isEdit) {
                serviceHelper.enqueueCall(webService.updateBranchProfile(branchId,
                        edtBranchName.getText().toString(), edtEmail.getText().toString(),
                        edtEnterPassword.getText().toString(), edtConfirmPassword.getText().toString(),
                        prefHelper.getMerchant().getToken()), WebServiceConstants.updateBranch);

            } else {
                serviceHelper.enqueueCall(webService.createMerchantBranch(
                        edtBranchName.getText().toString(), edtEmail.getText().toString(),
                        edtEnterPassword.getText().toString(), edtConfirmPassword.getText().toString(),
                        prefHelper.getMerchant().getToken()), WebServiceConstants.createBranch);
            }
        }
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.createBranch:
                UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.new_branch_created));
                //getDockActivity().replaceDockableFragment(BranchesEditListFragmnet.newInstance(), "BranchesEditListFragmnet");
                getMainActivity().popFragment();
                break;
            case WebServiceConstants.getBranchData:

                BranchEnt branchEnt = (BranchEnt) result;

                if (branchEnt != null) {
                    edtBranchName.setText(branchEnt.getFirstName().toString());
                    edtEmail.setText(branchEnt.getEmail().toString());
//                    edtEnterPassword.setText(branchEnt.getPassword().toString());
//                    edtConfirmPassword.setText(branchEnt.getPassword().toString());
                }

                break;
            case WebServiceConstants.updateBranch:
                UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.branch_updated));
                //getDockActivity().replaceDockableFragment(BranchesEditListFragmnet.newInstance(), "BranchesEditListFragmnet");
                getMainActivity().popFragment();
                break;
        }
    }
}
