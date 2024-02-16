package com.zlt.exception;

import com.zlt.enums.AppHttpCodeEnum;

/**
 * @Authof: zlt
 * @Date: 2024-2-15 11:40
 */
//统一异常处理
public class SystemException extends RuntimeException{
    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    //定义一个构造方法，接收的参数是枚举类型

    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code=httpCodeEnum.getCode();
        this.msg=httpCodeEnum.getMsg();
    }
}
