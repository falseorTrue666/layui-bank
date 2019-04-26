package com.mr.utils;


import java.util.List;

/**
 * Created by DELL on 2019/4/8.
 */
public class LayResult<T> {
    private Integer code;
    private String msg;
    private Integer count;
    private List<T> Data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<T> getData() {
        return Data;
    }

    public void setData(List<T> data) {
        Data = data;
    }

    public LayResult(Integer code, String msg, Integer count, List<T> data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        Data = data;
    }

    public LayResult() {
    }

    public LayResult(List<T> data) {
        Data = data;
    }
}
