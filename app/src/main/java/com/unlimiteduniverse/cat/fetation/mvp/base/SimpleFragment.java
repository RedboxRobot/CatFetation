package com.unlimiteduniverse.cat.fetation.mvp.base;

import android.support.annotation.NonNull;

import com.unlimiteduniverse.cat.fetation.api.HandleBackInterface;
import com.unlimiteduniverse.cat.fetation.utils.HandleBackUtil;

/**
 * Created by Irvin
 * on 2018/7/13 0022.
 */

public abstract class SimpleFragment extends BaseFragment<BaseView, BasePresenter<BaseView>> implements HandleBackInterface {

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
