package com.clark.aop;

import com.clark.aop.ProxyAdvisor;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @Author: ClarkRao
 * @Date: 2019/2/24 14:52
 * @Description: 代理类创建器
 */
public class ProxyCreator {
    /**
     * 创建代理类
     */
    public static Object createProxy(Class<?> targetClass, List<ProxyAdvisor> proxyList) {
        return Enhancer.create(targetClass,new AdviceMethodInterceptor(targetClass,proxyList));
    }

    /**
     * cglib MethodInterceptor实现类
     */
    private static class AdviceMethodInterceptor implements MethodInterceptor {
        /**
         * 目标类
         */
        private final Class<?> targetClass;
        /**
         * 代理通知列表
         */
        private List<ProxyAdvisor> proxyList;


        public AdviceMethodInterceptor(Class<?> targetClass, List<ProxyAdvisor> proxyList) {
            this.targetClass = targetClass;
            this.proxyList = proxyList;
        }

        @Override
        public Object intercept(Object target, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            return  new AdviceChain(targetClass, target, method, args, methodProxy, proxyList).doAdviceChain();
        }
    }
}
