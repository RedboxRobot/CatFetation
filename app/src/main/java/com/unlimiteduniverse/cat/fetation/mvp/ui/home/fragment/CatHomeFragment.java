package com.unlimiteduniverse.cat.fetation.mvp.ui.home.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.unlimiteduniverse.cat.fetation.R;
import com.unlimiteduniverse.cat.fetation.mvp.ui.home.HomeActivity;

/**
 * @author Irvin
 * @time 2018/11/9 0009
 */
public class CatHomeFragment extends Fragment {

    private DrawerLayout drawerLayout;
    private NavigationView navigation;
    private ActionBarDrawerToggle drawerToggle;
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
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeToolbar();
    }

    private void initializeToolbar() {
        drawerLayout = (DrawerLayout) mHomeActivity.findViewById(R.id.drawer_layout);
        navigation = (NavigationView) mHomeActivity.findViewById(R.id.drawer_navigation);
        Toolbar toolbar = (Toolbar) mHomeActivity.findViewById(R.id.toolbar);
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
        drawerToggle = new ActionBarDrawerToggle(mHomeActivity, drawerLayout, toolbar, R.string.openString, R.string.closeString){
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
}
