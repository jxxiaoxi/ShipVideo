package com.anding.shipvideo.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.anding.shipvideo.R;
import com.anding.shipvideo.activity.PlaybackActivity;
import com.anding.shipvideo.activity.VideosActivity;
import com.anding.shipvideo.adapter.VideosAdapter;
import com.anding.shipvideo.data.Video;
import com.anding.shipvideo.manager.VideosManager;
import com.anding.shipvideo.utils.Constants;
import com.anding.shipvideo.utils.DisplayAdaptive;
import com.anding.shipvideo.utils.LogUtils;
import com.owen.adapter.CommonRecyclerViewAdapter;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;

import java.util.List;

public class VideosFragment extends BaseFragment {
    TvRecyclerView mRecyclerView;
    List<Video> videos;
    Intent mIntent;
    TextView tv_title,tv_content;
    private CommonRecyclerViewAdapter mAdapter;

    public static VideosFragment newInstance() {
        VideosFragment fragment = new VideosFragment();
        return fragment;
    }

    @Override
    int getLayoutId() {
        return R.layout.fragment_videos;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mIntent = ((VideosActivity) activity).getCategoryDetails();//通过强转成宿主activity，就可以获取到传递过来的数据
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.list_11);
        tv_title = view.findViewById(R.id.tv_title);
        tv_content = view.findViewById(R.id.tv_content);
        setListener();

//      final Drawable divider = getResources().getDrawable(R.drawable.divider);
//      mRecyclerView.addItemDecoration(new DividerItemDecoration(divider));
//      mRecyclerView.addItemDecoration(new SpacingItemDecoration(20, 20));
        // 通过Margins来设置布局的横纵间距(与addItemDecoration()方法可二选一)
        // 推荐使用此方法
        mRecyclerView.setSpacingWithMargins(30, 30);

        mAdapter = new VideosAdapter(getContext());
        //mAdapter.setDatas(ItemDatas.getDatas(60));
        //mAdapter.setDatas(VideosManager.getInstance().queryVideosByCategory(0));

        if(mIntent == null){
            Toast.makeText(getContext(),"视频加载失败",Toast.LENGTH_SHORT).show();
            return;
        }

        String categoryValue = mIntent.getStringExtra(Constants.CATEGORY_VALUE);
        String categoryDescription = mIntent.getStringExtra(Constants.CATEGORY_DESCRIPTION);
        tv_title.setText(categoryDescription + getContext().getString(R.string.category_video));

        LogUtils.d("VideosFragment"," categoryValue : "+categoryValue);
        videos =  VideosManager.getInstance().queryCategoryVideos(categoryValue);
        if(videos != null) {

            if(videos.size()>0) {
                mAdapter.setDatas(videos);
                mRecyclerView.setAdapter(mAdapter);
                tv_content.setVisibility(View.GONE);
            }else{
                tv_content.setVisibility(View.VISIBLE);
            }
        }
    }

    private void setListener() {
        setScrollListener(mRecyclerView);

        mRecyclerView.setOnItemListener(new SimpleOnItemListener() {

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
                float radius = DisplayAdaptive.getInstance().toLocalPx(0);
                onMoveFocusBorder(itemView, 1.1f, radius);
            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {
               // showToast("onItemClick::" + position);
                gotoPlay(position);
            }
        });

        mRecyclerView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mFocusBorder.setVisible(hasFocus);
            }
        });

//        mRecyclerView.setOnInBorderKeyEventListener(new TvRecyclerView.OnInBorderKeyEventListener() {
//            @Override
//            public boolean onInBorderKeyEvent(int direction, View focused) {
//                Log.i("zzzz", "onInBorderKeyEvent: ");
//                return false;
//            }
//        });

        /*mRecyclerView.setOnLoadMoreListener(new TvRecyclerView.OnLoadMoreListener() {
            @Override
            public boolean onLoadMore() {
                Log.i("@@@@", "onLoadMore: ");
                mRecyclerView.setLoadingMore(true); //正在加载数据
                mLayoutAdapter.appendDatas(); //加载数据
                mRecyclerView.setLoadingMore(false); //加载数据完毕
                return false; //是否还有更多数据
            }
        });*/
    }

    private void gotoPlay(int position) {
        if(videos!= null &&  position <= videos.size()) {
            Video video = videos.get(position);
            Intent intent = new Intent(getActivity(), PlaybackActivity.class);
            intent.putExtra("title",video.getVname());
            intent.putExtra("uri",video.getVid());
            intent.putExtra("pic",video.getVpic());
            startActivity(intent);
        }
    }

}
