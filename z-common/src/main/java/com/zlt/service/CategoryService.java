package com.zlt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zlt.entity.Category;
import com.zlt.entity.ResponseResult;

import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * 分类表(Category)表服务接口
 *
 * @author zlt
 * @since 2024-02-12 08:37:17
 */
public interface CategoryService extends IService<Category> {
    ResponseResult getCategoryList();
}
