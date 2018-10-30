package com.unlimiteduniverse.http;

import com.unlimiteduniverse.http.base.BaseResponse;

import org.json.JSONObject;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * @author ChengXinPing
 * @time 2018/8/24 9:17
 */
public interface UploadService {
    String BASE_URL = "";

    /**
     * 文件上传完成或取消
     *
     * @param params 参数-->
     *               "id":1,              // 文件id
     *               "status":1,          // 文件状态 (如果上传成功传1)（取消上传传0）（上传失败传2）
     *               下面三个取消任务和上传失败不需要传
     *               "user_id":10086,      // 用户id
     *               "oss_path":  "https://file.delicloud.xin/test/100M.txt",    //文件下载地址
     *               "file_md5":"xxxxxxxx"  //文件MD5
     * @return
     */
    @POST("netdisk/finish")
    Observable<BaseResponse<JSONObject>> changeUploadStatus(@Body Map<String, Object> params);
}
