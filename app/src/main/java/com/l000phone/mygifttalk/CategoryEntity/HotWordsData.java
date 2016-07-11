package com.l000phone.mygifttalk.categoryentity;

import java.util.List;

/**
 * 热词数据实体类
 * HotWords: http://api.liwushuo.com/v2/search/hot_words
 * Created by Leon on 2016/7/6.
 */

public class HotWordsData {

    /**
     * code : 200
     * data : {"hot_words":["零食","手机壳","双肩包","凉鞋","宿舍","钱包","情侣","手表","泳衣","杯子","连衣裙","手链"],"placeholder":"快选一份礼物，送给亲爱的Ta吧"}
     * message : OK
     */

    private int code;
    /**
     * hot_words : ["零食","手机壳","双肩包","凉鞋","宿舍","钱包","情侣","手表","泳衣","杯子","连衣裙","手链"]
     * placeholder : 快选一份礼物，送给亲爱的Ta吧
     */

    private DataBean data;
    private String message;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        private String placeholder;
        private List<String> hot_words;

        public String getPlaceholder() {
            return placeholder;
        }

        public void setPlaceholder(String placeholder) {
            this.placeholder = placeholder;
        }

        public List<String> getHot_words() {
            return hot_words;
        }

        public void setHot_words(List<String> hot_words) {
            this.hot_words = hot_words;
        }
    }
}
