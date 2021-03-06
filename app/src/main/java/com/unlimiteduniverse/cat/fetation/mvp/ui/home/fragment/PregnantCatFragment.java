package com.unlimiteduniverse.cat.fetation.mvp.ui.home.fragment;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.unlimiteduniverse.cat.fetation.R;
import com.unlimiteduniverse.cat.fetation.mvp.base.SimpleFragment;
import com.unlimiteduniverse.cat.fetation.mvp.ui.home.HomeActivity;
import com.unlimiteduniverse.common.sysutils.ScreenUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Irvin
 * @time 2018/11/9 0009
 */
public class PregnantCatFragment extends SimpleFragment<HomeActivity> {

    @BindView(R.id.litter_list)
    RecyclerView mRecyclerView;

    Unbinder mUnbinder;

    @Override
    public HomeActivity getAppActivity() {
        return (HomeActivity) getActivity();
    }

    @Override
    public int getRootViewId() {
        return R.layout.fragment_pregnant_cat;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mUnbinder = ButterKnife.bind(this, getRootView());
        initializeToolbar();
        initList();
    }

    private void initList() {

    }

    private void initializeToolbar() {
        final Toolbar toolbar = (Toolbar) mContentActivity.findViewById(R.id.toolbar);
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                if (toolbar.getMeasuredHeight() == 0) {
                    mRecyclerView.postDelayed(this, 50);
                } else {
                    int height = toolbar.getMeasuredHeight();
                    AppBarLayout.LayoutParams lp = new AppBarLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            height + ScreenUtil.getSystemStatusBarHeight(mContentActivity));
                    toolbar.setLayoutParams(lp);
                    toolbar.setPadding(0, ScreenUtil.getSystemStatusBarHeight(mContentActivity), 0, 0);
                }
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        toolbar.setTitle("繁育室");
        //设置toolbar
        mContentActivity.setSupportActionBar(toolbar);
        //左上角返回图标
        mContentActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
