package com.unlimiteduniverse.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import com.kprogresshud.KProgressHUD;

/**
 * date: 2017/8/7
 * desc: ProgressUtil等待框工具类.
 */

public class ProgressUtil {

    private static KProgressHUD mProgressHUD;
    private static KProgressHUD mSpecial;

    public static void show(Context context) {
        if (context == null) {
            return;
        }

        if (mProgressHUD == null) {
            mProgressHUD = KProgressHUD.create(context)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("请稍等...")
                    .setCancellable(false)
                    .setFocusable(false)
                    .setAnimationSpeed(1)
                    .setDimAmount(0.5f);
            if (context instanceof Activity) {
                if (!((Activity)context).isFinishing() && !((Activity)context).isDestroyed()) {
                    mProgressHUD.show();
                }
            }
        } else if (mProgressHUD.getContext() != context) {
            dismiss();
            mProgressHUD = KProgressHUD.create(context)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("请稍等...")
                    .setCancellable(false)
                    .setFocusable(false)
                    .setAnimationSpeed(1)
                    .setDimAmount(0.5f);
            if (context instanceof Activity) {
                if (!((Activity)context).isFinishing() && !((Activity)context).isDestroyed()) {
                    mProgressHUD.show();
                }
            }
        }
    }

    public static void show(Context context, String message, boolean canCancelable,
                            DialogInterface.OnCancelListener listener) {
        if (context == null) {
            return;
        }
        if (mProgressHUD == null) {
            mProgressHUD = KProgressHUD.create(context)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel(message)
                    .setCancellable(canCancelable)
                    .setCancellable(listener)
                    .setFocusable(true)
                    .setAnimationSpeed(1)
                    .setDimAmount(0.5f);
            if (context instanceof Activity) {
                if (!((Activity)context).isFinishing() && !((Activity)context).isDestroyed()) {
                    mProgressHUD.show();
                }
            }
        } else if (mProgressHUD.getContext() != context) {
            dismiss();
            mProgressHUD = KProgressHUD.create(context)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel(message)
                    .setCancellable(canCancelable)
                    .setCancellable(listener)
                    .setFocusable(true)
                    .setAnimationSpeed(1)
                    .setDimAmount(0.5f);
            if (context instanceof Activity) {
                if (!((Activity)context).isFinishing() && !((Activity)context).isDestroyed()) {
                    mProgressHUD.show();
                }
            }
        }
    }

    public static void dismiss() {
        if (mProgressHUD == null) {
            return;
        }
        if (mProgressHUD.isShowing()) {
            try {
                mProgressHUD.dismiss();
                mProgressHUD = null;
            } catch (Exception e) {
                // maybe we catch IllegalArgumentException here.
                e.printStackTrace();
            }
        }
    }

    /**
     * just for login
     *
     * @param context 上下文
     * @param isShow  是否显示dialog
     */
    public static void displaySpecialProgress(Context context, boolean isShow,
                                              DialogInterface.OnCancelListener listener) {
        if (context == null) {
            return;
        }
        if (isShow) {
            mSpecial = KProgressHUD.create(context)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("正在登陆,请稍候...")
                    .setCancellable(true)
                    .setCancellable(listener)
                    .setFocusable(true)
                    .setAnimationSpeed(1)
                    .setDimAmount(0.5f);
            if (context instanceof Activity) {
                if (!((Activity)context).isFinishing() && !((Activity)context).isDestroyed()) {
                    mSpecial.show();
                }
            }
        } else {
            if (mSpecial != null && mSpecial.isShowing()
                    && !((Activity)context).isDestroyed() && !((Activity)context).isFinishing()) {
                mSpecial.dismiss();
                mSpecial = null;
            }
        }
    }

    public static void displaySpecialProgress(Context context, String message, boolean isShow,
                                              DialogInterface.OnCancelListener listener) {
        if (context == null) {
            return;
        }
        if (isShow) {
            mSpecial = KProgressHUD.create(context)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel(message)
                    .setCancellable(true)
                    .setCancellable(listener)
                    .setFocusable(true)
                    .setAnimationSpeed(1)
                    .setDimAmount(0.5f);
            if (context instanceof Activity) {
                if (!((Activity)context).isFinishing() && !((Activity)context).isDestroyed()) {
                    mSpecial.show();
                }
            }
        } else {
            if (mSpecial != null && mSpecial.isShowing()
                    && !((Activity)context).isDestroyed() && !((Activity)context).isFinishing()) {
                mSpecial.dismiss();
                mSpecial = null;
            }
        }
    }

    public synchronized static void displayCustomViewProgress(Context context, String message, boolean isShow, View view,
                                                              DialogInterface.OnCancelListener listener) {
        if (context == null) {
            return;
        }
        if (isShow) {
            mSpecial = KProgressHUD.create(context)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel(message)
                    .setCancellable(true)
                    .setCancellable(listener)
                    .setFocusable(true)
                    .setAnimationSpeed(1)
                    .setDimAmount(0.5f);
            if (view != null) {
                mSpecial.setCustomView(view);
            }
            if (context instanceof Activity) {
                if (!((Activity)context).isFinishing() && !((Activity)context).isDestroyed()) {
                    mSpecial.show();
                }
            }
        } else {
            if (mSpecial != null && mSpecial.isShowing()
                    && !((Activity)context).isDestroyed() && !((Activity)context).isFinishing()) {
                mSpecial.dismiss();
                mSpecial = null;
            }
        }
    }
}
