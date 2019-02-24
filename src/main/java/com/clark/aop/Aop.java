package com.clark.aop;

import com.clark.ProxyCreator;
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
    public void doAop() {
        beanContainer.getClassesBySuper(Advice.class)
                .stream()
                .filter(clz -> clz.isAnnotationPresent(Aspect.class))
                .forEach(clz -> {
                    final Advice advice = (Advice) beanContainer.getBean(clz);
                    Aspect aspect = clz.getAnnotation(Aspect.class);
                    beanContainer.getClassesByAnnotation(aspect.target())
                            .stream()
                            .filter(target -> !Advice.class.isAssignableFrom(target))
                            .filter(target -> !target.isAnnotationPresent(Aspect.class))
                            .forEach(target -> {
                                ProxyAdvisor proxyAdvisor = new ProxyAdvisor(advice);
                                Object proxy = ProxyCreator.createProxy(target, proxyAdvisor);
                                beanContainer.addBean(target, proxy);
                            });
                });
    }
}
