package com.unlimiteduniverse.common.recyclerview.holder;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Irvin
 * on 2016/12/11.
 */

public abstract class RecyclerViewHolder<F, T extends RecyclerView.Adapter, V extends BaseViewHolder, K> {
    final private T adapter;

    final private F fragment;

    public RecyclerViewHolder(T adapter, F fragment) {
        this.adapter = adapter;
        this.fragment = fragment;
    }

    public T getAdapter() {
        return adapter;
    }

    public F getFragment() {
        return fragment;
    }

    public abstract void convert(V holder, K data, int position, boolean isScrolling);
}
