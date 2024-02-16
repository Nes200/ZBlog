package com.zlt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zlt.entity.ResponseResult;
import com.zlt.entity.User;
import com.zlt.mapper.UserMapper;
import com.zlt.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户表(User)表服务实现类
 *
 * @author zlt
 * @since 2024-02-12 23:26:24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
