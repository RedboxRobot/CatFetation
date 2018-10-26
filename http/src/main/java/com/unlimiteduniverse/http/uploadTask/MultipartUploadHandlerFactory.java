package com.unlimiteduniverse.http.uploadTask;

import com.scott.annotionprocessor.ITask;
import com.scott.transer.handler.AbsHandlerFactory;
import com.scott.transer.handler.ITaskHandler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * @author ChengXinPing
 * @time 2018/8/21 16:16
 */

public class MultipartUploadHandlerFactory extends AbsHandlerFactory {

    @Override
    protected ITaskHandler create(ITask task) {
        String path = "Private/" + task.getName();
        try {
            path = URLEncoder.encode(path, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (path == null) {
            return null;
        }
        return new MultipartUploadHandler.Builder()
//                .disableAutoRetry()
                .addHeader("path", path)
                .build();
    }
}
