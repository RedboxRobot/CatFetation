package com.unlimiteduniverse.http.model;

import java.io.Serializable;

/**
 * @author ChengXinPing
 * @time 2018/8/23 16:49
 */
public class MultipartUploadResponse implements Serializable {

    /**
     * code : 0
     * data : {"file_id":482229468253913088,"block":4,"url":"http://192.168.0.202:8113/482229468253913088"}
     */

    private int code;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * file_id : 482229468253913088
         * block : 4
         * url : http://192.168.0.202:8113/482229468253913088
         */

        private long file_id;
        private int block;
        private String url;

        public long getFile_id() {
            return file_id;
        }

        public void setFile_id(long file_id) {
            this.file_id = file_id;
        }

        public int getBlock() {
            return block;
        }

        public void setBlock(int block) {
            this.block = block;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
