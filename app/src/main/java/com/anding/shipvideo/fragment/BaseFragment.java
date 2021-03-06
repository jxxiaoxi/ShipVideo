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

package com.anding.shipvideo.fragment;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.owen.focus.AbsFocusBorder;
import com.owen.focus.FocusBorder;
import com.owen.tvrecyclerview.widget.TvRecyclerView;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * @author ZhouSuQiang
 */
public abstract class BaseFragment extends Fragment {
//    private TextView mPositionText;
//    private TextView mCountText;
//    private TextView mStateText;
    protected Toast mToast;
    protected FocusBorder mFocusBorder;

    private RecyclerView mRecyclerView;
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
            updateState(scrollState);
        }

        @Override
        public void onScrolled(RecyclerView rv, int i, int i2) {
            updatePosition((TvRecyclerView) rv);
        }

    };

    abstract int getLayoutId();

    protected void onMoveFocusBorder(View focusedView, float scale) {
        if(null != mFocusBorder) {
            mFocusBorder.onFocus(focusedView, FocusBorder.OptionsFactory.get(scale, scale));
        }
    }

    protected void onMoveFocusBorder(View focusedView, float scale, float roundRadius) {
        if(null != mFocusBorder) {
            mFocusBorder.onFocus(focusedView, FocusBorder.OptionsFactory.get(scale, scale, roundRadius));
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(getActivity() instanceof FocusBorderHelper) {
           // mFocusBorder = ((FocusBorderHelper)getActivity()).getFocusBorder();
            mFocusBorder = initBorder();
        }
    }

    private FocusBorder initBorder(){
        /** 颜色焦点框 */
        FocusBorder mColorFocusBorder = new FocusBorder.Builder().asColor()
                //阴影宽度(方法shadowWidth(18f)也可以设置阴影宽度)
                .shadowWidth(TypedValue.COMPLEX_UNIT_DIP, 2f)
                //阴影颜色
                .shadowColor(Color.parseColor("#ffffff"))
                //边框宽度(方法borderWidth(2f)也可以设置边框宽度)
                .borderWidth(TypedValue.COMPLEX_UNIT_PX, 0.2f)
                //边框颜色
                .borderColor(Color.parseColor("#ffffff"))
                //padding值
                .padding(0f)
                //动画时长
                .animDuration(300)
                //不要闪光动画
                .noShimmer()
                //闪光颜色
                .shimmerColor(Color.parseColor("#66FFFFFF"))
                //闪光动画时长
                .shimmerDuration(1000)
                //不要呼吸灯效果
                .noBreathing()
                //呼吸灯效果时长
                .breathingDuration(3000)
                //边框动画模式
                .animMode(AbsFocusBorder.Mode.NOLL)
                .build(this);

        return  mColorFocusBorder;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(getLayoutId(), container, false);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Activity activity = getActivity();

        mToast = Toast.makeText(activity, "", Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.CENTER, 0, 0);

//        mPositionText = (TextView) view.getRootView().findViewById(R.id.position);
//        mCountText = (TextView) view.getRootView().findViewById(R.id.count);
//        mStateText = (TextView) view.getRootView().findViewById(R.id.state);
        updateState(SCROLL_STATE_IDLE);
    }

    public void showToast(String str) {
        mToast.setText(str);
        mToast.show();
    }

    public void showToast(int resId) {
        mToast.setText(resId);
        mToast.show();
    }

    protected void setScrollListener(RecyclerView recyclerView) {
        if(mRecyclerView != recyclerView) {
            if(null != mRecyclerView) {
                mRecyclerView.removeOnScrollListener(mOnScrollListener);
            }
            recyclerView.addOnScrollListener(mOnScrollListener);
            mRecyclerView = recyclerView;
        }
    }

    private void updatePosition(TvRecyclerView rv) {
//        if(null != mPositionText && null != mCountText) {
//            final int count = rv.getChildCount();
//            final int first = rv.getFirstVisiblePosition();
//            mPositionText.setText("First: " + first);
//            mCountText.setText("Count: " + count);
//        }
    }

    private void updateState(int scrollState) {
//        if(null != mStateText) {
//            String stateName = "Undefined";
//            switch (scrollState) {
//                case SCROLL_STATE_IDLE:
//                    stateName = "Idle";
//                    break;
//
//                case SCROLL_STATE_DRAGGING:
//                    stateName = "Dragging";
//                    break;
//
//                case SCROLL_STATE_SETTLING:
//                    stateName = "Flinging";
//                    break;
//            }
//
//            mStateText.setText(stateName);
//        }
    }

    public interface FocusBorderHelper {
        FocusBorder getFocusBorder();
    }
}
