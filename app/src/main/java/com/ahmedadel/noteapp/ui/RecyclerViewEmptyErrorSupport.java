package com.ahmedadel.noteapp.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Ahmed Adel on 3/28/18.
 * <p>
 * RecyclerViewEmptyErrorSupport is a subclass of recycle view that shows empty or error view when there is no data or
 * something went from API.
 */

public class RecyclerViewEmptyErrorSupport extends RecyclerView {

    private View emptyErrorView;
    final private AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            checkIfEmpty();
        }
    };

    public RecyclerViewEmptyErrorSupport(Context context) {
        super(context);
    }

    public RecyclerViewEmptyErrorSupport(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewEmptyErrorSupport(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    void checkIfEmpty() {
        if (emptyErrorView != null && getAdapter() != null) {
            final boolean emptyViewVisible = getAdapter().getItemCount() == 0;
            emptyErrorView.setVisibility(emptyViewVisible ? VISIBLE : GONE);
            setVisibility(emptyViewVisible ? GONE : VISIBLE);
        }
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        final Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(observer);
        }
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
        }
        checkIfEmpty();
    }

    public void setEmptyErrorView(View emptyView) {
        this.emptyErrorView = emptyView;
        checkIfEmpty();
    }
}
