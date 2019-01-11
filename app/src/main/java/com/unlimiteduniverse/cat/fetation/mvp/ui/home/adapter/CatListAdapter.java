package com.unlimiteduniverse.cat.fetation.mvp.ui.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.unlimiteduniverse.cat.fetation.AppConstants;
import com.unlimiteduniverse.cat.fetation.R;
import com.unlimiteduniverse.cat.fetation.mvp.ui.entity.NewCat;
import com.unlimiteduniverse.common.recyclerview.adapter.BaseQuickAdapter;
import com.unlimiteduniverse.common.recyclerview.holder.BaseViewHolder;
import com.unlimiteduniverse.common.utils.TimeUtils;

import java.util.List;

/**
 * @author Irvin
 * @time 2018/11/12 0012
 */
public class CatListAdapter extends BaseQuickAdapter<NewCat, BaseViewHolder, RecyclerView> {

    private TextView mCatName;
    private ImageView mCatSex;
    private ImageView mCatAvatar;
    private TextView mCatStatus;

    private TextView mCatBirthday;
    private TextView mCatWeight;
    private TextView mCatDescribe;
    private TextView mCatAge;

    public CatListAdapter(RecyclerView recyclerView, int layoutResId, List<NewCat> data) {
        super(recyclerView, layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewCat item, int position, boolean isScrolling) {
        inflateView(helper);

        setCatName(item);
        setCatSex(item);
        setCatAvatar(helper, item);
        setCatAge(item);
        setCatBirthday(item);
        setCatStatus(item);
        setCatDescribe(item);
        setCatWeight(item);
    }

    private void inflateView(BaseViewHolder helper) {
        mCatName = helper.getView(R.id.cat_list_name);
        mCatSex = helper.getView(R.id.cat_list_sex);
        mCatAge = helper.getView(R.id.cat_list_age);
        mCatAvatar = helper.getView(R.id.cat_list_avatar);
        mCatStatus = helper.getView(R.id.cat_list_status);
        mCatBirthday = helper.getView(R.id.cat_list_birthday);
        mCatWeight = helper.getView(R.id.cat_list_weight);
        mCatDescribe = helper.getView(R.id.cat_list_describe);
    }

    private void setCatName(NewCat item) {
        mCatName.setText(item.getCatName());
    }

    private void setCatSex(NewCat item) {
        if (item.getCatSex().equals("男")) {
            mCatSex.setImageResource(R.mipmap.ic_sex_men);
        } else {
            mCatSex.setImageResource(R.mipmap.ic_sex_women);
        }
    }

    private void setCatAvatar(BaseViewHolder helper, NewCat item) {
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter();

        if (TextUtils.isEmpty(item.getCatAvatar())) {
            Glide.with(helper.getContext())
                    .load(R.mipmap.icon_cat)
                    .apply(options)
                    .into(mCatAvatar);
            return;
        }

        Glide.with(helper.getContext())
                .load(item.getCatAvatar())
                .apply(options)
                .into(mCatAvatar);
    }

    private void setCatBirthday(NewCat item) {
        String bir = TimeUtils.timestamp2String(item.getBirthday(), null);
        mCatBirthday.setText(bir);
    }

    private void setCatStatus(NewCat item) {
        mCatStatus.setVisibility(View.VISIBLE);
        if (item.getIsNeutering() == AppConstants.NEUTERING) {
            mCatStatus.setText("已绝育");
        } else if (item.getIsNeutering() == AppConstants.UNNEUTERING) {
            mCatStatus.setText("未绝育");
        } else {
            mCatStatus.setVisibility(View.GONE);
        }
    }

    private void setCatAge(NewCat item) {
        if (item.getBirthday() == 0) {
           mCatAge.setVisibility(View.GONE);
        } else {
            mCatAge.setVisibility(View.VISIBLE);
            mCatAge.setText(TimeUtils.getAgeByBirthday(item.getBirthday()));
        }
    }

    private void setCatDescribe(NewCat item) {
        if (TextUtils.isEmpty(item.getDescribe())) {
            mCatDescribe.setVisibility(View.GONE);
        } else {
            mCatDescribe.setVisibility(View.VISIBLE);
            mCatDescribe.setText(item.getDescribe());
        }
    }

    private void setCatWeight(NewCat item) {
        if (!TextUtils.isEmpty(item.getCatWeight())) {
            mCatWeight.setText(String.format("%skg", item.getCatWeight()));
            mCatWeight.setVisibility(View.VISIBLE);
        } else {
            mCatWeight.setVisibility(View.GONE);
        }
    }
}