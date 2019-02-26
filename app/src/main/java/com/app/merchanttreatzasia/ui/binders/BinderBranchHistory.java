package com.app.merchanttreatzasia.ui.binders;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.app.merchanttreatzasia.R;
import com.app.merchanttreatzasia.entities.BranchEnt;
import com.app.merchanttreatzasia.global.AppConstants;
import com.app.merchanttreatzasia.helpers.BasePreferenceHelper;
import com.app.merchanttreatzasia.ui.viewbinders.abstracts.ViewBinder;
import com.app.merchanttreatzasia.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BinderBranchHistory extends ViewBinder<BranchEnt> {

    private Context context;
    private ImageLoader imageLoader;
    private BasePreferenceHelper preferenceHelper;

    public BinderBranchHistory(Context context, BasePreferenceHelper prefHelper) {
        super(R.layout.branches_list_item);
        this.context = context;
        this.preferenceHelper = prefHelper;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(BranchEnt entity, int position, int grpPosition, View view, Activity activity) {

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        viewHolder.txtNo.setText(position + 1 + "");
        if (position == 0){
            if (preferenceHelper.getMerchant().getUserType().equalsIgnoreCase(AppConstants.MERCHANT)) {
                viewHolder.txtBranchName.setText(entity.getFirstName() + " (Admin)");
            } else {
                viewHolder.txtBranchName.setText(entity.getFirstName() + "");
            }
        } else {
            viewHolder.txtBranchName.setText(entity.getFirstName() + "");
        }
/*        if (entity.getRoleId().equals(3)){
            viewHolder.txtBranchName.setText(entity.getFirstName() + "(Admin)");
        }
        if (entity.getRoleId().equals(4)){
            viewHolder.txtBranchName.setText(entity.getFirstName() + "");
        }*/
    }


    static class ViewHolder extends BaseViewHolder  {
        @BindView(R.id.txtNo)
        AnyTextView txtNo;
        @BindView(R.id.txtBranchName)
        AnyTextView txtBranchName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
