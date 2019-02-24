package com.clark.mvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: ClarkRao
 * @Date: 2019/2/24 20:34
 * @Description: 请求的方法参数名
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestParam {
    /**
     * 方法参数别名
     */
    String value() default "";

    /**
     * 是否必传
     */
    boolean required() default true;
}
