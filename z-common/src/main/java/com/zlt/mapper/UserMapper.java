package com.zlt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zlt.entity.User;
import org.apache.ibatis.annotations.Mapper;


/**
 * 用户表(User)表数据库访问层
 *
 * @author zlt
 * @since 2024-02-12 23:26:24
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
