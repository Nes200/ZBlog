package com.zlt.handler.mybatisPlus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.zlt.Utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Authof: zlt
 * @Date: 2024-2-16 12:16
 */
@Component
//这个类是用来配置mybatis的字段填充。用于'发送评论'的时候自动填充一些参数
//只要我们更新了评论表的字段，那么无法插入值得字段就自动有值了
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    //只要对数据库执行了插入语句，那么就会执行到这个方法
    public void insertFill(MetaObject metaObject) {
        Long userId=null;
        try {
            userId= SecurityUtils.getUserId().longValue();
            //SecurityUtils.getUserId()的值为null的时候并没有出现异常
            ////如果出现异常的话，会直接跳到catch里面
            //if(userId==null){
            //    userId=-1L;
            //}
        }catch (Exception e){
            e.printStackTrace();
            userId=-1L;//如果出现异常了，就说明该用户还没注册，我们就把该用户得userid字段赋值为-1
        }
        //自动为下面四个字段赋值
        this.setFieldValByName("createTime",new Date(),metaObject);
        this.setFieldValByName("createBy",userId,metaObject);
        this.setFieldValByName("updateTime",new Date(),metaObject);
        this.setFieldValByName("updateBy",userId,metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime",new Date(),metaObject);
        this.setFieldValByName("updateBy",SecurityUtils.getUserId(),metaObject);
    }
}
