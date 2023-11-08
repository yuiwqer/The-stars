package com.example.demo.util;

import cn.hutool.json.JSONObject;
import lombok.Data;

@Data
public class R {
    private int code;
    private String msg;
    private Object data;
    private R(){

    }
    public static String ok(int code,String msg,Object data){
        R r=new R();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return new JSONObject(r).toString();
    }
    public static String msg(int code,String msg){
        R r=new R();
        r.setCode(code);
        r.setMsg(msg);
        return new JSONObject(r).toString();
    }

    public static void main(String[] args) {
        System.out.println(R.msg(200,"你好"));
    }
}
