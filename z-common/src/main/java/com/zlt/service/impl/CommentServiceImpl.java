package com.zlt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zlt.Utils.BeanCopyUtils;
import com.zlt.constants.SystemConstants;
import com.zlt.entity.Comment;
import com.zlt.entity.ResponseResult;
import com.zlt.entity.User;
import com.zlt.enums.AppHttpCodeEnum;
import com.zlt.exception.SystemException;
import com.zlt.mapper.CommentMapper;
import com.zlt.service.CommentService;
import com.zlt.service.UserService;
import com.zlt.vo.CommentVo;
import com.zlt.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author zlt
 * @since 2024-02-15 12:19:32
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private UserService userService;

    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Comment::getArticleId, articleId);

        //对评论区的某条评论的rootID进行判断，如果为-1。就表示是根评论
        queryWrapper.eq(Comment::getRootId, SystemConstants.COMMENT_ROOT);

        Page<Comment> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);

        List<CommentVo> commentVoList = xxToCommentList(page.getRecords());

        //添加子评论
        for (CommentVo commentVO :
                commentVoList) {
            List<CommentVo> children = getChildren(commentVO.getId());
            commentVO.setChildren(children);
        }

        return ResponseResult.okResult(new PageVo(commentVoList, page.getTotal()));
    }

    @Override
    public ResponseResult addComment(Comment comment) {
        //限制用户在发送评论时，评论内容不能为空。如果为空就抛出异常
        if (!StringUtils.hasText(comment.getContent())) {
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }

        save(comment);

        return ResponseResult.okResult();
    }

    private List<CommentVo> getChildren(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId, id);
        //对子评论按照时间进行排序
        queryWrapper.orderByDesc(Comment::getCreateBy);
        List<Comment> comments = list(queryWrapper);
        //查询子评论的过程中依然会遇到和查询根评论的时候相同的问题：comment表里面没有username字段
        List<CommentVo> commentVos = xxToCommentList(comments);
        return commentVos;
    }

    private List<CommentVo> xxToCommentList(List<Comment> list) {
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);

        //这里使用stream流可能存在一些问题（commentVos是操作流的对象，但是最终结果也赋值给了commentVos，我记得这种情况好像是应该使用for循环的）
        //实际运行好像并没有出现问题
        //Comment表里面没有username字段,通过遍历为CommentVo的username字段赋值
        commentVos = commentVos.stream()
                .map(new Function<CommentVo, CommentVo>() {
                    @Override
                    public CommentVo apply(CommentVo commentVo) {
                        Long createBy = commentVo.getCreateBy();
                        String nickName;
                        if (createBy == -1L) {
                            //匿名评论
                            nickName="匿名用户";
                        } else {
                            nickName = userService.getById(createBy).getUserName();
                        }
                        commentVo.setUsername(nickName);

                        //Comment表里面也没有toCommentUserName字段,通过遍历为CommentVo的toCommentUserName字段赋值
                        //判断getToCommentUserId为1，就表示这条评论是根评论
                        if (commentVo.getToCommentId() != -1) {
                            String toCommentName = userService.getById(commentVo.getToCommentUserId()).getNickName();
                            commentVo.setToCommentUserName(toCommentName);
                        }
                        return commentVo;
                    }
                }).collect(Collectors.toList());
        return commentVos;
    }
}
