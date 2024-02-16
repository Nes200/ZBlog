package com.zlt.controller;

import com.zlt.entity.ResponseResult;
import com.zlt.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * 分类表(Category)表控制层
 *
 * @author zlt
 * @since 2024-02-12 08:37:17
 */
@RestController
@RequestMapping("category")
public class CategoryController  {
    /**
     * 服务对象
     */
    @Resource
    private CategoryService categoryService;

    @GetMapping("/getCategoryList")
    public ResponseResult getCategoryList(){
        return categoryService.getCategoryList();
    }
}

