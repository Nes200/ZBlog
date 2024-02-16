package com.zlt.controller;

import com.zlt.constants.SystemConstants;
import com.zlt.entity.Comment;
import com.zlt.entity.ResponseResult;
import com.zlt.service.CommentService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Authof: zlt
 * @Date: 2024-2-15 18:01
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/commentList")
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize){
        return commentService.commentList(SystemConstants.ARTICLE_COMMENT,articleId,pageNum,pageSize);
    }

    @PostMapping
    public ResponseResult addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }

    @GetMapping("/linkCommentList")
    public ResponseResult linkCommentList(Integer pageNum,Integer pageSize){
        ResponseResult result = commentService.commentList(SystemConstants.LINK_COMMENT, null, pageNum, pageSize);
        return result;
    }
}
