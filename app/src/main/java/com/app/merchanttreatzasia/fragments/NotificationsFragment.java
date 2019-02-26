package com.app.merchanttreatzasia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.merchanttreatzasia.R;
import com.app.merchanttreatzasia.entities.NotificationEnt;
import com.app.merchanttreatzasia.entities.NotificationGetEnt;
import com.app.merchanttreatzasia.fragments.abstracts.BaseFragment;
import com.app.merchanttreatzasia.global.WebServiceConstants;
import com.app.merchanttreatzasia.ui.adapters.ArrayListAdapter;
import com.app.merchanttreatzasia.ui.binders.BinderNotification;
import com.app.merchanttreatzasia.ui.views.AnyTextView;
import com.app.merchanttreatzasia.ui.views.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;



public class NotificationsFragment extends BaseFragment {

    @BindView(R.id.lv_notification)
    ListView lvNotification;
    Unbinder unbinder;
    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;

    private List<NotificationGetEnt> notificationCollection;
    private ArrayList<NotificationGetEnt> notificationGetEnts;

    private ArrayListAdapter<NotificationGetEnt> adapter;

    public static NotificationsFragment newInstance() {
        return new NotificationsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<>(getDockActivity(), new BinderNotification(getDockActivity(),prefHelper));
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.getNotification:
                bindData((ArrayList<NotificationGetEnt>) result);
                break;
            case WebServiceConstants.NotificaitonCount:
                prefHelper.setNotificationCount(0);
                break;
        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.notification));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prefHelper.setNotificationCount(0);

        serviceHelper.enqueueCall(webService.getNotificationCount(prefHelper.getMerchant().getToken()), WebServiceConstants.NotificaitonCount);

        serviceHelper.enqueueCall(webService.getNotification(prefHelper.getMerchant().getToken()), WebServiceConstants.getNotification);

        lvNotification.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void bindData(ArrayList<NotificationGetEnt> result) {

        notificationCollection = new ArrayList<>();

        if (result.size() <= 0) {
            txtNoData.setVisibility(View.VISIBLE);
            lvNotification.setVisibility(View.GONE);
        } else {
            txtNoData.setVisibility(View.GONE);
            lvNotification.setVisibility(View.VISIBLE);

        }
        notificationCollection.addAll(result);
        adapter.clearList();
        lvNotification.setAdapter(adapter);
        adapter.addAll(notificationCollection);
        adapter.notifyDataSetChanged();
    }
}
