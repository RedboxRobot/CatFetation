package com.unlimiteduniverse.cat.fetation.mvp.ui.cats.fragment;

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
import com.unlimiteduniverse.cat.fetation.view.widgets.XEditText;
import com.unlimiteduniverse.common.utils.CheckEmptyUtils;
import com.unlimiteduniverse.common.utils.XEditUtils;

/**
 * @author Irvin
 * @time 2018/9/6
 */

public class EditNameFragment extends SimpleFragment
        implements Toolbar.OnMenuItemClickListener {

    public static final String KEY_NEW_NAME = "key_new_name";
    private XEditText editName;
    private boolean isUploadInfo;
    private String mOldName;

    public static void start(Activity mActivity, String oldName, boolean isNeedUpload, Integer requestCode) {
        start(mActivity, oldName, isNeedUpload, requestCode, null);
    }

    public static void start(Activity mActivity, String oldName, boolean isNeedUpload, Integer requestCode, Fragment mFragment) {
        Intent intent = new Intent();
        intent.setClass(mActivity, ContentActivity.class);
        intent.putExtra(AppConstants.KEY_USER_INFO, oldName);
        intent.putExtra(AppConstants.KEY_USER_INFO_UPLOAD, isNeedUpload);
        intent.putExtra(AppConstants.KEY_FRAGMENT, AppConstants.FRAGMENT_EDIT_NAME);
        if (requestCode != null) {
            if (mFragment != null) {
                mFragment.startActivityForResult(intent, requestCode);
            } else {
                mActivity.startActivityForResult(intent, requestCode);
            }
        } else {
            mActivity.startActivity(intent);
        }
    }

    public static EditNameFragment newInstance() {
        EditNameFragment fragment = new EditNameFragment();
        return fragment;
    }

    @Override
    public int getRootViewId() {
        return R.layout.fragment_edit_name;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initializeToolbar();
        mOldName = (String) mContentActivity.getIntent().getStringExtra(AppConstants.KEY_USER_INFO);
        isUploadInfo = mContentActivity.getIntent().getBooleanExtra(AppConstants.KEY_USER_INFO_UPLOAD, false);
        editName = (XEditText) mContentActivity.findViewById(R.id.device_name_edit);
        new XEditUtils().set(editName, AppConstants.LETTER_NUMBER_CHINESE_ENGLISH, "请输入中/英文或数字");
        editName.setHint("请输入昵称");
        editName.setText(mOldName);
        editName.setSelection(editName.getText().length());
        editName.setOnDropArrowClickListener(new XEditText.OnDropArrowClickListener() {
            @Override
            public void onDropArrowClick() {
                editName.setText("");
            }
        });
    }

    private void initializeToolbar() {
        Toolbar toolbar = (Toolbar) mContentActivity.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContentActivity.finish();
            }
        });
        toolbar.setTitle("修改昵称");
        toolbar.inflateMenu(R.menu.menu_save);
        toolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public void initData() {
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                if (!isUploadInfo) {
                    Intent intent = new Intent();
                    intent.putExtra(KEY_NEW_NAME, editName.getText().toString());
                    mContentActivity.setResult(Activity.RESULT_OK, intent);
                    mContentActivity.finish();
                    return true;
                }
                if (!CheckEmptyUtils.checkIsEmpty(editName.getText().toString(), mContentActivity, CheckEmptyUtils.FLAG_NAME_NOT_NULL)) {
                }
                return true;
            default:
                break;
        }
        return false;
    }
}
