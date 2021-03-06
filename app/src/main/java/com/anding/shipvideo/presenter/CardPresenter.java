/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.anding.shipvideo.presenter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.anding.shipvideo.R;
import com.anding.shipvideo.data.Category;
import com.anding.shipvideo.utils.LogUtils;
import com.bumptech.glide.Glide;
import com.wzq.leanback.widget.BaseCardView;
import com.wzq.leanback.widget.ImageCardView;
import com.wzq.leanback.widget.Presenter;

/*
 * A CardPresenter is used to generate Views and bind Objects to them on demand.
 * It contains an Image CardView
 */
public class CardPresenter extends Presenter {
    private static final String TAG = "CardPresenter";

    private static int sSelectedBackgroundColor;
    private static int sDefaultBackgroundColor;
    private Drawable mDefaultCardImage;

    private static void updateCardBackgroundColor(ImageCardView view, boolean selected) {
        int color = selected ? sSelectedBackgroundColor : sDefaultBackgroundColor;
        // Both background colors should be set because the view's background is temporarily visible
        // during animations.
        view.setBackgroundColor(Color.RED);
        view.findViewById(R.id.info_field).setBackgroundColor(color);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        final Context context = parent.getContext();
        sDefaultBackgroundColor =
                ContextCompat.getColor(parent.getContext(), R.color.default_background);
        sSelectedBackgroundColor =
                ContextCompat.getColor(parent.getContext(), R.color.selected_background);
        mDefaultCardImage = ContextCompat.getDrawable(parent.getContext(), R.color.app_background_color);

        ImageCardView cardView =
                new ImageCardView(parent.getContext()) {
                    @Override
                    public void setSelected(boolean selected) {
                        updateCardBackgroundColor(this, selected);
                        super.setSelected(selected);
                    }
                };

        cardView.setFocusable(true);
        TextView title_= (TextView)cardView.findViewById(R.id.title_text);
        title_.setTextSize(32);//标题字体的大小
        TextView content= (TextView)cardView.findViewById(R.id.content_text);
        content.setTextSize(26);//标题字体的大小
        cardView.setFocusableInTouchMode(true);
        cardView.setInfoVisibility(BaseCardView.CARD_REGION_VISIBLE_ALWAYS);
        updateCardBackgroundColor(cardView, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        Category category = (Category) item;
        ImageCardView cardView = (ImageCardView) viewHolder.view;
        cardView.getMainImageView().setBackground(mDefaultCardImage);
        Context context = viewHolder.view.getContext();
        if (category.getCardImageUrl() != null) {
            cardView.setTitleText(category.getName());
            //  cardView.setContentText(Category.getStudio());
            cardView.setContentText(category.getDescription());

            cardView.setMainImageDimensions(context.getResources().getDimensionPixelSize(R.dimen.card_width), context.getResources().getDimensionPixelSize(R.dimen.card_height));
            Glide.with(viewHolder.view.getContext())
                    .load(category.getCardImageUrl())
                    .centerCrop()
                    .error(mDefaultCardImage)
                    .into(cardView.getMainImageView());
        }
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
        Log.d(TAG, "onUnbindViewHolder");
        ImageCardView cardView = (ImageCardView) viewHolder.view;
        // Remove references to images so that the garbage collector can free up memory
        cardView.setBadgeImage(null);
        cardView.setMainImage(null);
    }
}
