package com.clark.aop.advice;

import java.lang.reflect.Method;

/**
 * @Author: ClarkRao
 * @Date: 2019/2/24 14:34
 * @Description: 返回通知接口
 */
public interface AfterReturningAdvice extends Advice{

    /**
     * 返回后方法
     */
    void afterReturning(Class<?> clz, Object returnValue, Method method, Object[] args)throws Throwable;
}
