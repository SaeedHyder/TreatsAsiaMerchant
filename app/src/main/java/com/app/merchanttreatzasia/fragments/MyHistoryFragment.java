package com.app.merchanttreatzasia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.merchanttreatzasia.R;
import com.app.merchanttreatzasia.entities.MerchantRedeemHistoryEnt;
import com.app.merchanttreatzasia.entities.MyHistoryEnt;
import com.app.merchanttreatzasia.fragments.abstracts.BaseFragment;
import com.app.merchanttreatzasia.global.WebServiceConstants;
import com.app.merchanttreatzasia.ui.adapters.ArrayListAdapter;
import com.app.merchanttreatzasia.ui.binders.BinderMyHistoryItem;
import com.app.merchanttreatzasia.ui.views.AnyTextView;
import com.app.merchanttreatzasia.ui.views.TitleBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class MyHistoryFragment extends BaseFragment {

    @BindView(R.id.lvHistory)
    ListView lvHistory;
    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    Unbinder unbinder;
    private List<MyHistoryEnt> myHistoryEntList;
    private List<MerchantRedeemHistoryEnt> merchantRedeemHistoryEnts;
    private List<MerchantRedeemHistoryEnt> merchantRedeemHistoryEntsDeleted;
    private ArrayListAdapter<MerchantRedeemHistoryEnt> adapter;
    int i;

    public static MyHistoryFragment newInstance() {
        return new MyHistoryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<>(getDockActivity(), new BinderMyHistoryItem(getDockActivity(), prefHelper));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_history, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //serviceHelper.enqueueCall(webService.getMerchantRedeemHistory(prefHelper.getMerchantId(),prefHelper.getSort(),prefHelper.getSearch()), WebServiceConstants.merchantredeemhistory);
        if (prefHelper.getSort().equals("")) {
            serviceHelper.enqueueCall(webService.getMerchantRedeemHistory(prefHelper.getMerchant().getToken()), WebServiceConstants.merchantredeemhistory);
        } else {
            serviceHelper.enqueueCall(webService.getMerchantRedeemHistorySort(prefHelper.getSort()/*, prefHelper.getSearch()*/, prefHelper.getMerchant().getToken()), WebServiceConstants.merchantredeemhistory);
        }

/*        lvHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getDockActivity().replaceDockableFragment(RedemptionDetailsFragment.newInstance(merchantRedeemHistoryEnts.get(position).getEvoucherId() + ""), "RedemptionDetailsFragment");
            }
        });*/
    }

    private void bindData(List<MerchantRedeemHistoryEnt> merchantRedeemHistoryEnts) {

        if (merchantRedeemHistoryEnts.size() <= 0) {
            txtNoData.setVisibility(View.VISIBLE);
            lvHistory.setVisibility(View.GONE);
        } else {
            txtNoData.setVisibility(View.GONE);
            lvHistory.setVisibility(View.VISIBLE);
        }

        adapter.clearList();
        lvHistory.setAdapter(adapter);
        adapter.addAll(merchantRedeemHistoryEnts);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.my_history));

        titleBar.showNotificationButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDockActivity().replaceDockableFragment(NotificationsFragment.newInstance(), "NotificationsFragment");
            }
        }, prefHelper.getNotificationCount());

        titleBar.showFilterButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDockActivity().replaceDockableFragment(FilterFragment.newInstance(), "FilterFragment");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.merchantredeemhistory:
                merchantRedeemHistoryEnts = (List<MerchantRedeemHistoryEnt>) result;

                if (merchantRedeemHistoryEnts.size() != 0) {
                    for (i = 0; i < merchantRedeemHistoryEnts.size(); i++) {
                        if (merchantRedeemHistoryEnts != null &&
                                merchantRedeemHistoryEnts.get(i).getMerchantDetail() != null &&
                                merchantRedeemHistoryEnts.get(i).getEvoucherDetail() != null &&
                                merchantRedeemHistoryEnts.get(i).getEvoucherDetail().getProductDetail() != null &&
                                merchantRedeemHistoryEnts.get(i).getUserDetail() != null) {
                            bindData(merchantRedeemHistoryEnts);
                        } else {
                            merchantRedeemHistoryEnts.remove(i);
                        }
                    }
                } else {

                    if (merchantRedeemHistoryEnts.size() <= 0) {
                        txtNoData.setVisibility(View.VISIBLE);
                        lvHistory.setVisibility(View.GONE);
                    } else {
                        txtNoData.setVisibility(View.GONE);
                        lvHistory.setVisibility(View.VISIBLE);
                    }
                }
                break;
        }
    }
}
