package com.zlt.service;

import com.zlt.entity.ResponseResult;
import com.zlt.entity.User;

/**
 * @Authof: zlt
 * @Date: 2024-2-13 8:32
 */
public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
