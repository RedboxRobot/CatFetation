package com.unlimiteduniverse.cat.fetation.mvp.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.unlimiteduniverse.cat.fetation.R;
import com.unlimiteduniverse.cat.fetation.mvp.ui.home.fragment.CatHomeFragment;
import com.unlimiteduniverse.cat.fetation.mvp.ui.home.fragment.OtherFragment;
import com.unlimiteduniverse.cat.fetation.mvp.ui.home.fragment.PregnantCatFragment;

public class HomeActivity extends AppCompatActivity {

    public static final String FRAGMENT_FLAG_CAT_HOME = "flag_cat_home";
    public static final String FRAGMENT_FLAG_PREGNANT_CAT = "flag_pregnant_cat";
    public static final String FRAGMENT_FLAG_OTHER = "flag_other";

    private FragmentManager mFragmentManager;
    private CatHomeFragment mCatHomeFragment;
    private PregnantCatFragment mPregnantCatFragment;
    private OtherFragment mOtherFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_print:
                    showFragment(mCatHomeFragment, FRAGMENT_FLAG_CAT_HOME);
                    return true;
                case R.id.navigation_discovery:
                    showFragment(mPregnantCatFragment, FRAGMENT_FLAG_PREGNANT_CAT);
                    return true;
                case R.id.navigation_user_center:
                    showFragment(mOtherFragment, FRAGMENT_FLAG_OTHER);
                    return true;
            }
            return false;
        }
    };

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setClass(context, HomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置toolbar透明
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        setContentView(R.layout.activity_home);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mFragmentManager = getSupportFragmentManager();
        mCatHomeFragment = new CatHomeFragment();
        mPregnantCatFragment = new PregnantCatFragment();
        mOtherFragment = new OtherFragment();
        setDefaultFirstFragment();
    }

    private void setDefaultFirstFragment() {
        showFragment(mCatHomeFragment, FRAGMENT_FLAG_CAT_HOME);
    }

    public void showFragment(Fragment fragment, String tag) {
        if (fragment == null) {
            return;
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        hideAllFragment(fragmentTransaction);

        Log.e("Irvin", "fragment.isAdded():" + fragment.isAdded());
        if (!fragment.isAdded()) {
            fragmentTransaction.add(R.id.content, fragment, tag);
        } else {
            fragmentTransaction.show(fragment);
        }
        commitShowFragment(fragmentTransaction, fragment);
    }

    public void commitShowFragment(FragmentTransaction fragmentTransaction, Fragment fragment) {
        fragmentTransaction.show(fragment);
        fragmentTransaction.commitAllowingStateLoss();
        mFragmentManager.executePendingTransactions();
    }

    public void hideAllFragment(FragmentTransaction fragmentTransaction) {
        hideFragment(fragmentTransaction, mCatHomeFragment);
        hideFragment(fragmentTransaction, mPregnantCatFragment);
        hideFragment(fragmentTransaction, mOtherFragment);
    }

    private void hideFragment(FragmentTransaction fragmentTransaction, Fragment fragment) {
        if (fragment != null) {
            fragmentTransaction.hide(fragment);
        }
    }
}
