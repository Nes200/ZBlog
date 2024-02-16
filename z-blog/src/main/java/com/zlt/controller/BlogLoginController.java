package com.zlt.controller;

import com.zlt.entity.ResponseResult;
import com.zlt.entity.User;
import com.zlt.enums.AppHttpCodeEnum;
import com.zlt.exception.SystemException;
import com.zlt.service.BlogLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Authof: zlt
 * @Date: 2024-2-13 8:57
 */
@RestController
public class BlogLoginController {
    @Autowired
    private BlogLoginService blogLoginService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){
        //如果用户在登陆的时候，没有传入‘用户名’
        if(!StringUtils.hasText(user.getUserName())){
            throw new  SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return blogLoginService.login(user);
    }

    @PostMapping("/logout")
    public ResponseResult logout(){
        return blogLoginService.logout();
    }
}