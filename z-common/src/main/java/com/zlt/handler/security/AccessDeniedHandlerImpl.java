package com.zlt.handler.security;

import com.alibaba.fastjson.JSON;
import com.zlt.Utils.WebUtils;
import com.zlt.entity.ResponseResult;
import com.zlt.enums.AppHttpCodeEnum;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Authof: zlt
 * @Date: 2024-2-15 11:29
 */
//自定义授权失败的处理器
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        //输出异常信息
        e.printStackTrace();

        ResponseResult result = ResponseResult.okResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);

        WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
    }
}
