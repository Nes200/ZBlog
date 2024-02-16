package com.zlt.exception;

import com.zlt.entity.ResponseResult;
import com.zlt.enums.AppHttpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Authof: zlt
 * @Date: 2024-2-15 11:43
 */
//@ControllerAdvice //对controller层的增强
//@ResponseBody

//或者用下面一个注解代替上面的两个注解
@RestControllerAdvice

//使用Lombok提供的Slf4j注解，实现日志功能
@Slf4j

//全局异常处理。最终都会在这个类进行处理异常
public class GlobalExceptionHandler {
    //SystemException是我们写的类。用户登录的异常交给这里处理
    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException e) {
        //{}是占位符
        log.error("出现了异常！{}", e);

        return ResponseResult.errorResult(e.getCode(), e.getMsg());
    }

    //其他异常交给这里处理
    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e){
        log.error("出现了异常！{}",e);

        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),e.getMessage());
    }
}
