package com.pc3ryangzhang.fixnow.entity.ValueObject;
/**
 * Message Model object (data response)
 * state code
 * success 1, failed 0
 *
 * alert message : String
 * */
public class MessageModel {
    private Integer code;
    private String msg = "welcome";
    private Object object;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }



}
