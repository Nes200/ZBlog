package com.zlt.controller;

import com.zlt.entity.Article;
import com.zlt.entity.ResponseResult;
import com.zlt.service.ArticleService;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * 文章表(Article)表控制层
 *
 * @author zlt
 * @since 2024-02-05 14:50:08
 */
@RestController
@RequestMapping("article")
public class ArticleController  {
    /**
     * 服务对象
     */
    @Resource
    private ArticleService articleService;

    @GetMapping("/list")
    public List<Article> test(){
        return articleService.list();
    }

    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){
        //查询热门文章
        ResponseResult result = articleService.hotArticleList();
        return result;
    }

    //分页查询文章的列表
    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId){
        return articleService.articleList(pageNum,pageSize,categoryId);
    }

    //查询文章详情
    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){
        return articleService.getArticleDetail(id);
    }
}

