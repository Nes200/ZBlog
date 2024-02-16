package com.zlt.Utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Authof: zlt
 * @Date: 2024-2-11 11:27
 */
public class BeanCopyUtils {
    //私有的空参构造方法
    private BeanCopyUtils() {
    }

    //1.单个实体类的拷贝
    //第一个参数是要拷贝的对象，第二个参数是类的字节码对象
    public static <V> V copyBean(Object source, Class<V> vClass) {
        //创建目标对象
        V result = null;
        try {
            result = vClass.newInstance();
            //BeanUtils是spring提供的工具类
            BeanUtils.copyProperties(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //返回结果
        return result;
    }

    //集合的拷贝
    //第一个参数是要拷贝的集合
    //第二个参数是类的字节码对象
    public static <O,V> List<V> copyBeanList(List<O> list, Class<V> vClass){
        //不使用for循环，使用stream流进行转换
        return list.stream()
                .map(o->copyBean(o,vClass))
                //把结果转换成集合
                .collect(Collectors.toList());
    }

}
