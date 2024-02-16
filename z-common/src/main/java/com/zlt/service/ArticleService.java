package com.zlt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zlt.entity.Article;
import com.zlt.entity.ResponseResult;
import io.swagger.models.auth.In;


/**
 * 文章表(Article)表服务接口
 *
 * @author zlt
 * @since 2024-02-05 14:50:08
 */
public interface ArticleService extends IService<Article> {
    //热门文章列表
    ResponseResult hotArticleList();

    //分类查询文章列表
    ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId);

    //根据id查询文章详情
    ResponseResult getArticleDetail(Long id);
}
