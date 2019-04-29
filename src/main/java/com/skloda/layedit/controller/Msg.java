package com.skloda.layedit.controller;

/**
 * @Author: jiangkun
 * @Description:
 * @Date: Created in 2019-04-28 18:34
 */
public class Msg {

    public static Msg success(String url, String name) {
        Data data = new Data(url, name);
        return new Msg(0, "上传成功", data);
    }


    public static Msg fail() {
        return new Msg(99, "上传失败", new Data());
    }

    private int code;
    private String msg;
    private Data data;

    public Msg(int code, String msg, Data data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    static class Data {

        String src;
        String title;

        public Data() {
        }

        public Data(String src, String title) {
            this.src = src;
            this.title = title;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
