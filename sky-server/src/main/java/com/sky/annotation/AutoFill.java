package com.sky.annotation;

import com.sky.enumeration.OperationType;

import java.lang.annotation.*;

/*
 * @author Sylphy
 * @Description
 * @create 2025/4/13
 * */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutoFill {

    //数据库操作类型
    OperationType value();
}
