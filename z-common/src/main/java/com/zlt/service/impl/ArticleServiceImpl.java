package com.zlt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zlt.Utils.BeanCopyUtils;
import com.zlt.constants.SystemConstants;
import com.zlt.entity.Article;
import com.zlt.entity.Category;
import com.zlt.entity.ResponseResult;
import com.zlt.mapper.ArticleMapper;
import com.zlt.service.ArticleService;
import com.zlt.service.CategoryService;
import com.zlt.vo.ArticleDetailVo;
import com.zlt.vo.ArticleListVo;
import com.zlt.vo.HotArticleVO;
import com.zlt.vo.PageVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.zlt.constants.SystemConstants.*;

/**
 * 文章表(Article)表服务实现类
 *
 * @author zlt
 * @since 2024-02-05 14:50:08
 */
//@Service("articleService")
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;

    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章，封装成ResponseResult返回，把所有的查询条件写在queryWrapper里面
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //查询的不能是草稿，也就是说status字段不能为1(0表示草稿)
        queryWrapper.eq(Article::getStatus, ARTICLE_STATUS_NORMAL);
        //按照访问量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //每页显示10条数据，当前显示第一页的数据
        Page<Article> page = new Page<>(ARTICLE_STATUS_CURRENT, ARTICLE_STATUS_SIZE);
        page(page, queryWrapper);
        //获取最终的查询结果
        List<Article> articles = page.getRecords();

        ////将结果封装到HotArticleVO里面
        //ArrayList<HotArticleVO> hotArticleVOS = new ArrayList<>();
        //for (Article xarticle :
        //        articles) {
        //    HotArticleVO hotArticleVO = new HotArticleVO();
        //    //使用spring提供的BeanUtils类，来实现bean拷贝。第一个参数是源数据，第二个参数是目标数据，把源数据拷贝给目标数据
        //    BeanUtils.copyProperties(xarticle, hotArticleVO);
        //    hotArticleVOS.add(hotArticleVO);
        //}
        //return ResponseResult.okResult(hotArticleVOS);

        //使用自定义的工具类，一行搞定
        List<HotArticleVO> hotArticleVOS = BeanCopyUtils.copyBeanList(articles, HotArticleVO.class);

        return ResponseResult.okResult(hotArticleVOS);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        //判空
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);

        //只查询状态是正式发布的文章
        lambdaQueryWrapper.eq(Article::getStatus, ARTICLE_STATUS_NORMAL);

        //实现置顶的文章(isTop值为1)在最前面
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);

        Page<Article> page = new Page<>(pageNum, pageSize);

        page(page, lambdaQueryWrapper);

        //通过Article的CategoryId字段为Article实体类的CategoryName字段赋值
        List<Article> articles = page.getRecords();
        List<Article> articleList = articles.stream()
                .map(new Function<Article, Article>() {
                    @Override
                    public Article apply(Article article) {
                        Long id = article.getCategoryId();
                        Category category = categoryService.getById(id);
                        article.setCategoryName(category.getName());
                        return article;
                    }
                }).collect(Collectors.toList());


        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articleList, ArticleListVo.class);
        PageVo pageVo = new PageVo(articleListVos, page.getTotal());
        return ResponseResult.okResult(pageVo);

    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        Article article = getById(id);

        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);

        Category category = categoryService.getById(articleDetailVo.getCategoryId());
        if(category!=null){
            String name = category.getName();
            category.setName(name);
        }
        return ResponseResult.okResult(category);
    }
}