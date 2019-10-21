package com.anding.shipvideo.fragment;

import android.os.Bundle;
import android.support.v17.leanback.app.SearchSupportFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.ObjectAdapter;

import com.anding.shipvideo.utils.LogUtils;

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
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
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
