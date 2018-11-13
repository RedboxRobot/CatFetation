package com.unlimiteduniverse.common.recyclerview.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Irvin
 * Date: on 2018/7/17 0017.
 * 一个ImageView + TextView的固定RecyclerView样式
 */

public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter<BaseRecyclerViewAdapter.ViewHolder> {

    private OnRecyclerItemSelected mItemSelected;
    protected List<Model> mList = new ArrayList<>();

    protected abstract int getLayoutRootId();

    protected abstract int getImageId();

    protected abstract int getNameId();

    public BaseRecyclerViewAdapter(OnRecyclerItemSelected mItemSelected) {
        this.mItemSelected = mItemSelected;
    }

    @NonNull
    @Override
    public BaseRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(getLayoutRootId(), parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerViewAdapter.ViewHolder holder, int position) {
        Model model = mList.get(position);
        holder.icon.setImageResource(model.mIcon);
        holder.txt.setText(model.mName);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class Model<T> {
        private String mName;
        private int mIcon;
        private T mType;

        public Model(String name, int icon, T type) {
            mName = name;
            mIcon = icon;
            mType = type;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView txt;

        ViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(getImageId());
            txt = itemView.findViewById(getNameId());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemSelected.onViewItemSelected(mList.get(getLayoutPosition()).mType);
                }
            });
        }
    }

    public interface OnRecyclerItemSelected<T> {
        void onViewItemSelected(T type);
    }
}
