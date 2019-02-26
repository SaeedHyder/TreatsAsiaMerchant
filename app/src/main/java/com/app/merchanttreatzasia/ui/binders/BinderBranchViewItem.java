package com.app.merchanttreatzasia.ui.binders;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.app.merchanttreatzasia.R;
import com.app.merchanttreatzasia.entities.BranchEnt;
import com.app.merchanttreatzasia.entities.MerchantRedeemHistoryEnt;
import com.app.merchanttreatzasia.helpers.BasePreferenceHelper;
import com.app.merchanttreatzasia.ui.viewbinders.abstracts.ViewBinder;
import com.app.merchanttreatzasia.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by khan_muhammad on 11/15/2017.
 */

public class BinderBranchViewItem extends ViewBinder<MerchantRedeemHistoryEnt> {

    private Context context;
    private ImageLoader imageLoader;
    private BasePreferenceHelper preferenceHelper;

    public BinderBranchViewItem(Context context, BasePreferenceHelper prefHelper) {
        super(R.layout.branch_view_item);
        this.context = context;
        this.preferenceHelper = prefHelper;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(MerchantRedeemHistoryEnt entity, int position, int grpPosition, View view, Activity activity) {

        BinderBranchViewItem.ViewHolder viewHolder = (BinderBranchViewItem.ViewHolder) view.getTag();

        /*viewHolder.txtPoints.setText(position+1+"");
        viewHolder.txtVoucherDetail.setText(entity.getFirstName());*/

    }

    public static class ViewHolder extends BaseViewHolder {

        AnyTextView txtPoints;
        AnyTextView tv_date;
        AnyTextView tv_time;
        AnyTextView txtVoucherDetail;

        public ViewHolder(View view) {

            txtPoints = (AnyTextView) view.findViewById(R.id.txtPoints);
            tv_date = (AnyTextView) view.findViewById(R.id.tv_date);
            tv_time = (AnyTextView) view.findViewById(R.id.tv_time);
            txtVoucherDetail = (AnyTextView) view.findViewById(R.id.txtVoucherDetail);
        }
    }
}
