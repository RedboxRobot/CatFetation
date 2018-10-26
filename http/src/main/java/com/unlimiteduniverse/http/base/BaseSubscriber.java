package com.unlimiteduniverse.http.base;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.delicloud.app.common.utils.sys.NetworkUtils;
import com.delicloud.app.common.utils.tool.ErrorToastController;
import com.delicloud.app.common.utils.tool.ProgressUtil;
import com.delicloud.app.http.HttpLibrary;
import com.delicloud.app.http.R;
import com.delicloud.app.http.utils.ExceptionHandler;

import rx.Subscriber;

/**
 * BaseSubscriber
 * Created by Irvin on 2017-8-15.
 */
public abstract class BaseSubscriber<T> extends Subscriber<T> {
    private Context mContext;
    private boolean isShowProgress;
    private ErrorToastController mErrorToastController;

    public BaseSubscriber(Context context, boolean isShowProgress) {
        this.mContext = context;
        this.isShowProgress = isShowProgress;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!NetworkUtils.isConnected(mContext)) {
            ErrorToastController.timeMachine(mContext, R.string.net_work_error);
            // **一定要主动调用下面这一句**
            onCompleted();
            return;
        }
        // 显示进度条
        if (isShowProgress) {
            showLoadingProgress();
        }
    }

    private void showLoadingProgress() {
        ProgressUtil.show(mContext);
    }

    @Override
    public void onCompleted() {
        //关闭等待进度条
        closeLoadingProgress();
    }

    private void closeLoadingProgress() {
        ProgressUtil.dismiss();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        ExceptionHandler.GivenMessageException gme;
        if (e instanceof ExceptionHandler.GivenMessageException) {
            gme = (ExceptionHandler.GivenMessageException)e;
        } else {
            ExceptionHandler.ResponseThrowable responseThrowable = ExceptionHandler.handleException(e);
            gme = new ExceptionHandler.GivenMessageException(String.valueOf(responseThrowable.code),
                    responseThrowable.message, responseThrowable.message);
        }
        if (onSpecialErrorHandle(gme)) {
            Log.e(HttpLibrary.getContext().getPackageName(), gme.getExMessage());
            try {
                int errorCode = Integer.valueOf(gme.getCode());
                if (errorCode <= 509  && errorCode > 500) {
                    //token失效
                    if (errorCode == 501) {
                        onHandleUserMultiPortLogin(gme);
                    } else {
                        onHandleTokenExpiration(gme);
                    }
                } else {
                    Toast.makeText(mContext, gme.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException exception) {
                Toast.makeText(mContext, gme.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            int errorCode = Integer.valueOf(gme.getCode());
            if (errorCode <= 509  && errorCode > 500) {
                //token失效
                if (errorCode == 501) {
                    onHandleUserMultiPortLogin(gme);
                } else {
                    onHandleTokenExpiration(gme);
                }
            }
        }
        onCompleted();
    }

    //特使错误处理 如果实现此方法，返回true 则表示走公共错误处理机制，false 则只实现自己的方法
    protected abstract boolean onSpecialErrorHandle(ExceptionHandler.GivenMessageException gme);

    //服务器50X
    protected abstract void onHandleTokenExpiration(ExceptionHandler.GivenMessageException gme);

    //多端登录服务器返回501
    protected abstract void onHandleUserMultiPortLogin(ExceptionHandler.GivenMessageException gme);
}
