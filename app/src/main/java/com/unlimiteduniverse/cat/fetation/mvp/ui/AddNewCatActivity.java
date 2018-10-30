package com.unlimiteduniverse.cat.fetation.mvp.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.photopicker.PhotoPicker;
import com.photopicker.PhotoPreview;
import com.unlimiteduniverse.cat.fetation.R;
import com.unlimiteduniverse.cat.fetation.view.CircleImageView;
import com.unlimiteduniverse.common.utils.PictureUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Irvin
 * Date: on 2018/10/8 0008.
 */

public class AddNewCatActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener, View.OnClickListener {


    @BindView(R.id.avatar_container)
    ConstraintLayout mAvatarContainer;
    @BindView(R.id.civ_avatar)
    CircleImageView mCIVAvatar;

    @BindView(R.id.name_container)
    ConstraintLayout mNameContainer;
    @BindView(R.id.sex_container)
    ConstraintLayout mSexContainer;
    @BindView(R.id.weight_container)
    ConstraintLayout mWeightContainer;
    @BindView(R.id.neutering_container)
    ConstraintLayout mNeuteringContainer;
    @BindView(R.id.birthday_container)
    ConstraintLayout mBirthdayContainer;
    @BindView(R.id.coming_date_container)
    ConstraintLayout mComingContainer;
    @BindView(R.id.describe_container)
    ConstraintLayout mDescribeContainer;

    Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_cat);

        mUnbinder = ButterKnife.bind(this);
        initializeToolbar();
        initView();
    }

    private void initView() {
        mAvatarContainer.setOnClickListener(this);
        mNameContainer.setOnClickListener(this);
        mSexContainer.setOnClickListener(this);
        mWeightContainer.setOnClickListener(this);
        mNeuteringContainer.setOnClickListener(this);
        mBirthdayContainer.setOnClickListener(this);
        mComingContainer.setOnClickListener(this);
        mDescribeContainer.setOnClickListener(this);
    }

    private void initializeToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_confirm);
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitle(R.string.add_new_cat_title);
        toolbar.setTitleTextAppearance(this, R.style.ActionBarTitle);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.avatar_container:
                PhotoPicker.builder()
                        .setPhotoCount(1)
                        .setShowCamera(true)
                        .setPreviewEnabled(false)
                        .start(this, PhotoPicker.REQUEST_CODE);
                break;
            case R.id.name_container:

                break;
            case R.id.sex_container:
                break;
            case R.id.weight_container:
                break;
            case R.id.neutering_container:
                break;
            case R.id.birthday_container:
                break;
            case R.id.coming_date_container:
                break;
            case R.id.describe_container:
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK &&
                (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {

            List<String> photos;
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                if (photos != null && photos.size() >0) {
                    PictureUtils.pictureZip(this, photos.get(0), new PictureUtils.Callback() {
                        @Override
                        public void onPictureZipDone(Uri uri) {
                            if (uri != null) {
                                RequestOptions options = new RequestOptions()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .fitCenter()
                                        .override(250, 250);

                                Glide.with(AddNewCatActivity.this)
                                        .load(uri)
                                        .apply(options)
                                        .into(mCIVAvatar);
                            }
                        }
                    });
                }
            }
        }
    }
}
