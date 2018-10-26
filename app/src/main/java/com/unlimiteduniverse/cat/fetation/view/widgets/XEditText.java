package com.unlimiteduniverse.cat.fetation.view.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.unlimiteduniverse.cat.fetation.R;


/**
 * Created by Irvin
 * on 2017/8/14 0014.
 */

public class XEditText extends AppCompatEditText {

    private Context mContext;
    private String hintText;
    private float hintTextSize;

    final int DRAWABLE_LEFT = 0;
    final int DRAWABLE_TOP = 1;
    final int DRAWABLE_RIGHT = 2;
    final int DRAWABLE_BOTTOM = 3;

    public XEditText(@NonNull Context context) {
        this(context, null);
    }

    public XEditText(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.xEditText, 0, 0);
        try {
            hintText = a.getString(R.styleable.xEditText_hintText);
            hintTextSize = a.getDimension(
                    R.styleable.xEditText_hintTextSize, getResources().getDimension(R.dimen.text_size_14));
        } finally {
            a.recycle();
        }

        setHintTextSize();
    }

    public XEditText(@NonNull Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.xEditText, defStyleAttr, 0);
        try {
            hintText = a.getString(R.styleable.xEditText_hintText);
            hintTextSize = a.getDimension(
                    R.styleable.xEditText_hintTextSize, getResources().getDimension(R.dimen.text_size_14));
        } finally {
            a.recycle();
        }

        setHintTextSize();
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue 从getTextSize获取的值是px
     * @return setTextSize的值是sp
     */
    private int px2sp(float pxValue) {
        final float fontScale = mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    private void setHintTextSize() {
        if(TextUtils.isEmpty(hintText)) {
            return;
        }
        SpannableString s = new SpannableString(hintText);
        AbsoluteSizeSpan textSize = new AbsoluteSizeSpan(px2sp(hintTextSize), true);
        s.setSpan(textSize, 0, s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        setHint(s);
    }

    public void setXHintText(String text) {
        hintText = text;
        setHintTextSize();
    }

    public void setXHintText(int id) {
        hintText = mContext.getResources().getString(id);
        setHintTextSize();
    }

    public interface OnDropArrowClickListener {
        void onDropArrowClick();
    }
    private OnDropArrowClickListener onDropArrowClickListener;

    public void setOnDropArrowClickListener(OnDropArrowClickListener onDropArrowClickListener) {
        this.onDropArrowClickListener = onDropArrowClickListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Drawable drawableRight = getCompoundDrawables()[DRAWABLE_RIGHT];
            if (drawableRight != null) {
                //本次点击事件的x轴坐标，如果>当前控件宽度-控件右间距-drawable实际展示大小
                if (event.getX() >= (getWidth() - getPaddingRight() - drawableRight.getIntrinsicWidth())) {
                    //设置点击EditText右侧图标EditText失去焦点，
                    // 防止点击EditText右侧图标EditText获得焦点，软键盘弹出
                    setFocusableInTouchMode(false);
                    setFocusable(false);
                    if (onDropArrowClickListener != null) {
                        onDropArrowClickListener.onDropArrowClick();
                    }
                } else {
                    setFocusableInTouchMode(true);
                    setFocusable(true);
                }
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }
}
