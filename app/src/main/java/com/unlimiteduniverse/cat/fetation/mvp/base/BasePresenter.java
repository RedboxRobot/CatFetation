package com.unlimiteduniverse.cat.fetation.mvp.base;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.unlimiteduniverse.cat.fetation.http.ApiService;
import com.unlimiteduniverse.common.utils.GsonHelper;
import com.unlimiteduniverse.common.utils.ProgressUtil;
import com.unlimiteduniverse.http.RetrofitFactory;
import com.unlimiteduniverse.http.base.BaseResponse;
import com.unlimiteduniverse.http.utils.ExceptionHandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

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
 * on 2017/8/27.
 */

public class BasePresenter<V extends BaseView> extends MvpBasePresenter<V> {

    private Context mContext;

    protected ApiService mApiService;

    protected CompositeDisposable compositeSubscription;

    public BasePresenter(Context context) {
        this.mContext = context;
        mApiService = RetrofitFactory.getInstance()
                .createService(ApiService.class, false);
        compositeSubscription = new CompositeDisposable();
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public boolean isViewAttached() {
        Log.d("", "isViewAttached:" + super.isViewAttached());
        return super.isViewAttached();
    }

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
            compositeSubscription.dispose();
        }
        //clear progressbar
        //some times when crash happened progressbar will not be closed
        ProgressUtil.dismiss();
    }

    /**
     * 获取默认数据
     * @param fileName
     * @param clazz
     * @return
     */
    public  BaseResponse getDefaultData(String fileName, Class clazz) {
        String textSting= getInfoFromAssets(fileName);
        if(textSting == null){
            return null;
        }
        Gson gson = GsonHelper.getInstance();
        BaseResponse baseResponse = null;
        if (gson != null) {
            try{
                Type objectType = type(BaseResponse.class, clazz);
                baseResponse = gson.fromJson(textSting, objectType);
            }catch (JsonSyntaxException e){
                e.printStackTrace();
            }
        }
        return baseResponse;
    }

    public ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }

    //从assets 文件夹中获取文件并读取数据
    public String getInfoFromAssets(String fileName){
        StringBuffer result = new StringBuffer();
        try {
            InputStreamReader inputReader = new InputStreamReader(mContext.getAssets().open("apimockdata/"+fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            while((line = bufReader.readLine()) != null){
                result.append(line).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return result.toString();
    }

}
