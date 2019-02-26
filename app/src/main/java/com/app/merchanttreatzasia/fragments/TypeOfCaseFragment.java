package com.app.merchanttreatzasia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.merchanttreatzasia.R;
import com.app.merchanttreatzasia.entities.TypeEnt;
import com.app.merchanttreatzasia.fragments.abstracts.BaseFragment;
import com.app.merchanttreatzasia.helpers.EqualSpacingItemDecoration;
import com.app.merchanttreatzasia.interfaces.ItemClickListener;
import com.app.merchanttreatzasia.ui.adapters.RecyclerViewAdapter;
import com.app.merchanttreatzasia.ui.binders.lawyerTypesItemBinder;
import com.app.merchanttreatzasia.ui.views.TitleBar;
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by khan_muhammad on 10/20/2017.
 */

public class TypeOfCaseFragment extends BaseFragment implements ItemClickListener {


    @BindView(R.id.btnNext)
    Button btnNext;
    @BindView(R.id.btnSkip)
    Button btnSkip;
    Unbinder unbinder;
    @BindView(R.id.rvLawyerTypes)
    RecyclerView rvLawyerTypes;

    private List<TypeEnt> dataCollection;
    private RecyclerViewAdapter<TypeEnt> rvAdapter;
    GridLayoutManager layoutManager;

    public static TypeOfCaseFragment newInstance() {
        return new TypeOfCaseFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_type_of_case, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindData();

    }

    public void bindData() {

        dataCollection = new ArrayList<>();
        dataCollection.add(new TypeEnt("Cvil Case", true));
        dataCollection.add(new TypeEnt("Criminal Case", false));
        dataCollection.add(new TypeEnt("Bail Application Case", true));
        dataCollection.add(new TypeEnt("Cvil Case", true));
        dataCollection.add(new TypeEnt("Criminal Case", false));
        dataCollection.add(new TypeEnt("Bail Application Case", true));


        FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
        flowLayoutManager.setAutoMeasureEnabled(true);
        flowLayoutManager.maxItemsPerLine(2);

        rvAdapter = new RecyclerViewAdapter<>(dataCollection, new lawyerTypesItemBinder(this), getDockActivity(), R.layout.lawyer_types_item);
        rvLawyerTypes.setLayoutManager(flowLayoutManager);
        rvLawyerTypes.addItemDecoration(new EqualSpacingItemDecoration(16));
        rvLawyerTypes.setAdapter(rvAdapter);
        rvAdapter.notifyDataSetChanged();

    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.find_a_lawyer));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @OnClick({R.id.btnNext, R.id.btnSkip})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.btnNext:

                getDockActivity().replaceDockableFragment(BudgetFragment.newInstance(), BudgetFragment.class.getSimpleName());
                break;

            case R.id.btnSkip:

                getDockActivity().replaceDockableFragment(BudgetFragment.newInstance(), BudgetFragment.class.getSimpleName());
                break;
        }
    }


    @Override
    public void itemClicked(TypeEnt servicesEnt, int position) {


        if (dataCollection != null && dataCollection.contains(servicesEnt)) {
            int Position = dataCollection.indexOf(servicesEnt);

            if (dataCollection.get(Position).isClick()) {
                dataCollection.get(Position).setClick(false);
            } else {
                dataCollection.get(Position).setClick(true);
            }

            rvAdapter.notifyItemChanged(Position);
        }

    }

}
