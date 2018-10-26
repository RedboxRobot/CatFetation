package com.unlimiteduniverse.http.uploadTask;

import android.text.TextUtils;
import android.util.Log;

import com.delicloud.app.common.utils.tool.FileOperateUtils;
import com.delicloud.app.http.RetrofitFactory;
import com.delicloud.app.http.UploadService;
import com.delicloud.app.http.base.BaseResponse;
import com.delicloud.app.http.download.RxBus;
import com.delicloud.app.http.model.MultipartUploadResponse;
import com.delicloud.app.http.utils.ExceptionHandler;
import com.google.gson.Gson;
import com.scott.annotionprocessor.ITask;
import com.scott.transer.handler.BaseTaskHandler;
import com.scott.transer.handler.DefaultHttpUploadHandler;
import com.scott.transer.http.OkHttpProxy;
import com.shilec.xlogger.XLogger;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.CRC32;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author ChengXinPing
 * @time 2018/8/21 16:16
 */
public class MultipartUploadHandler extends BaseTaskHandler {
    private RandomAccessFile mFile;
    private String mResponse;     //返回数据
    private int mPiceRealSize = 0; //每一片的实际大小
    private PiceRequestBody mRequestBody; // 写入一片
    private final String TAG = DefaultHttpUploadHandler.class.getSimpleName();
    private Call mCurrentCall;
    private int block = 0;
    private long crc = 0;
    private CRC32 mCRC32;
    private UploadService mUploadService;

    /***
     * 当前这一片传输完成服务器返回的数据
     * @return
     */
    protected String getNowResponse() {
        return mResponse;
    }


    /***
     * 判断一片是否上传成功，需要通过服务器的返回值去判断
     * 注意:
     * 最后一片上传完也会去判断 一片是否上传成功
     * 所以需要考虑最后一片返回和每一片返回不同。
     * @return true 成功， false 失败
     */
    @Override
    protected boolean isPiceSuccessful() { //判断一片是否成功
        MultipartUploadResponse uploadResponse = new Gson().fromJson(getNowResponse(), MultipartUploadResponse.class);
        if (uploadResponse != null && uploadResponse.getCode() == 0) {
            if (!TextUtils.isEmpty(uploadResponse.getData().getUrl()) && !TextUtils.isEmpty(getTask().getUserId()) && !TextUtils.isEmpty(getTask().getSesstionId())) {
                mUploadService = RetrofitFactory.getInstance()
                        .createService(UploadService.class, false);
                Map<String, Object> params = new HashMap<>();
                params.put("token", getTask().getSesstionId());
                params.put("status", 1);
                params.put("file_md5", FileOperateUtils.getFileMD5Sync(new File(getTask().getSourceUrl())));
                params.put("oss_path", uploadResponse.getData().getUrl());
                params.put("user_id", Long.valueOf(getTask().getUserId()));
                mUploadService.changeUploadStatus(params)
                        .compose(this.<JSONObject>applySchedulers())
                        .subscribe(new Subscriber<JSONObject>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(JSONObject jsonObject) {

                            }
                        });
            }
            block++;
            mCRC32.reset();
            return true;
        } else {
            return false;
        }
    }


    /**
     * 对网络接口返回的Response进行分割操作
     *
     * @param response
     * @param <T>
     * @return
     */
    private <T> Observable<T> flatResponse(final BaseResponse<T> response) {
        return Observable.create(new Observable.OnSubscribe<T>() {

            @Override
            public void call(Subscriber<? super T> subscriber) {
                if (response.isSuccess()) {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(response.data);
                    }
                } else {
                    if (!subscriber.isUnsubscribed()) {
                        Log.e("Irvin", "Code:" + response.code);
                        subscriber.onError(new ExceptionHandler.GivenMessageException(
                                response.code, response.msg, response.ex_msg == null ? "" : response.ex_msg));
                    }
                    return;
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }

            }
        });
    }

    protected <T> Observable.Transformer<BaseResponse<T>, T> applySchedulers() {
        return new Observable.Transformer<BaseResponse<T>, T>() {
            @Override
            public Observable<T> call(Observable<BaseResponse<T>> baseResponseObservable) {
                return baseResponseObservable.subscribeOn(Schedulers.immediate())
                        .observeOn(Schedulers.immediate())
                        .flatMap(new Func1<BaseResponse<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(BaseResponse<T> tBaseResponse) {
                                return flatResponse(tBaseResponse);
                            }
                        });
            }
        };
    }

    /***
     * 最后一片上传完会被调用，需要判断最后一片上传成功后服务器返回值
     * @return true 成功， false 失败
     */
    @Override
    protected boolean isSuccessful() {
        MultipartUploadResponse uploadResponse = new Gson().fromJson(getNowResponse(), MultipartUploadResponse.class);
        if (uploadResponse != null && uploadResponse.getCode() == 0 && uploadResponse.getData() != null && !TextUtils.isEmpty(uploadResponse.getData().getUrl())) {
            RxBus.getDefault().send(uploadResponse.getData().getUrl());
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected byte[] readPice(ITask task) throws IOException {
        byte[] datas = new byte[getPiceBuffSize()];
        mPiceRealSize = mFile.read(datas, 0, getPiceBuffSize());
        return datas;
    }

    private String encodeName(String name) {
        try {
            return URLEncoder.encode(name, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void writePice(byte[] datas, ITask task) throws Exception {

        if (getPiceRealSize() < getPiceBuffSize()) {
            byte[] bytes = new byte[getPiceRealSize()];
            System.arraycopy(datas, 0, bytes, 0, getPiceRealSize());
            mRequestBody = new PiceRequestBody(bytes);
            mCRC32 = new CRC32();
            mCRC32.update(bytes);
            crc = mCRC32.getValue();
        } else {
            mRequestBody = new PiceRequestBody(datas);
            mCRC32 = new CRC32();
            mCRC32.update(datas);
            crc = mCRC32.getValue();
        }

        //服务端需要支持 Content-Range 的 header


        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", encodeName(task.getName()), mRequestBody)
                .addFormDataPart("block", block + "")
                .addFormDataPart("checksum", crc + "")
                .build();
        Request.Builder builder = new Request.Builder()
                .addHeader("Content-Range", "bytes " + task.getStartOffset()
                        + "-" + (task.getEndOffset() - 1) + "/" + mFile.length())
                .addHeader("Content-Disposition", "attachment; filename=" + encodeName(task.getName()))
                .addHeader("Connection", "Keep-Alive")
                .url(task.getDestUrl())
                .post(requestBody);
        //加入header
        if (getHeaders() != null) {
            for (String k : getHeaders().keySet()) {
                builder.addHeader(k, getHeaders().get(k));
            }
        }

        Request request = builder.build();
        OkHttpClient client = OkHttpProxy.getClient();
        mCurrentCall = client.newCall(request);
        mResponse = null;
        //XLogger.getDefault().e(TAG,"wait response === ");
        Response execute = mCurrentCall.execute();
        //XLogger.getDefault().e(TAG,"wait2 response === ");
        if (!execute.isSuccessful()) {
            XLogger.getDefault().e(TAG, "error msg = " + execute.body().string());
            throw new IllegalStateException(execute.message());
        }
        ResponseBody body = execute.body();
        mResponse = body.string();
        XLogger.getDefault().e(TAG, "response === " + mResponse);
    }

    @Override
    public void stop() {
        if (mCurrentCall != null) {
            mCurrentCall.cancel();
        }
        super.stop();
    }

    @Override
    protected void release() {
        try {
            mFile.close();
            mRequestBody = null;
            mResponse = null;
            mPiceRealSize = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void prepare(ITask task) throws IOException {
        mFile = new RandomAccessFile(task.getSourceUrl(), "r");
        //将文件指针移动到上次传输的位置
        mFile.seek(task.getCompleteLength());
    }

    @Override
    protected int getPiceRealSize() {
        return mPiceRealSize;
    }

    @Override
    protected long fileSize() {
        try {
            return mFile.length();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    protected long getCurrentCompleteLength() {
        if (mRequestBody == null) {
            return super.getCurrentCompleteLength();
        }
        return super.getCurrentCompleteLength() + mRequestBody.mCurrentCompleteLength;
    }

    protected class PiceRequestBody extends RequestBody {

        private ByteArrayInputStream mSource; //当前需要传输的一片
        private int mCurrentCompleteLength; //当前已经完成的长度，写入多少增加多少

        PiceRequestBody(byte[] datas) {
            mSource = new ByteArrayInputStream(datas, 0, getPiceRealSize());
        }

        @Override
        public long contentLength() throws IOException {
            //需要指定此次请求的内容长度，以从数据圆中实际读取的长度为准
            XLogger.getDefault().e(TAG, "offset = " + getTask().getStartOffset() + ", end = " + getTask().getEndOffset());
            return getPiceRealSize();
        }

        @Override
        public MediaType contentType() {
            //服务器支持的contenttype 类型
            return MediaType.parse("application/octet-stream");
        }

        @Override
        public void writeTo(BufferedSink sink) throws IOException {
            byte[] buf = new byte[8192];
            int len = 0;

            //这里这样处理是由于可以得到进度的连续变化数值，而不需要等到一片传完才等获取已经传输的长度
            while ((len = mSource.read(buf)) != -1) {
                sink.write(buf, 0, len);
                sink.flush();
                mCurrentCompleteLength += len;
            }

            mSource.reset();
            mSource.close();
        }
    }

    public static class Builder extends BaseTaskHandler.Builder<Builder, MultipartUploadHandler> {
        @Override
        protected MultipartUploadHandler buildTarget() {
            return new MultipartUploadHandler();
        }
    }
}
