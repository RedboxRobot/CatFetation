package com.unlimiteduniverse.cat.fetation.mvp.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.unlimiteduniverse.cat.fetation.AppConstants;
import com.unlimiteduniverse.cat.fetation.R;
import com.unlimiteduniverse.cat.fetation.mvp.base.SimpleFragment;
import com.unlimiteduniverse.cat.fetation.mvp.ui.ContentActivity;
import com.unlimiteduniverse.uikit.widget.FJEditTextCount;

/**
 * Created by Irvin
 * Date: on 2018/9/6 0006.
 */

public class UserSignatureFragment extends SimpleFragment
        implements Toolbar.OnMenuItemClickListener {

    public static final String KEY_NEW_SIGNATURE = "key_new_signature";
    private FJEditTextCount mSignatureEdit;
    private boolean isUploadInfo;
    private String oldSign;

    public static void start(Activity mActivity, String oldSign, Integer requestCode) {
        start(mActivity, oldSign, false, requestCode, null);
    }

    public static void start(Activity activity, String oldSign, boolean isNeedUpload, Integer requestCode, Fragment fragment) {
        Intent intent = new Intent();
        intent.setClass(activity, ContentActivity.class);
        intent.putExtra(AppConstants.KEY_USER_SIGNATURE, oldSign);
        intent.putExtra(AppConstants.KEY_USER_INFO_UPLOAD, isNeedUpload);
        intent.putExtra(AppConstants.KEY_FRAGMENT, AppConstants.FRAGMENT_EDIT_SIGNATURE);
        if (requestCode != null) {
            if (fragment != null) {
                fragment.startActivityForResult(intent, requestCode);
            } else {
                activity.startActivityForResult(intent, requestCode);
            }
        } else {
            activity.startActivity(intent);
        }
    }

    public static UserSignatureFragment newInstance() {
        UserSignatureFragment fragment = new UserSignatureFragment();
        return fragment;
    }

    @Override
    public int getRootViewId() {
        return R.layout.fragment_user_signature;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initializeToolbar();
        isUploadInfo = mContentActivity.getIntent().getBooleanExtra(AppConstants.KEY_USER_INFO_UPLOAD, false);
        mSignatureEdit = mContentActivity.findViewById(R.id.edit_signature);
        oldSign = mContentActivity.getIntent().getStringExtra(AppConstants.KEY_USER_SIGNATURE);
        mSignatureEdit.setText(oldSign);
        mSignatureEdit.setRegex("[a-zA-Z|\u4e00-\u9fa5|0-9]+");
    }

    private void initializeToolbar() {
        Toolbar toolbar = (Toolbar) mContentActivity.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContentActivity.finish();
            }
        });
        toolbar.setTitle("编辑签名");
        toolbar.inflateMenu(R.menu.menu_save);
        toolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public void initData() {
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                if (!isUploadInfo) {
                    Intent intent = new Intent();
                    intent.putExtra(KEY_NEW_SIGNATURE, mSignatureEdit.getText());
                    mContentActivity.setResult(Activity.RESULT_OK, intent);
                    mContentActivity.finish();
                    return true;
                }
                //存数据
                return true;
        }

        return false;
    }

    @Override
    public void onClick(View v) {

    }
}
