package com.zlt.controller;

import com.zlt.entity.ResponseResult;
import com.zlt.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 用户表(User)表控制层
 *
 * @author zlt
 * @since 2024-02-12 23:26:24
 */
@RestController
@RequestMapping("user")
public class UserController  {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;

    @GetMapping("/userInfo")
    public ResponseResult userInfo(){
        return null;
    }
}

