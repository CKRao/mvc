package com.clark.aop;

import com.clark.aop.advice.Advice;
import com.clark.aop.annotation.Aspect;
import com.clark.core.BeanContainer;
import lombok.extern.slf4j.Slf4j;

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
        beanContainer.getClassesBySuper(Advice.class)
                .stream()
                .filter(clz -> clz.isAnnotationPresent(Aspect.class))
                .map(this::createProxyAdvisor)
                .forEach(proxyAdvisor -> beanContainer.getClasses()
                        .stream()
                        .filter(target -> !Advice.class.isAssignableFrom(target))
                        .filter(target -> !target.isAnnotationPresent(Aspect.class))
                        .forEach(target -> {
                                if (proxyAdvisor.getPointcut().matches(target)) {
                                    Object proxyBean = ProxyCreator.createProxy(target, proxyAdvisor);
                                    beanContainer.addBean(target, proxyBean);
                                }
                        }));
    }

    /**
     * 通过Aspect切面类创建代理通知类
     */
    private ProxyAdvisor createProxyAdvisor(Class<?> aspectClass) {
        String expression = aspectClass.getAnnotation(Aspect.class).pointcut();
        ProxyPointcut proxyPointcut = new ProxyPointcut();
        proxyPointcut.setExpression(expression);
        Advice advice = (Advice) beanContainer.getBean(aspectClass);
        return new ProxyAdvisor(advice, proxyPointcut);
    }
}
