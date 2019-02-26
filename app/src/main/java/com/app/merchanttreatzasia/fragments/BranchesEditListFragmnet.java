package com.app.merchanttreatzasia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.app.merchanttreatzasia.R;
import com.app.merchanttreatzasia.entities.BranchEnt;
import com.app.merchanttreatzasia.entities.NotificationEnt;
import com.app.merchanttreatzasia.fragments.abstracts.BaseFragment;
import com.app.merchanttreatzasia.global.WebServiceConstants;
import com.app.merchanttreatzasia.helpers.DialogHelper;
import com.app.merchanttreatzasia.helpers.UIHelper;
import com.app.merchanttreatzasia.interfaces.ItemsClickListener;
import com.app.merchanttreatzasia.ui.adapters.ArrayListAdapter;
import com.app.merchanttreatzasia.ui.binders.BinderBranchesList;
import com.app.merchanttreatzasia.ui.views.AnyTextView;
import com.app.merchanttreatzasia.ui.views.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;



public class BranchesEditListFragmnet extends BaseFragment implements ItemsClickListener {


    @BindView(R.id.lvBranches)
    ListView lvBranches;
    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    @BindView(R.id.btnCreateBranch)
    Button btnCreateBranch;
    Unbinder unbinder;

    private ArrayListAdapter<BranchEnt> adapter;

    public static BranchesEditListFragmnet newInstance() {
        return new BranchesEditListFragmnet();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<>(getDockActivity(), new BinderBranchesList(getDockActivity(),prefHelper,this));

    }

    public void bindData(ArrayList<BranchEnt> branchEntList) {

        if (branchEntList.size() <= 0) {
            txtNoData.setVisibility(View.VISIBLE);
            lvBranches.setVisibility(View.GONE);
        } else {
            txtNoData.setVisibility(View.GONE);
            lvBranches.setVisibility(View.VISIBLE);
        }

        adapter.clearList();
        lvBranches.setAdapter(adapter);
        adapter.addAll(branchEntList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.getBranches:
                bindData((ArrayList<BranchEnt>) result);
                break;
            case WebServiceConstants.deleteBranche:
                UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.branch_deleted));
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_branches, container, false);


        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (prefHelper.getBranchSort().equals("")) {
            serviceHelper.enqueueCall(webService.getBranchList(prefHelper.getMerchant().getToken()), WebServiceConstants.getBranches);
        } else {
            serviceHelper.enqueueCall(webService.getBranchListSort(prefHelper.getBranchSort(), prefHelper.getMerchant().getToken()), WebServiceConstants.getBranches);
        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.create_branch_account));
        titleBar.showFilterButtonRight(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDockActivity().replaceDockableFragment(BranchFilterFragment.newInstance(), "BranchFilterFragment");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnCreateBranch)
    public void onViewClicked() {
        getDockActivity().replaceDockableFragment(CreateNewBranchFragment.newInstance(null,false), "CreateNewBranchFragment");
    }

    @Override
    public void editClick(final BranchEnt branchEnt) {
        getDockActivity().replaceDockableFragment(CreateNewBranchFragment.newInstance(branchEnt.getId()+"",true), "CreateNewBranchFragment");
    }

    @Override
    public void deleteClick(final BranchEnt branchEnt) {

        final DialogHelper dialogHelper = new DialogHelper(getMainActivity());
        dialogHelper.initLogoutDialog(R.layout.confirm_dialog, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogHelper.hideDialog();
                serviceHelper.enqueueCall(webService.deleteBranch(branchEnt.getId()+"", prefHelper.getMerchant().getToken()), WebServiceConstants.deleteBranche);
                //getDockActivity().replaceDockableFragment(BranchesEditListFragmnet.newInstance(), "BranchesEditListFragmnet");
                serviceHelper.enqueueCall(webService.getBranchList(prefHelper.getMerchant().getToken()), WebServiceConstants.getBranches);
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogHelper.hideDialog();
            }
        });

        dialogHelper.showDialog();
    }
}
