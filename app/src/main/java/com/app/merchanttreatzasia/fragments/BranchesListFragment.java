package com.app.merchanttreatzasia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.merchanttreatzasia.R;
import com.app.merchanttreatzasia.entities.BranchEnt;
import com.app.merchanttreatzasia.fragments.abstracts.BaseFragment;
import com.app.merchanttreatzasia.global.WebServiceConstants;
import com.app.merchanttreatzasia.ui.adapters.ArrayListAdapter;
import com.app.merchanttreatzasia.ui.binders.BinderBranchHistory;
import com.app.merchanttreatzasia.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class BranchesListFragment extends BaseFragment {


    Unbinder unbinder;
    @BindView(R.id.lvBranches)
    ListView lvBranches;

    private ArrayListAdapter<BranchEnt> adapter;

    ArrayList<BranchEnt> branchEntList;

    public static BranchesListFragment newInstance() {
        return new BranchesListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<>(getDockActivity(), new BinderBranchHistory(getDockActivity(), prefHelper));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_branches_list, container, false);

        unbinder = ButterKnife.bind(this, view);

        if (prefHelper.getBranchSort().equals("")) {
            serviceHelper.enqueueCall(webService.getHistoryList(prefHelper.getMerchant().getToken()), WebServiceConstants.getBranches);
        } else {
            serviceHelper.enqueueCall(webService.getHistoryListSort(prefHelper.getBranchSort(), prefHelper.getMerchant().getToken()), WebServiceConstants.getBranches);
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        lvBranches.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    getDockActivity().replaceDockableFragment(MyHistoryFragment.newInstance(), "MyHistoryFragment");

                } else {
                    getDockActivity().replaceDockableFragment(BranchHistoryFragment.newInstance(branchEntList.get(position).getId() + "", branchEntList.get(position).getFirstName()), "BranchHistoryFragment");
                }
            }
        });
    }

    public void bindData() {

        adapter.clearList();
        lvBranches.setAdapter(adapter);
        adapter.addAll(branchEntList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {

        branchEntList = (ArrayList<BranchEnt>) result;
        bindData();
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.history));
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
}
