package com.clark;

import com.clark.aop.ProxyAdvisor;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * @Author: ClarkRao
 * @Date: 2019/2/24 14:52
 * @Description: 代理类创建器
 */
public class ProxyCreator {
    /**
     * 创建代理类
     */
    public static Object createProxy(Class<?> targetClass, ProxyAdvisor proxyAdvisor) {
        return Enhancer.create(targetClass,
                (MethodInterceptor) (target, method, args, proxy) ->
                        proxyAdvisor.doProxy(target, targetClass, method, args, proxy));
    }
}
