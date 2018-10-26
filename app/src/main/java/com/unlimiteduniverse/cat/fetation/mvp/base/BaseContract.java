package com.unlimiteduniverse.cat.fetation.mvp.base;

import android.util.Log;

import com.delicloud.app.http.RetrofitFactory;
import com.delicloud.app.http.base.BaseResponse;
import com.delicloud.app.http.utils.ExceptionHandler;
import com.delicloud.app.smartprint.http.PlatformApiService;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Irvin
 * on 2017/9/23.
 */

public class BaseContract {
    protected PlatformApiService mPlatformApiService = RetrofitFactory.getInstance()
        .createService(PlatformApiService.class, false);

    protected CompositeDisposable compositeSubscription = new CompositeDisposable();


    protected <T> ObservableTransformer<BaseResponse<T>, T> applySchedulers() {
        return new ObservableTransformer<BaseResponse<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseResponse<T>> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap(new Function<BaseResponse<T>, ObservableSource<T>>() {
                            @Override
                            public ObservableSource<T> apply(BaseResponse<T> baseResponseObservable) throws Exception {
                                return flatResponse(baseResponseObservable);
                            }
                        });
            }
        };
    }

    /**
     * 对网络接口返回的Response进行分割操作
     *
     * @param response
     * @param <T>
     * @return
     */
    private <T> ObservableSource<T> flatResponse(final BaseResponse<T> response) {
        return Observable.create(new ObservableOnSubscribe<T>() {

            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                if (response.isSuccess()) {
                    if (!emitter.isDisposed()) {
                        emitter.onNext(response.data);
                    }
                } else {
                    if (!emitter.isDisposed()) {
                        Log.e("Irvin", "Code:" + response.code);
                        emitter.onError(new ExceptionHandler.GivenMessageException(response.code,
                                response.msg, response.ex_msg == null ? "" : response.ex_msg));
                    }
                    return;
                }

                if (!emitter.isDisposed()) {
                    emitter.onComplete();
                }
            }
        });
    }

    public void clearSubscribe() {
        if (compositeSubscription != null && !compositeSubscription.isDisposed()) {
            Log.e("Irvin", "compositeSubscription:" + compositeSubscription);
            compositeSubscription.clear();
        }
    }

}
