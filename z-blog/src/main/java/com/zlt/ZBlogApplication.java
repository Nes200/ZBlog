package com.zlt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Authof: zlt
 * @Date: 2024-2-5 15:14
 */
@MapperScan("com.zlt.mapper")
@SpringBootApplication
public class ZBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZBlogApplication.class);
    }
}
