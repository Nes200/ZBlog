package com.zlt.Utils;

import com.zlt.entity.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Authof: zlt
 * @Date: 2024-2-16 12:10
 */
//在'发送评论'功能那里会用到的工具类
public class SecurityUtils {
    /**
     * 获取用户的userid
     */
    public static LoginUser getLoginUser() {
        return (LoginUser) getAuthentication().getPrincipal();
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 指定userid为1的用户就是网站管理员
     */
    public static Boolean isAdmin() {
        Integer id = getLoginUser().getUser().getId();
        return id != null && 1L == id;
    }

    public static Integer getUserId(){
        return getLoginUser().getUser().getId();
    }
}
