package com.unlimiteduniverse.cat.fetation.mvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby.mvp.MvpFragment;

public abstract class BaseFragment<A extends AppCompatActivity, V extends BaseView, P extends BasePresenter<V>> extends
        MvpFragment<V, P> implements View.OnClickListener {

    protected A mContentActivity;
    protected Fragment mFragment;

    private View mRootView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //显示fragment中的menu
        setHasOptionsMenu(true);

        mContentActivity = getAppActivity();
        if (mContentActivity == null) {
            throw new IllegalArgumentException("Activity must not be null!");
        }
        mFragment = this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getRootViewId(), container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getPresenter().clearSubscribe();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public View getRootView() {
        return mRootView;
    }

    //返回true表示fragment自己处理back的点击事件
    public boolean isFragmentHandleBack() {
        return false;
    }

    //考虑改为abstract具体点击事件的实现
    public void onBackClick() {
    }
    //-------------- abstract ------------------

    public abstract A getAppActivity();

    public abstract int getRootViewId();

    public abstract void initView(Bundle savedInstanceState);

    public abstract void initData();

}

