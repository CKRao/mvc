package com.clark.aop.advice;

import java.lang.reflect.Method;

/**
 * @Author: ClarkRao
 * @Date: 2019/2/24 14:35
 * @Description: 异常通知接口
 */
public interface ThrowsAdvice extends Advice {

    /**
     * 异常方法
     */
    void afterThrowing(Class<?> clz, Method method, Object[] args, Throwable e);
}
