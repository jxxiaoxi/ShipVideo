/*
 * Copyright (C) 2017 The Android Open Source Project
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

package com.anding.shipvideo.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;

import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.anding.shipvideo.activity.SearchActivity;
import com.anding.shipvideo.activity.VideosActivity;
import com.anding.shipvideo.data.Category;
import com.anding.shipvideo.manager.GlideBackgroundManager;
import com.anding.shipvideo.presenter.CardPresenter;
import com.anding.shipvideo.data.CategoryList;
import com.anding.shipvideo.R;
import com.anding.shipvideo.presenter.IconHeaderItemPresenter;
import com.anding.shipvideo.utils.Constants;

import java.util.List;
import java.util.Timer;

import com.anding.shipvideo.utils.LogUtils;
import com.wzq.leanback.app.BackgroundManager;
import com.wzq.leanback.app.BrowseFragment;
import com.wzq.leanback.widget.ArrayObjectAdapter;
import com.wzq.leanback.widget.HeaderItem;
import com.wzq.leanback.widget.ListRow;
import com.wzq.leanback.widget.ListRowPresenter;
import com.wzq.leanback.widget.OnItemViewClickedListener;
import com.wzq.leanback.widget.OnItemViewSelectedListener;
import com.wzq.leanback.widget.Presenter;
import com.wzq.leanback.widget.PresenterSelector;
import com.wzq.leanback.widget.Row;
import com.wzq.leanback.widget.RowPresenter;

public class MainFragment extends BrowseFragment {
    private static final String TAG = "MainFragment";

    private static final int BACKGROUND_UPDATE_DELAY = 300;
    private static final int GRID_ITEM_WIDTH = 200;
    private static final int GRID_ITEM_HEIGHT = 200;
    private static final int NUM_ROWS = 4;
    private static final int NUM_COLS = 4;

    private final Handler mHandler = new Handler();
    private Drawable mDefaultBackground;
    private DisplayMetrics mMetrics;
    private Timer mBackgroundTimer;
    private String mBackgroundUri;
    private BackgroundManager mBackgroundManager;
    private static GlideBackgroundManager picassoBackgroundManager = null;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onActivityCreated(savedInstanceState);

        setupUIElements();

        loadRows();

        setupEventListeners();
        setMianBg();
    }

    private void setMianBg() {
        picassoBackgroundManager = new GlideBackgroundManager(getActivity());
        // picassoBackgroundManager.updateBackgroundWithDelay("http://heimkehrend.raindrop.jp/kl-hacker/wp-content/uploads/2014/10/RIMG0656.jpg");
        picassoBackgroundManager.updateBackgroundWithDelay("error");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mBackgroundTimer) {
            LogUtils.d(TAG, "onDestroy: " + mBackgroundTimer.toString());
            mBackgroundTimer.cancel();
        }
    }

    private void loadRows() {
        ArrayObjectAdapter rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        CardPresenter cardPresenter = new CardPresenter();
        //加载行业分类
        initTradeCategoryRows(rowsAdapter, cardPresenter);
        //加载安全类别分类
        initSecurityCategoryRows(rowsAdapter, cardPresenter);
        //加载工种分类
        initRegionCategoryRows(rowsAdapter, cardPresenter);
        //加载综合分类
        initColligateCategoryRows(rowsAdapter, cardPresenter);

    }

    private void initTradeCategoryRows(ArrayObjectAdapter rowsAdapter, CardPresenter cardPresenter) {
        List<Category> tradeList = CategoryList.setupTradeCategorys();

        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);
        if (tradeList == null) {
            return;
        }
        for (int j = 0; j < tradeList.size(); j++) {
            listRowAdapter.add(tradeList.get(j));
        }
        HeaderItem header = new HeaderItem(Constants.CATEGORY_TRADE, CategoryList.HEADER_CATEGORYS[Constants.CATEGORY_TRADE - 1]);
        rowsAdapter.add(new ListRow(header, listRowAdapter));
        setAdapter(rowsAdapter);
    }

    private void initSecurityCategoryRows(ArrayObjectAdapter rowsAdapter, CardPresenter cardPresenter) {
        List<Category> securityList = CategoryList.setupSecurityCategorys();

        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);
        if (securityList == null) {
            return;
        }
        for (int j = 0; j < securityList.size(); j++) {
            listRowAdapter.add(securityList.get(j));
        }
        HeaderItem header = new HeaderItem(Constants.CATEGORY_TIME, CategoryList.HEADER_CATEGORYS[Constants.CATEGORY_TIME - 1]);
        rowsAdapter.add(new ListRow(header, listRowAdapter));
        setAdapter(rowsAdapter);
    }

    private void initRegionCategoryRows(ArrayObjectAdapter rowsAdapter, CardPresenter cardPresenter) {
        List<Category> regionList = CategoryList.setupRegionCategorys();
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);
        if (regionList == null) {
            return;
        }
        for (int j = 0; j < regionList.size(); j++) {
            listRowAdapter.add(regionList.get(j));
        }
        HeaderItem header = new HeaderItem(Constants.CATEGORY_REGION, CategoryList.HEADER_CATEGORYS[Constants.CATEGORY_REGION - 1]);
        rowsAdapter.add(new ListRow(header, listRowAdapter));
        setAdapter(rowsAdapter);
    }

    private void initColligateCategoryRows(ArrayObjectAdapter rowsAdapter, CardPresenter cardPresenter) {
        List<Category> colligateList = CategoryList.setupColligateCategorys();
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);
        if (colligateList == null) {
            return;
        }
        for (int j = 0; j < colligateList.size(); j++) {
            listRowAdapter.add(colligateList.get(j));
        }
        HeaderItem header = new HeaderItem(listRowAdapter.size(), CategoryList.HEADER_CATEGORYS[Constants.CATEGORY_COLLIGATE - 1]);
        rowsAdapter.add(new ListRow(header, listRowAdapter));
        setAdapter(rowsAdapter);
    }

    private void setupUIElements() {
        setTitle(getString(R.string.browse_title)); // Badge, when set, takes precedent
        // over title
        setHeadersState(HEADERS_ENABLED);//设置侧边栏显示状态 enabled 可见
        setHeadersTransitionOnBackEnabled(true);
        //getTitleView().setBackgroundColor(getResources().getColor(R.color.red));//整个上面的tietle区
        // set fastLane (or headers) background color
        setBrandColor(ContextCompat.getColor(getActivity(), R.color.fastlane_background)); //设置快速通道（侧边栏）背景
        // set search icon color
        setSearchAffordanceColor(ContextCompat.getColor(getActivity(), R.color.search_opaque));//搜索图标颜色
//客制化
//        setHeaderPresenterSelector(new PresenterSelector() {
//            @Override
//            public Presenter getPresenter(Object o) {
//                return new IconHeaderItemPresenter();
//            }
//        });
    }

    private void setupEventListeners() {
        setOnSearchClickedListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });

        setOnItemViewClickedListener(new ItemViewClickedListener());
        setOnItemViewSelectedListener(new ItemViewSelectedListener());
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {
            LogUtils.d(TAG, "onItemClicked ==> " + row.getId() + " -->" + row.getHeaderItem().getId() + " ---> " + row.getHeaderItem().getName());
            if (item instanceof Category) {
                Category category = (Category) item;

                LogUtils.d(TAG, "onItemClicked: " + category.toString());
                Intent intent = new Intent(getActivity(), VideosActivity.class);
                intent.putExtra(Constants.CATEGORY_VALUE, category.getValue());
                intent.putExtra(Constants.CATEGORY_DESCRIPTION, category.getDescription());
                getActivity().startActivity(intent);
            } else if (item instanceof String) {
                if (((String) item).contains(getString(R.string.error_fragment))) {
                    //Intent intent = new Intent(getActivity(), BrowseErrorActivity.class);
                    // startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), ((String) item), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private final class ItemViewSelectedListener implements OnItemViewSelectedListener {
        @Override
        public void onItemSelected(
                Presenter.ViewHolder itemViewHolder,
                Object item,
                RowPresenter.ViewHolder rowViewHolder,
                Row row) {
            LogUtils.d(TAG, "ItemViewSelectedListener" + row.getId() + "-->" + row.getHeaderItem().getName() + "  " + row.getHeaderItem().getId());
            if (item instanceof Category) {
                // mBackgroundUri = ((Category) item).getBackgroundImageUrl();
                //  startBackgroundTimer();
            }
        }
    }


//    private class GridItemPresenter extends Presenter {
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup parent) {
//            TextView view = new TextView(parent.getContext());
//            view.setLayoutParams(new ViewGroup.LayoutParams(GRID_ITEM_WIDTH, GRID_ITEM_HEIGHT));
//            view.setFocusable(true);
//            view.setFocusableInTouchMode(true);
//            view.setBackgroundColor(
//                    ContextCompat.getColor(getActivity(), R.color.default_background));
//            view.setTextColor(Color.WHITE);
//            view.setGravity(Gravity.CENTER);
//            return new ViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(ViewHolder viewHolder, Object item) {
//            ((TextView) viewHolder.view).setText((String) item);
//        }
//
//        @Override
//        public void onUnbindViewHolder(ViewHolder viewHolder) {
//        }
//    }

}
