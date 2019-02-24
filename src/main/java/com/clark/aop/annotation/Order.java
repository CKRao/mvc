package com.clark.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: ClarkRao
 * @Date: 2019/2/24 20:04
 * @Description: aop顺序
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Order {

    /**
     * aop顺序,值越大越先执行
     */
    int value() default 0;
}
