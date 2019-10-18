/*
 * Copyright (C) 2014 Lucas Rocha
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.anding.shipvideo.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.anding.shipvideo.R;
import com.anding.shipvideo.ShipvideoApplication;
import com.anding.shipvideo.data.ItemBean;
import com.bumptech.glide.Glide;
import com.owen.adapter.CommonRecyclerViewAdapter;
import com.owen.adapter.CommonRecyclerViewHolder;

public class GridAdapter extends CommonRecyclerViewAdapter<ItemBean> {
    public GridAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.grid_item;
    }

    @Override
    public void onBindItemHolder(CommonRecyclerViewHolder helper, ItemBean item, int position) {
        helper.getHolder()
                .setText(R.id.title, String.valueOf(position));
        showImage(helper, R.id.image, item.imgUrl);
    }
    
    public void showImage(CommonRecyclerViewHolder helper, int viewId, String url) {
        ImageView imageView = helper.getHolder().getView(viewId);
        Log.d("liuwei","cccc : "+ ShipvideoApplication.getContext());
        Glide.with(ShipvideoApplication.getContext()).load(url).into(imageView);
    }
}
