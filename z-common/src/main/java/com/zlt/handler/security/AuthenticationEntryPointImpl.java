package com.zlt.handler.security;

import com.alibaba.fastjson.JSON;
import com.zlt.Utils.WebUtils;
import com.zlt.entity.ResponseResult;
import com.zlt.enums.AppHttpCodeEnum;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Authof: zlt
 * @Date: 2024-2-15 11:23
 */
@Component
//自定义认证失败的处理器
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        //输出异常信息
        e.printStackTrace();

        //判断是登录才出现异常(返回'用户名或密码错误')，还是没有登录就访问特定接口才出现的异常(返回'需要登录后访问')，还是其它情况(返回'出现错误')
        ResponseResult result=null;
        if(e instanceof BadCredentialsException){
            result =ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        }else if(e instanceof InsufficientAuthenticationException){
            result= ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }else {
            result=ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),"认证或授权失败");
        }
        //把上一行的result转换成JSON
        WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
    }
}
