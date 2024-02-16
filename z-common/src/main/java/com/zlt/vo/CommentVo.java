package com.zlt.vo;

import com.zlt.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.LifecycleState;

import java.util.Date;
import java.util.List;

/**
 * @Authof: zlt
 * @Date: 2024-2-15 17:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVo {
    private Long id;

    //文章id
    private Long articleId;
    //根评论id
    private Long rootId;
    //评论内容
    private String content;
    //发根评论的userid
    private Long toCommentUserId;
    //发根评论的userName
    private String toCommentUserName;
    //回复目标评论id
    private Long toCommentId;
    //当前评论的创建人id
    private Long createBy;

    private Date createTime;

    //评论是谁发的
    private String username;

    //查询子评论
    private List<CommentVo> children;
}
