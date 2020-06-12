package com.anding.shipvideo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.TypedValue;

import com.anding.shipvideo.R;
import com.anding.shipvideo.fragment.VideosFragment;
import com.anding.shipvideo.fragment.BaseFragment;
import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.owen.focus.FocusBorder;
import com.umeng.analytics.MobclickAgent;

public class VideosActivity extends FragmentActivity implements BaseFragment.FocusBorderHelper{
    protected FocusBorder mFocusBorder;

    VideosFragment mFragment;
    Intent mIntent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);
        mIntent = getIntent();
        // 移动框
        if(null == mFocusBorder) {
            mFocusBorder = new FocusBorder.Builder()
                    .asColor()
                    .borderColorRes(R.color.actionbar_color)
                    .borderWidth(TypedValue.COMPLEX_UNIT_DIP, 3.2f)
                    .shadowColorRes(R.color.green_bright)
                    .shadowWidth(TypedValue.COMPLEX_UNIT_DIP, 22f)
                    .build(this);
        }
        if(mFragment == null) {
            mFragment = new VideosFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_Flayout, mFragment).commit();
        }

    }

    @Override
    public FocusBorder getFocusBorder() {
        return mFocusBorder;
    }

    public Intent getCategoryDetails(){
        return mIntent;
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
