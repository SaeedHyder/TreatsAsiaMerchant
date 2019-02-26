package com.app.merchanttreatzasia.ui.binders;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.app.merchanttreatzasia.R;
import com.app.merchanttreatzasia.activities.DockActivity;
import com.app.merchanttreatzasia.entities.BranchEnt;
import com.app.merchanttreatzasia.fragments.CreateNewBranchFragment;
import com.app.merchanttreatzasia.fragments.LanguageFragment;
import com.app.merchanttreatzasia.global.AppConstants;
import com.app.merchanttreatzasia.helpers.BasePreferenceHelper;
import com.app.merchanttreatzasia.helpers.DateHelper;
import com.app.merchanttreatzasia.interfaces.ItemsClickListener;
import com.app.merchanttreatzasia.ui.viewbinders.abstracts.ViewBinder;
import com.app.merchanttreatzasia.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;



public class BinderBranchesList extends ViewBinder<BranchEnt> {

    private Context context;
    private ImageLoader imageLoader;
    private BasePreferenceHelper preferenceHelper;
    ItemsClickListener itemsClickListener;

    public BinderBranchesList(Context context, BasePreferenceHelper prefHelper,ItemsClickListener itemsClickListener) {
        super(R.layout.fragment_edit_branches_item);
        this.context = context;
        this.preferenceHelper = prefHelper;
        this.itemsClickListener = itemsClickListener;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(final BranchEnt entity, int position, int grpPosition, View view, Activity activity) {

        BinderBranchesList.ViewHolder viewHolder = (BinderBranchesList.ViewHolder) view.getTag();

        viewHolder.txtNo.setText(position+1+"");
        viewHolder.txtBranchName.setText(entity.getFirstName());

        viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemsClickListener.editClick(entity);
            }
        });


        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemsClickListener.deleteClick(entity);
            }
        });

    }

    public static class ViewHolder extends BaseViewHolder {

        AnyTextView txtNo;
        AnyTextView txtBranchName;
        ImageButton btnEdit;
        ImageButton btnDelete;

        public ViewHolder(View view) {

            txtNo = (AnyTextView) view.findViewById(R.id.txtNo);
            txtBranchName = (AnyTextView) view.findViewById(R.id.txtBranchName);
            btnEdit = (ImageButton) view.findViewById(R.id.btnEdit);
            btnDelete = (ImageButton) view.findViewById(R.id.btnDelete);
        }
    }
}
