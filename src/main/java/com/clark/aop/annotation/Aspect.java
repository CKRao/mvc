package com.clark.aop.annotation;

import java.lang.annotation.*;

/**
 * @Author: ClarkRao
 * @Date: 2019/2/24 14:21
 * @Description:
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    /**
     * 目标代理类的范围
     */
    Class<? extends Annotation> target();
}
