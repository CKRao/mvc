package com.clark.aop.advice;

import java.lang.reflect.Method;

/**
 * @Author: ClarkRao
 * @Date: 2019/2/24 14:32
 * @Description: 前置通知接口
 */
public interface MethodBeforeAdvice extends Advice {

    /**
     * 前置方法
     */
    void before(Class<?> clz, Method method, Object[] args) throws Throwable;
}
