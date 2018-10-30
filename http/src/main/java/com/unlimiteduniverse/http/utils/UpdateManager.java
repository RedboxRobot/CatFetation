package com.unlimiteduniverse.http.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import com.unlimiteduniverse.http.download.FileDownFactory;

import java.io.File;
import java.io.IOException;

import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import rx.Observer;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * app更新管理类
 */
public class UpdateManager {

    /**
     * 是否需要更新,需要则下载
     *
     * @param context 上下文
     * @param url     新版本地址
     * @param apkPath 本地apk保存路径
     */
    public static void downloadApk(final Context context, final String url, final String apkPath) {
        FileDownFactory.getInstance().down(url)
                .map(new Func1<ResponseBody, BufferedSource>() {
                    @Override
                    public BufferedSource call(ResponseBody responseBody) {
                        return responseBody.source();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<BufferedSource>() {

                    @Override
                    public void onCompleted() {
                        //安装apk
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        Uri data;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            //临时添加一个临时权限
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            data = FileProvider.getUriForFile(context, "com.delicloud.app.smartoffice.FileProvider", new File(apkPath + ""));
                        } else {

                            data = Uri.fromFile(new File(apkPath));
                        }
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setDataAndType(data, "application/vnd.android.package-archive");
                        context.startActivity(intent);
//                        SharedPreferencesUtils.remove(context, "update");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BufferedSource bufferedSource) {
                        try {
                            writeFile(bufferedSource, new File(apkPath));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    /**
     * 写入文件
     */
    private static void writeFile(BufferedSource source, File file) throws IOException {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        if (file.exists()) {
            file.delete();
        }

        BufferedSink bufferedSink = Okio.buffer(Okio.sink(file));
        bufferedSink.writeAll(source);

        bufferedSink.close();
        source.close();
    }

}
