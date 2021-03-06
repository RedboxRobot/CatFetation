package com.unlimiteduniverse.cat.fetation.mvp.ui.home.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.unlimiteduniverse.cat.fetation.R;
import com.unlimiteduniverse.cat.fetation.dao.DaoHelper;
import com.unlimiteduniverse.cat.fetation.mvp.ui.cats.activity.AddNewCatActivity;
import com.unlimiteduniverse.cat.fetation.mvp.ui.entity.NewCat;
import com.unlimiteduniverse.cat.fetation.mvp.ui.entity.NewCatDao;
import com.unlimiteduniverse.cat.fetation.mvp.ui.home.HomeActivity;
import com.unlimiteduniverse.cat.fetation.mvp.ui.home.adapter.CatListAdapter;
import com.unlimiteduniverse.common.recyclerview.decoration.SimpleDividerItemDecoration;
import com.unlimiteduniverse.common.sysutils.ScreenUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Irvin
 * @time 2018/11/9 0009
 */
public class CatHomeFragment extends Fragment {

    private DrawerLayout drawerLayout;
    private NavigationView navigation;
    private ActionBarDrawerToggle drawerToggle;
    @BindView(R.id.cat_list)
    RecyclerView mCatList;
    @BindView(R.id.cat_list_srl)
    SwipeRefreshLayout srl;

    private CatListAdapter mAdapter;

    Unbinder mUnbinder;

    private HomeActivity mHomeActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHomeActivity = (HomeActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cat_home, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeToolbar();
        initView();
        initData();
    }

    private void initializeToolbar() {
        drawerLayout = (DrawerLayout) mHomeActivity.findViewById(R.id.drawer_layout);
        navigation = (NavigationView) mHomeActivity.findViewById(R.id.drawer_navigation);
        Toolbar toolbar = (Toolbar) mHomeActivity.findViewById(R.id.cat_home_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        toolbar.setTitle("首页");
        //设置toolbar
        mHomeActivity.setSupportActionBar(toolbar);
        //设置左上角图标是否可点击
        mHomeActivity.getSupportActionBar().setHomeButtonEnabled(true);
        //左上角加上一个返回图标
        mHomeActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //初始化ActionBarDrawerToggle(ActionBarDrawerToggle就是一个开关一样用来打开或者关闭drawer)
        drawerToggle = new ActionBarDrawerToggle(mHomeActivity, drawerLayout, toolbar, R.string.openString, R.string.closeString) {
            /*
             * 抽屉菜单打开监听
             * */
            @Override
            public void onDrawerOpened(View drawerView) {
                Toast.makeText(mHomeActivity, "菜单打开了", Toast.LENGTH_SHORT).show();
                super.onDrawerOpened(drawerView);
            }
            /*
             * 抽屉菜单关闭监听
             * */
            @Override
            public void onDrawerClosed(View drawerView) {
                Toast.makeText(mHomeActivity, "菜单关闭了", Toast.LENGTH_SHORT).show();
                super.onDrawerClosed(drawerView);
            }
        };
        /*
         * NavigationView设置点击监听
         * */
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Toast.makeText(mHomeActivity, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                return false;
            }
        });
        drawerToggle.syncState();
        //设置DrawerLayout的抽屉开关监听
        drawerLayout.setDrawerListener(drawerToggle);
    }

    private void initView() {
        FloatingActionButton fab = (FloatingActionButton) mHomeActivity.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mHomeActivity, AddNewCatActivity.class));
            }
        });

        int start = getResources().getDimensionPixelSize(R.dimen.swipe_refresh_layout_offset_start);
        int end = getResources().getDimensionPixelSize(R.dimen.swipe_refresh_layout_offset_end);
        srl.setProgressViewOffset(true, start, end);

        //下拉刷新
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.clearData();
                initData();
            }
        });

        LinearLayoutManager llm = new LinearLayoutManager(mHomeActivity, LinearLayoutManager.VERTICAL, false);
        mCatList.setLayoutManager(llm);
        mCatList.addItemDecoration(new SimpleDividerItemDecoration(mHomeActivity, new ColorDrawable(Color.TRANSPARENT), ScreenUtil.dip2px(10)));
    }

    private void initData() {
        NewCatDao newCatDao = DaoHelper.getDbSession().getNewCatDao();
        List<NewCat> catList = newCatDao.queryRaw("", null);
        mAdapter = new CatListAdapter(mCatList, R.layout.item_cat_list, catList);
        mCatList.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        if (srl != null) {
            srl.setRefreshing(false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
