package com.anding.shipvideo.presenter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anding.shipvideo.R;
import com.anding.shipvideo.utils.LogUtils;
import com.wzq.leanback.widget.HeaderItem;
import com.wzq.leanback.widget.ListRow;
import com.wzq.leanback.widget.Presenter;
import com.wzq.leanback.widget.RowHeaderPresenter;

public class IconHeaderItemPresenter extends RowHeaderPresenter {
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup) {
//        mUnselectedAlpha = viewGroup.getResources()
//                .getFraction(R.fraction.lb_browse_header_unselect_alpha, 1, 1);
//        LayoutInflater inflater = (LayoutInflater) viewGroup.getContext()
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        View view = inflater.inflate(R.layout.item_header, null);
//        view.setAlpha(mUnselectedAlpha);
//        return new ViewHolder(view);


        mUnselectedAlpha = viewGroup.getResources()
                .getFraction(R.fraction.lb_browse_header_unselect_alpha, 1, 1);
        LayoutInflater inflater = (LayoutInflater) viewGroup.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_header, null);
        view.setAlpha(mUnselectedAlpha); // Initialize icons to be at half-opacity.
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        HeaderItem headerItem = ((ListRow) item).getHeaderItem();
        View rootView = viewHolder.view;
        rootView.setFocusable(true);

        ImageView iconView = (ImageView) rootView.findViewById(R.id.header_icon);
        Drawable icon = rootView.getResources().getDrawable(R.drawable.ic_app, null);
        iconView.setImageDrawable(icon);

        TextView label = (TextView) rootView.findViewById(R.id.header_label);
        LogUtils.d("liuwei","name : "+ headerItem.getName());
        label.setText(headerItem.getName());
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
        // no op
    }

    // TODO: This is a temporary fix. Remove me when leanback onCreateViewHolder no longer sets the
    // mUnselectAlpha, and also assumes the xml inflation will return a RowHeaderView.
    @Override
    protected void onSelectLevelChanged(RowHeaderPresenter.ViewHolder holder) {
        holder.view.setAlpha(mUnselectedAlpha + holder.getSelectLevel() *
                (1.0f - mUnselectedAlpha));
    }

    private float mUnselectedAlpha;
}
