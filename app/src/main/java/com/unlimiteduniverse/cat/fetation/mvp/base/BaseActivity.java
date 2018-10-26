package com.unlimiteduniverse.cat.fetation.mvp.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.hannesdorfmann.mosby.mvp.MvpActivity;

public abstract class BaseActivity<V extends BaseView, P extends BasePresenter<V>> extends
        MvpActivity<V, P> implements View.OnClickListener {
    // view
    protected Activity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        presenter = createPresenter();
        presenter.attachView((V) mActivity);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPresenter().clearSubscribe();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}

