package com.zlt.controller;

import com.zlt.entity.ResponseResult;
import com.zlt.service.LinkService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 友链(Link)表控制层
 *
 * @author zlt
 * @since 2024-02-12 20:59:03
 */
@RestController
@RequestMapping("link")
public class LinkController  {
    /**
     * 服务对象
     */
    @Resource
    private LinkService linkService;

    @GetMapping("/getAllLink")
    public ResponseResult getAllLink(){
        return linkService.getAllLink();
    }
}

