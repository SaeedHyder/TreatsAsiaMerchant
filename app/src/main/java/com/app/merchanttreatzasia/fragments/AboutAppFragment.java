package com.app.merchanttreatzasia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.merchanttreatzasia.R;
import com.app.merchanttreatzasia.entities.AboutUsEnt;
import com.app.merchanttreatzasia.fragments.abstracts.BaseFragment;
import com.app.merchanttreatzasia.global.AppConstants;
import com.app.merchanttreatzasia.global.WebServiceConstants;
import com.app.merchanttreatzasia.ui.views.AnyTextView;
import com.app.merchanttreatzasia.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AboutAppFragment extends BaseFragment {

    @BindView(R.id.tv_body)
    AnyTextView tvBody;
    Unbinder unbinder;

    public static AboutAppFragment newInstance() {
        return new AboutAppFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_app, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        serviceHelper.enqueueCall(webService.aboutUs(AppConstants.AboutUs, prefHelper.getMerchant().getToken()), WebServiceConstants.aboutus);
    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.about_app));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.aboutus:
                AboutUsEnt ent = (AboutUsEnt) result;

                /*if(prefHelper.getLanguage().equalsIgnoreCase(AppConstants.Eng)){
                    tvBody.setText(Html.fromHtml(ent.getBody()));
                }
                else if(prefHelper.getLanguage().equalsIgnoreCase(AppConstants.Malai)){
                    tvBody.setText(Html.fromHtml(ent.getBody()));
                }
                else if(prefHelper.getLanguage().equalsIgnoreCase(AppConstants.Indo)){
                    tvBody.setText(Html.fromHtml(ent.getBody()));
                }*/

                tvBody.setText(Html.fromHtml(ent.getBody()));
                break;
        }
    }


}
