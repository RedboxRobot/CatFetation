package com.unlimiteduniverse.uikit.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.unlimiteduniverse.common.sysutils.ScreenUtil;

/**
 * @author ChengXinPing
 * @time 2018/1/4 15:47
 * 自定义dialog  只需要传入自定义布局和自定义style
 */

public class CustomBuilderDialog extends Dialog {
    private Context context;
    private boolean cancelTouchout;
    private View view;

    private CustomBuilderDialog(Builder builder) {
        super(builder.context);
        context = builder.context;
        cancelTouchout = builder.cancelTouchout;
        view = builder.view;

    }

    private CustomBuilderDialog(Builder builder, int resStyle) {
        super(builder.context, resStyle);
        context = builder.context;
        cancelTouchout = builder.cancelTouchout;
        view = builder.view;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(view);

        setCanceledOnTouchOutside(cancelTouchout);
        Window win = getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.height = ScreenUtil.dip2px(250);
        lp.width = ScreenUtil.dip2px(275);
        win.setAttributes(lp);
    }

    public View getView(@IdRes int resId) {
        return view.findViewById(resId);
    }

    public static final class Builder {

        private Context context;
        private boolean cancelTouchout;
        private View view;
        private int resStyle = -1;

        public Builder(Context context) {
            this.context = context;
        }


        public Builder cancelTouchout(boolean val) {
            cancelTouchout = val;
            return this;
        }

        public CustomBuilderDialog build() {
            if (resStyle != -1) {
                return new CustomBuilderDialog(this, resStyle);
            } else {
                return new CustomBuilderDialog(this);
            }
        }

        public Builder view(int resView) {
            view = LayoutInflater.from(context).inflate(resView, null);
            return this;
        }

        public Builder style(int resStyle) {
            this.resStyle = resStyle;
            return this;
        }

        public Builder addViewOnclick(int viewRes, View.OnClickListener listener) {
            view.findViewById(viewRes).setOnClickListener(listener);
            return this;
        }

    }

}
