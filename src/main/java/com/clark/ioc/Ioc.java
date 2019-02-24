package com.clark.ioc;

import com.clark.core.BeanContainer;
import com.clark.util.ClassUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Optional;

/**
 * @Author: ClarkRao
 * @Date: 2019/2/24 13:16
 * @Description:
 */
@Slf4j
public class Ioc {
    /**
     * Bean容器
     */
    private BeanContainer beanContainer;

    public Ioc() {
        beanContainer = BeanContainer.getInstance();
    }
    /**
     * 执行Ioc
     */
    public void doIoc() {
        //遍历Bean容器中所有的Bean
        for (Class<?> clz : beanContainer.getClasses()){
            final Object targetBean = beanContainer.getBean(clz);
            Field[] fields = clz.getDeclaredFields();
            //遍历Bean中的所有属性
            for (Field field : fields) {
                // 如果该属性被Autowired注解，则对其注入
                if (field.isAnnotationPresent(Autowired.class)) {
                    final Class<?> fieldClass = field.getType();
                    Object fieldValue = getClassInstance(fieldClass);

                    if (fieldValue != null) {
                        ClassUtil.setFiled(field,targetBean,fieldValue);
                    }else {
                        throw new RuntimeException("无法注入对应的类，目标类型:" + fieldClass.getName());
                    }
                }
            }
        }
    }
    /**
     * 根据Class获取其实例或者实现类
     */
    private Object getClassInstance(Class<?> clz) {
        return Optional
                .ofNullable(beanContainer.getBean(clz))
                .orElseGet(() -> {
                    Class<?> implementClass = getImplementClass(clz);
                    if (null != implementClass) {
                        return beanContainer.getBean(implementClass);
                    }
                    return null;
                });
    }
    /**
     * 获取接口的实现类
     */
    private Class<?> getImplementClass(Class<?> interfaceClass) {
        return beanContainer.getClassesBySuper(interfaceClass)
                .stream()
                .findFirst()
                .orElse(null);
    }
}
