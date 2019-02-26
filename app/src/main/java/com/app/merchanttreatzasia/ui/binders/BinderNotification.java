package com.app.merchanttreatzasia.ui.binders;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.app.merchanttreatzasia.R;
import com.app.merchanttreatzasia.entities.NotificationEnt;
import com.app.merchanttreatzasia.entities.NotificationGetEnt;
import com.app.merchanttreatzasia.global.AppConstants;
import com.app.merchanttreatzasia.helpers.BasePreferenceHelper;
import com.app.merchanttreatzasia.helpers.DateHelper;
import com.app.merchanttreatzasia.ui.viewbinders.abstracts.ViewBinder;
import com.app.merchanttreatzasia.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;


public class BinderNotification extends ViewBinder<NotificationGetEnt> {

    private Context context;
    private ImageLoader imageLoader;
    private BasePreferenceHelper preferenceHelper;

    public BinderNotification(Context context, BasePreferenceHelper prefHelper) {
        super(R.layout.fragment_notification_item);
        this.context = context;
        this.preferenceHelper = prefHelper;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(NotificationGetEnt entity, int position, int grpPosition, View view, Activity activity) {

        BinderNotification.ViewHolder viewHolder = (BinderNotification.ViewHolder) view.getTag();

        viewHolder.tv_date.setText(DateHelper.getFormatedDate(AppConstants.DateFormat_YMDHMS, AppConstants.DateFormat_DMY2,
                entity.getCreatedAt()));
        viewHolder.tv_time.setText(DateHelper.getFormatedDate(AppConstants.DateFormat_YMDHMS, AppConstants.DateFormat_HM,
                entity.getCreatedAt()));

        switch (preferenceHelper.getLanguage()) {
            case AppConstants.Eng:
                viewHolder.tv_msg.setText(entity.getMessage());
                break;
            case AppConstants.Malai:
                viewHolder.tv_msg.setText(entity.getMaMessage());
                break;
            case AppConstants.Indo:
                viewHolder.tv_msg.setText(entity.getInMessage());
                break;
            default:
                viewHolder.tv_msg.setText(entity.getMessage());
                break;
        }
        /*if(entity.getCreatedAt() != null && entity.getCreatedAt().length() > 0) {
            viewHolder.tv_date.setText(DateHelper.dateFormat(entity.getCreatedAt(), DateHelper.DATE_FORMAT, DateHelper.DATE_TIME_FORMAT));
            viewHolder.tv_time.setText(DateHelper.dateFormat(entity.getCreatedAt(), DateHelper.TIME_FORMAT, DateHelper.DATE_TIME_FORMAT));
        }*/
    }

    public static class ViewHolder extends BaseViewHolder {

        ImageView ivNotification;
        AnyTextView tv_msg;
        AnyTextView tv_date;
        AnyTextView tv_time;

        public ViewHolder(View view) {

            ivNotification = (ImageView) view.findViewById(R.id.ivNotification);
            tv_msg = (AnyTextView) view.findViewById(R.id.tv_msg);
            tv_date = (AnyTextView) view.findViewById(R.id.tv_date);
            tv_time = (AnyTextView) view.findViewById(R.id.tv_time);
        }
    }
}
