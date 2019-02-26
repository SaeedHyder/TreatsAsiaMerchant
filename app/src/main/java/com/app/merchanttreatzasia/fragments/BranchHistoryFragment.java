package com.app.merchanttreatzasia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.merchanttreatzasia.R;
import com.app.merchanttreatzasia.entities.Record;
import com.app.merchanttreatzasia.entities.SubBranchHistoryEnt;
import com.app.merchanttreatzasia.fragments.abstracts.BaseFragment;
import com.app.merchanttreatzasia.global.AppConstants;
import com.app.merchanttreatzasia.global.WebServiceConstants;
import com.app.merchanttreatzasia.ui.adapters.ArrayListAdapter;
import com.app.merchanttreatzasia.ui.binders.SubBranchHistoryBinder;
import com.app.merchanttreatzasia.ui.views.AnyTextView;
import com.app.merchanttreatzasia.ui.views.TitleBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class BranchHistoryFragment extends BaseFragment {

    @BindView(R.id.txtPoints)
    AnyTextView txtPoints;
    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    @BindView(R.id.lvBranchData)
    ListView lvBranchData;
    Unbinder unbinder;

    private String branchId = "";
    private String name = "";

    private SubBranchHistoryEnt subBranchHistoryEnt;
    private ArrayListAdapter<Record> adapter;
    int i;

    public static BranchHistoryFragment newInstance(String branchId, String name) {

        Bundle args = new Bundle();

        BranchHistoryFragment fragment = new BranchHistoryFragment();
        args.putString(AppConstants._branchId, branchId);
        args.putString(AppConstants._name, name);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        branchId = getArguments().getString(AppConstants._branchId);
        name = getArguments().getString(AppConstants._name);

        adapter = new ArrayListAdapter<>(getDockActivity(), new SubBranchHistoryBinder(getDockActivity()));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_branch_view, container, false);


        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (branchId != "") {
            if (prefHelper.getBranchDataSort().equals("")) {
                serviceHelper.enqueueCall(webService.getBranchHistory(
                        branchId, prefHelper.getMerchant().getToken()), WebServiceConstants.getBranches);
            } else {
                serviceHelper.enqueueCall(webService.getBranchHistorySort(
                        branchId, prefHelper.getBranchDataSort(), prefHelper.getMerchant().getToken()),
                        WebServiceConstants.getBranches);
            }
        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(name);
        titleBar.showFilterButtonRight(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDockActivity().replaceDockableFragment(BranchDataFilterFragment.newInstance(), "BranchDataFilterFragment");
            }
        });
    }

    private void bindData(List<Record> subBranchHistoryEnt) {

        if (subBranchHistoryEnt.size() <= 0) {
            txtNoData.setVisibility(View.VISIBLE);
            lvBranchData.setVisibility(View.GONE);
        } else {
            txtNoData.setVisibility(View.GONE);
            lvBranchData.setVisibility(View.VISIBLE);
        }

        adapter.clearList();
        lvBranchData.setAdapter(adapter);
        adapter.addAll(subBranchHistoryEnt);
        adapter.notifyDataSetChanged();
    }

    private void setData(String result) {

        if (result != null) {
            if (!result.equalsIgnoreCase("")) {
                txtPoints.setText(result);
            } else {
                txtPoints.setText("0");
            }
        }
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.getBranches:
                subBranchHistoryEnt = (SubBranchHistoryEnt) result;
                if (subBranchHistoryEnt != null && subBranchHistoryEnt.getRecord() != null) {
                    if (subBranchHistoryEnt.getRecord().size() != 0) {
                        bindData(subBranchHistoryEnt.getRecord());
                    } else {
                        txtNoData.setVisibility(View.VISIBLE);
                        lvBranchData.setVisibility(View.GONE);
                    }
                } else {
                    txtNoData.setVisibility(View.VISIBLE);
                    lvBranchData.setVisibility(View.GONE);
                }
                setData(subBranchHistoryEnt.getPointTotal());
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
