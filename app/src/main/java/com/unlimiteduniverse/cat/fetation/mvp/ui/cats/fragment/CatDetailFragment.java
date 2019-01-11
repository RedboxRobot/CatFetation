package com.unlimiteduniverse.cat.fetation.mvp.ui.cats.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.unlimiteduniverse.cat.fetation.R;
import com.unlimiteduniverse.cat.fetation.mvp.base.SimpleFragment;

/**
 * @author Irvin
 * @time 2019/1/5 0005
 */
public class CatDetailFragment extends SimpleFragment {
    @Override
    public AppCompatActivity getAppActivity() {
        return (AppCompatActivity) getActivity();
    }

    @Override
    public int getRootViewId() {
        return R.layout.fragment_cat_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

    }
}
