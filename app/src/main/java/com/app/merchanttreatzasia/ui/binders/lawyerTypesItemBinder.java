package com.app.merchanttreatzasia.ui.binders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.app.merchanttreatzasia.R;
import com.app.merchanttreatzasia.entities.TypeEnt;
import com.app.merchanttreatzasia.interfaces.ItemClickListener;
import com.app.merchanttreatzasia.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.app.merchanttreatzasia.ui.views.AnyTextView;

/**
 * Created by khan_muhammad on 10/20/2017.
 */

public class lawyerTypesItemBinder extends RecyclerViewBinder<TypeEnt> {

    private ItemClickListener itemClickListener;


    public lawyerTypesItemBinder(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public RecyclerViewBinder.BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(final TypeEnt entity, final int position, Object viewHolder, Context context) {
        ViewHolder holder = (ViewHolder) viewHolder;

        bindItemId(position);

        holder.txtType.setText(entity.getType());

        if(entity.isClick()){
            //setBackground(holder.llroot,"",context);
            holder.iconType.setImageResource(R.drawable.crossicon);
            holder.llroot.setBackgroundResource(R.drawable.type_drawable_clicked);
        }else{
            //setBackground(holder.llroot,"",context);
            holder.iconType.setImageResource(R.drawable.tickicon);
            holder.llroot.setBackgroundResource(R.drawable.type_drawable);
        }

        holder.llroot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.itemClicked(entity,position);
            }
        });

    }

   /* private void setBackground(LinearLayout llroot, String myColor , Context context) {
        Drawable tempDrawable = ContextCompat.getDrawable(context, R.drawable.type_drawable);
        LayerDrawable bubble = (LayerDrawable) tempDrawable; //(cast to root element in xml)
        GradientDrawable solidColor = (GradientDrawable) bubble.findDrawableByLayerId(R.id.outerRectangle);
        solidColor.setColor(context.getColor(myColor));
        llroot.setBackground(tempDrawable);
    }*/

    @Override
    public void bindItemId(int position) {

    }


    public static class ViewHolder extends BaseViewHolder {

        private ImageView iconType;
        private AnyTextView txtType;
        private LinearLayout llroot;

        public ViewHolder(View view) {
            super(view);
            llroot = (LinearLayout) view.findViewById(R.id.llroot);
            iconType = (ImageView) view.findViewById(R.id.iconType);
            txtType = (AnyTextView) view.findViewById(R.id.txtType);

        }
    }

}
