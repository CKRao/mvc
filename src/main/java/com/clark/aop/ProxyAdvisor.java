package com.clark.aop;

import com.clark.aop.advice.Advice;
import com.clark.aop.advice.AfterReturningAdvice;
import com.clark.aop.advice.MethodBeforeAdvice;
import com.clark.aop.advice.ThrowsAdvice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author: ClarkRao
 * @Date: 2019/2/24 14:37
 * @Description:
 */
@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProxyAdvisor {
    /**
     * 通知
     */
    private Advice advice;

    /**
     * AspectJ表达式切点匹配器
     */
    private ProxyPointcut pointcut;

    /**
     * 执行代理方法
     */
    public Object doProxy(Object target, Class<?> targetClass, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        //如果表达式不匹配，则直接执行目标方法
        if (!pointcut.matches(method)) {
            return proxy.invokeSuper(target, args);
        }

        Object result = null;

        if (advice instanceof MethodBeforeAdvice) {
            ((MethodBeforeAdvice) advice).before(targetClass, method, args);
        }
        try {
            //执行目标类的方法
            result = proxy.invokeSuper(target,args);
            if (advice instanceof AfterReturningAdvice) {
                ((AfterReturningAdvice) advice).afterReturning(targetClass, result, method, args);
            }
        } catch (Exception e) {
            if(advice instanceof ThrowsAdvice) {
                ((ThrowsAdvice) advice).afterThrowing(targetClass, method, args, e);
            } else {
                throw new Throwable(e);
            }
        }
        return result;
    }
}
