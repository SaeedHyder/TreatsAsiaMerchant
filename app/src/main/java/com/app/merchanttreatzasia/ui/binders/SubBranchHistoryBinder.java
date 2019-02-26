package com.app.merchanttreatzasia.ui.binders;

import android.app.Activity;
import android.view.View;

import com.app.merchanttreatzasia.R;
import com.app.merchanttreatzasia.activities.DockActivity;
import com.app.merchanttreatzasia.entities.Record;
import com.app.merchanttreatzasia.global.AppConstants;
import com.app.merchanttreatzasia.ui.viewbinders.abstracts.ViewBinder;
import com.app.merchanttreatzasia.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.app.merchanttreatzasia.helpers.DateHelper.getFormatedDate;


public class SubBranchHistoryBinder extends ViewBinder<Record> {

    DockActivity context;
    String timeDate;
    String splittedDate;
    String splittedTime;

    public SubBranchHistoryBinder(DockActivity context) {
        super(R.layout.items_sub_branch_history);
        this.context = context;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(Record entity, int position, int grpPosition, View view, Activity activity) {
        final ViewHolder viewHolder = (ViewHolder) view.getTag();

        timeDate = entity.getTime();
        String[] splited = timeDate.split("\\s");
        splittedDate = splited[0];
        splittedTime = splited[1];

        //splittedDate = getFormatedDate(AppConstants.DateFormat_YMD, AppConstants.DateFormat_DMY, splittedDate);

        viewHolder.tvCreditAmount.setText(entity.getPoint() + "");
        viewHolder.tvMsg.setText(entity.getMessage() + "");
        viewHolder.tvDate.setText(splittedDate + "");
        viewHolder.tvTime.setText(splittedTime + "");
    }


    static class ViewHolder extends BaseViewHolder{
        @BindView(R.id.tv_credit_type)
        AnyTextView tvCreditType;
        @BindView(R.id.tv_credit_amount)
        AnyTextView tvCreditAmount;
        @BindView(R.id.tv_msg)
        AnyTextView tvMsg;
        @BindView(R.id.tv_date)
        AnyTextView tvDate;
        @BindView(R.id.tv_time)
        AnyTextView tvTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
