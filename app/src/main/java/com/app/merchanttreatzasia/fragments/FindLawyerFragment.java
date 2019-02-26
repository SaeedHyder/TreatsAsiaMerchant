package com.app.merchanttreatzasia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.app.merchanttreatzasia.R;
import com.app.merchanttreatzasia.fragments.abstracts.BaseFragment;
import com.app.merchanttreatzasia.interfaces.IFragmentChangeListner;
import com.app.merchanttreatzasia.ui.views.TitleBar;

import static com.app.merchanttreatzasia.activities.DockActivity.KEY_FRAG_FIRST;

/**
 * Created by khan_muhammad on 10/20/2017.
 */

public class FindLawyerFragment extends BaseFragment implements IFragmentChangeListner {


    public static String currentState = "";

    public static enum States
    {
        EnterLocation,
        TypeOfLawyer,
        TypeOfCase,
        Budget,
    }


    public static FindLawyerFragment newInstance() {

        return new FindLawyerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_lawyer, container, false);



        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currentState = States.EnterLocation.toString();
        //loadingFragment(currentState);

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

    /*public void loadingFragment(String State){
        if(State.equalsIgnoreCase(States.EnterLocation.toString())){
            currentState = States.EnterLocation.toString();
            ReplaceListViewFragment(FragmentEnterLocation.newInstance(this));
        }
        else if(State.equalsIgnoreCase(States.TypeOfLawyer.toString())){
            currentState = States.TypeOfLawyer.toString();
            ReplaceListViewFragment(TypeOfLawyerFragment.newInstance(this));
        }
        else if(State.equalsIgnoreCase(States.TypeOfCase.toString())){
            currentState = States.TypeOfCase.toString();
            ReplaceListViewFragment(TypeOfLawyerFragment.newInstance(this));
        }
        else if(State.equalsIgnoreCase(States.Budget.toString())){
            currentState = States.Budget.toString();
            ReplaceListViewFragment(TypeOfLawyerFragment.newInstance(this));
        }
    }*/

    private void ReplaceListViewFragment(BaseFragment frag) {

        FragmentTransaction transaction = getChildFragmentManager()
                .beginTransaction();

        transaction.replace(R.id.ll_container, frag);
        transaction
                .addToBackStack(
                        getChildFragmentManager().getBackStackEntryCount() == 0 ? KEY_FRAG_FIRST
                                : null).commit();
    }

    @Override
    public void onNextButtonClick(String State) {

        //loadingFragment(State);

    }

    @Override
    public void onSkipButtonClick(String State) {
        //loadingFragment(State);
    }
}
