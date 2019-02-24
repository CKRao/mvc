package com.clark.test;

import com.clark.aop.advice.AroundAdvice;
import com.clark.aop.annotation.Aspect;
import com.clark.aop.annotation.Order;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * @Author: ClarkRao
 * @Date: 2019/2/24 20:19
 * @Description:
 */
@Slf4j
@Order(2)
@Aspect(pointcut =  "@within(com.clark.core.annotation.Controller)")
public class DoodleAspect2 implements AroundAdvice {

    @Override
    public void afterReturning(Class<?> clz, Object returnValue, Method method, Object[] args) throws Throwable {
        log.info("-----------after  DoodleAspect2-----------");
        log.info("class: {}, method: {}", clz, method.getName());
    }

    @Override
    public void before(Class<?> clz, Method method, Object[] args) throws Throwable {
        log.info("-----------before  DoodleAspect2-----------");
        log.info("class: {}, method: {}", clz.getName(), method.getName());
    }

    @Override
    public void afterThrowing(Class<?> clz, Method method, Object[] args, Throwable e) {
        log.error("-----------error  DoodleAspect2-----------");
        log.error("class: {}, method: {}, exception: {}", clz, method.getName(), e.getMessage());
    }
}
