package com.zlt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Authof: zlt
 * @Date: 2024-2-11 10:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
//返回给前端特定的字段
public class HotArticleVO {
    private Long id;
    //标题
    private String title;
    //访问量
    private Long viewCount;
}
