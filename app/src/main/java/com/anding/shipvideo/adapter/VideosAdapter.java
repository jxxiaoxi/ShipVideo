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
import android.widget.ImageView;

import com.anding.shipvideo.R;
import com.anding.shipvideo.ShipvideoApplication;
import com.anding.shipvideo.data.Video;
import com.bumptech.glide.Glide;
import com.owen.adapter.CommonRecyclerViewAdapter;
import com.owen.adapter.CommonRecyclerViewHolder;

public class VideosAdapter extends CommonRecyclerViewAdapter<Video> {
    public VideosAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_videos;
    }

    @Override
    public void onBindItemHolder(CommonRecyclerViewHolder helper, Video item, int position) {
        //视频的标题
        helper.getHolder()
                .setText(R.id.tv_changed, "你大爷");
        //视频的图片
        showImage(helper, R.id.image, item.getPic());
    }

    
    public void showImage(CommonRecyclerViewHolder helper, int viewId, String url) {
        ImageView imageView = helper.getHolder().getView(viewId);
        Glide.with(ShipvideoApplication.getContext()).load(url).into(imageView);
    }
}
