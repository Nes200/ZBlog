package com.zlt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zlt.entity.Comment;
import com.zlt.entity.ResponseResult;


/**
 * 评论表(Comment)表服务接口
 *
 * @author zlt
 * @since 2024-02-15 12:19:32
 */
public interface CommentService extends IService<Comment> {
    //查询评论区得评论
    ResponseResult commentList(String commentType, Long articleId,Integer pageNum,Integer pageSize);

    //在文章的评论区发送评论
    ResponseResult addComment(Comment comment);
}
