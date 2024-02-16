package com.zlt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zlt.entity.Article;
import org.apache.ibatis.annotations.Mapper;


/**
 * 文章表(Article)表数据库访问层
 *
 * @author zlt
 * @since 2024-02-05 14:50:08
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

}
