package com.clark.test;

import com.clark.aop.advice.AroundAdvice;
import com.clark.aop.annotation.Aspect;
import com.clark.core.annotation.Controller;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * @Author: ClarkRao
 * @Date: 2019/2/24 15:01
 * @Description:
 */
@Slf4j
@Aspect(target = Controller.class)
public class DoodleAspect implements AroundAdvice {
    @Override
    public void afterReturning(Class<?> clz, Object returnValue, Method method, Object[] args) throws Throwable {
        log.info("After  DoodleAspect ----> class: {}, method: {}", clz, method.getName());
    }

    @Override
    public void before(Class<?> clz, Method method, Object[] args) throws Throwable {
        log.info("Before  DoodleAspect ----> class: {}, method: {}", clz.getName(), method.getName());
    }

    @Override
    public void afterThrowing(Class<?> clz, Method method, Object[] args, Throwable e) {
        log.error("Error  DoodleAspect ----> class: {}, method: {}, exception: {}", clz, method.getName(), e.getMessage());
    }
}
