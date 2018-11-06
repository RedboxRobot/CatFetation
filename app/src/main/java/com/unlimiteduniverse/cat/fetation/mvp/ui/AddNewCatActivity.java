package com.unlimiteduniverse.cat.fetation.mvp.ui;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.photopicker.PhotoPicker;
import com.photopicker.PhotoPreview;
import com.unlimiteduniverse.cat.fetation.R;
import com.unlimiteduniverse.cat.fetation.mvp.ui.fragment.EditNameFragment;
import com.unlimiteduniverse.cat.fetation.mvp.ui.fragment.UserSignatureFragment;
import com.unlimiteduniverse.cat.fetation.view.CircleImageView;
import com.unlimiteduniverse.common.utils.PictureUtils;
import com.unlimiteduniverse.common.utils.TimeUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.unlimiteduniverse.cat.fetation.mvp.ui.fragment.EditNameFragment.KEY_NEW_NAME;
import static com.unlimiteduniverse.cat.fetation.mvp.ui.fragment.UserSignatureFragment.KEY_NEW_SIGNATURE;

/**
 * Created by Irvin
 * Date: on 2018/10/8 0008.
 */

public class AddNewCatActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener, View.OnClickListener {

    public static final int REQUEST_CODE_SET_NAME = 0x01;
    public static final int REQUEST_CODE_SET_DESCRIBE = 0x02;

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

    @BindView(R.id.cat_name)
    TextView mCatName;
    @BindView(R.id.cat_sex)
    TextView mCatSex;
    @BindView(R.id.cat_birthday)
    TextView mCatBirthday;
    @BindView(R.id.cat_coming_date)
    TextView mCatComingDate;
    @BindView(R.id.describe_content)
    TextView mDescrible;

    Unbinder mUnbinder;

    private int mCheckedSexItem = -1;
    private String mDateSet = "";
    private String mComingDate = "";

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
        Calendar calendar = Calendar.getInstance();
        switch (v.getId()) {
            case R.id.avatar_container:
                PhotoPicker.builder()
                        .setPhotoCount(1)
                        .setShowCamera(true)
                        .setPreviewEnabled(false)
                        .start(this, PhotoPicker.REQUEST_CODE);
                break;
            case R.id.name_container:
                EditNameFragment.start(this, mCatName.getText().toString(),
                        false, REQUEST_CODE_SET_NAME);
                break;
            case R.id.sex_container:
                final AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.DialogTheme)
                        .setTitle("性别")
                        .setSingleChoiceItems(new String[]{"男", "女"}, mCheckedSexItem, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mCheckedSexItem = which;
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mCatSex.setTextColor(getResources().getColor(R.color.high_level_text_color));
                                switch (mCheckedSexItem) {
                                    case 0:
                                        mCatSex.setText("男");
                                        break;
                                    case 1:
                                        mCatSex.setText("女");
                                        break;
                                    default:
                                        break;
                                }
                                dialog.dismiss();
                            }
                        })
                        .show();
                alertDialog.setCanceledOnTouchOutside(true);
                break;
            case R.id.weight_container:
                break;
            case R.id.neutering_container:
                break;
            case R.id.birthday_container:
                DatePickerDialog dialog = new DatePickerDialog(
                        this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int
                            dayOfMonth) {

                        String mouth;
                        String day;
                        if (monthOfYear < 9) {
                            mouth = "0" + (monthOfYear + 1);
                        } else {
                            mouth = String.valueOf(monthOfYear + 1);
                        }
                        if (dayOfMonth < 9) {
                            day = "0" + dayOfMonth;
                        } else {
                            day = String.valueOf(dayOfMonth);
                        }
                        mDateSet = String.valueOf(year) + "-" +
                                mouth + "-" + day;
                        mCatBirthday.setTextColor(getResources().getColor(R.color.high_level_text_color));
                        mCatBirthday.setText(mDateSet);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                if (mDateSet != null && !TextUtils.isEmpty(mDateSet)) {
                    Date date = TimeUtils.str2Data(mDateSet);
                    calendar.setTime(date);
                    dialog.getDatePicker().updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH));
                }
                dialog.setCanceledOnTouchOutside(true);
                dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                dialog.show();

                break;
            case R.id.coming_date_container:
                DatePickerDialog comingDialog = new DatePickerDialog(
                        this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int
                            dayOfMonth) {

                        String mouth;
                        String day;
                        if (monthOfYear < 9) {
                            mouth = "0" + (monthOfYear + 1);
                        } else {
                            mouth = String.valueOf(monthOfYear + 1);
                        }
                        if (dayOfMonth < 9) {
                            day = "0" + dayOfMonth;
                        } else {
                            day = String.valueOf(dayOfMonth);
                        }
                        mComingDate = String.valueOf(year) + "-" +
                                mouth + "-" + day;
                        mCatComingDate.setTextColor(getResources().getColor(R.color.high_level_text_color));
                        mCatComingDate.setText(mComingDate);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                if (mComingDate != null && !TextUtils.isEmpty(mComingDate)) {
                    Date date = TimeUtils.str2Data(mComingDate);
                    calendar.setTime(date);
                    comingDialog.getDatePicker().updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH));
                }
                comingDialog.setCanceledOnTouchOutside(true);
                comingDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                comingDialog.show();

                break;
            case R.id.describe_container:
                UserSignatureFragment.start(this, mDescrible.getText().toString(), REQUEST_CODE_SET_DESCRIBE);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PhotoPicker.REQUEST_CODE:
                case PhotoPreview.REQUEST_CODE:
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
                    break;
                case REQUEST_CODE_SET_NAME:
                    String newName = data.getStringExtra(KEY_NEW_NAME);
                    mCatName.setText(newName);
                    break;
                case REQUEST_CODE_SET_DESCRIBE:
                    String newSign = data.getStringExtra(KEY_NEW_SIGNATURE);
                    mDescrible.setText(newSign);
                    break;
                default:
                    break;
            }
        }
    }
}
