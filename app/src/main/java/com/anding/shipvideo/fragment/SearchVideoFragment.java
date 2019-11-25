package com.anding.shipvideo.fragment;

import android.os.Bundle;
import android.util.Log;

import com.anding.shipvideo.utils.LogUtils;
import com.wzq.leanback.app.SearchSupportFragment;
import com.wzq.leanback.widget.ArrayObjectAdapter;
import com.wzq.leanback.widget.ListRowPresenter;
import com.wzq.leanback.widget.ObjectAdapter;

public class SearchVideoFragment extends SearchSupportFragment implements  SearchSupportFragment.SearchResultProvider{
    private ArrayObjectAdapter mRowsAdapter;
    public static final String TAG = "SearchVideoFragment";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());

        setSearchResultProvider(this);
    }

    @Override
    public boolean onQueryTextChange(String s) {
        Log.d(TAG,"onQueryTextChange : "+s);
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        Log.d(TAG,"onQueryTextSubmit : "+s);
        return false;
    }

    @Override
    public ObjectAdapter getResultsAdapter() {
        LogUtils.d(TAG, "getResultsAdapter");
        LogUtils.d(TAG, mRowsAdapter.toString());

        // It should return search result here,
        // but static Movie Item list will be returned here now for practice.
//        ArrayList<Movie> mItems = MovieProvider.getMovieItems();
//        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new CardPresenter());
//        listRowAdapter.addAll(0, mItems);
//        HeaderItem header = new HeaderItem("Search results");
//        mRowsAdapter.add(new ListRow(header, listRowAdapter));

        return mRowsAdapter;
    }


}
