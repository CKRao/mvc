package com.clark.mvc;

import lombok.*;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Author: ClarkRao
 * @Date: 2019/2/24 20:48
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ControllerInfo {
    /**
     * controller类
     */
    private Class<?> controllerClass;

    /**
     * 执行的方法
     */
    private Method invokeMethod;

    /**
     * 方法参数别名对应参数类型
     */
    private Map<String, Class<?>> methodParameter;
}
