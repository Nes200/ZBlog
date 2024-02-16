package com.zlt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zlt.entity.Link;
import com.zlt.entity.ResponseResult;


/**
 * 友链(Link)表服务接口
 *
 * @author zlt
 * @since 2024-02-12 20:59:03
 */
public interface LinkService extends IService<Link> {
    //查询友链
    ResponseResult getAllLink();
}
