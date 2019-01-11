package com.unlimiteduniverse.cat.fetation.mvp.base;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.unlimiteduniverse.cat.fetation.api.HandleBackInterface;
import com.unlimiteduniverse.cat.fetation.utils.HandleBackUtil;

/**
 * Created by Irvin
 * on 2018/7/13 0022.
 */

public abstract class SimpleFragment<A extends AppCompatActivity> extends BaseFragment<A, BaseView, BasePresenter<BaseView>> implements HandleBackInterface {

    @NonNull
    @Override
    public BasePresenter<BaseView> createPresenter() {
        return new BasePresenter<>(getContext());
    }

    //返回键处理
    @Override
    public boolean onBackPressed() {
        return HandleBackUtil.handleBackPress(this);
    }

}
