package com.zlt.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Authof: zlt
 * @Date: 2024-2-13 8:49
 */
@Data
//chain的中文含义是链式的，设置为true，则setter方法返回当前对象
@Accessors(chain = true)
public class UserInfoVo {
    /**
     * 主键
     */
    private Long id;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

    private String sex;

    private String email;
}
