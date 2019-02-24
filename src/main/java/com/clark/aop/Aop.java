package com.clark.aop;

import com.clark.aop.advice.Advice;
import com.clark.aop.annotation.Aspect;
import com.clark.aop.annotation.Order;
import com.clark.core.BeanContainer;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: ClarkRao
 * @Date: 2019/2/24 14:44
 * @Description: Aop执行器
 */
@Slf4j
public class Aop {
    /**
     * Bean容器
     */
    private BeanContainer beanContainer;

    public Aop() {
        beanContainer = BeanContainer.getInstance();
    }
//    public void doAop() {
//        beanContainer.getClassesBySuper(Advice.class)
//                .stream()
//                .filter(clz -> clz.isAnnotationPresent(Aspect.class))
//                .forEach(clz -> {
//                    final Advice advice = (Advice) beanContainer.getBean(clz);
//                    Aspect aspect = clz.getAnnotation(Aspect.class);
//                    beanContainer.getClassesByAnnotation(aspect.target())
//                            .stream()
//                            .filter(target -> !Advice.class.isAssignableFrom(target))
//                            .filter(target -> !target.isAnnotationPresent(Aspect.class))
//                            .forEach(target -> {
//                                ProxyAdvisor proxyAdvisor = new ProxyAdvisor(advice);
//                                Object proxy = ProxyCreator.createProxy(target, proxyAdvisor);
//                                beanContainer.addBean(target, proxy);
//                            });
//                });
//    }

    public void doAop() {
        //创建所有的代理通知列表
        List<ProxyAdvisor> proxyAdvisorList = beanContainer.getClassesBySuper(Advice.class)
                .stream()
                .filter(clz -> clz.isAnnotationPresent(Aspect.class))
                .map(this::createProxyAdvisor)
                .collect(Collectors.toList());
        //创建代理类并注入到Bean容器中
        beanContainer.getClasses()
                .stream()
                .filter(clz -> !Advice.class.isAssignableFrom(clz))
                .filter(clz -> !clz.isAnnotationPresent(Aspect.class))
                .forEach(clz -> {
                    List<ProxyAdvisor> matchProxies = createMatchProxies(proxyAdvisorList, clz);
                    if (matchProxies.size() > 0) {
                        Object proxyBean = ProxyCreator.createProxy(clz,matchProxies);
                        beanContainer.addBean(clz, proxyBean);
                    }
                });
    }

    /**
     * 通过Aspect切面类创建代理通知类
     */
    private ProxyAdvisor createProxyAdvisor(Class<?> aspectClass) {
        int order = 0;
        if (aspectClass.isAnnotationPresent(Order.class)) {
            order = aspectClass.getAnnotation(Order.class).value();
        }
        String expression = aspectClass.getAnnotation(Aspect.class).pointcut();
        ProxyPointcut proxyPointcut = new ProxyPointcut();
        proxyPointcut.setExpression(expression);
        Advice advice = (Advice) beanContainer.getBean(aspectClass);
        return new ProxyAdvisor(order, advice, proxyPointcut);
    }

    /**
     * 获取目标类匹配的代理通知列表
     *
     * @param proxyList
     * @param targetClass
     * @return
     */
    private List<ProxyAdvisor> createMatchProxies(List<ProxyAdvisor> proxyList, Class<?> targetClass) {
        Object targetBean = beanContainer.getBean(targetClass);
        return proxyList
                .stream()
                .filter(proxyAdvisor -> proxyAdvisor.getPointcut().matches(targetBean.getClass()))
                .sorted(Comparator.comparing(ProxyAdvisor::getOrder))
                .collect(Collectors.toList());
    }
}
