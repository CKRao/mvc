package com.clark.util;

import java.util.Collection;
import java.util.Map;

/**
 * @Author: ClarkRao
 * @Date: 2019/2/21 21:16
 * @Description: 验证相关工具类
 */
public class ValidateUtil {

    /**
     * 判断对象是否为空
     * @param object
     * @return
     */
    public static boolean isEmpty(Object object) {
        return object == null;
    }

    /**
     * 判断数组是否为空
     * @param objects
     * @return
     */
    public static boolean isEmpty(Object[] objects) {
        return (objects == null || objects.length == 0);
    }
    /**
     * Collection是否为null或size为0
     *
     * @param obj Collection
     * @return 是否为空
     */
    public static boolean isEmpty(Collection<?> obj) {
        return obj == null || obj.isEmpty();
    }

    /**
     * 判断字符串是否为空
     * @param s
     * @return
     */
    public static boolean isEmpty(String s) {
        return (s == null || "".equals(s));
    }

    /**
     * 判断对象是否不为空
     * @param object
     * @return
     */
    public static boolean isNotEmpty(Object object) {
        return !isEmpty(object);
    }

    /**
     * 判断字符串是否不为空
     * @param object
     * @return
     */
    public static boolean isNotEmpty(String object) {
        return !isEmpty(object);
    }

    /**
     * Map是否为null或size为0
     *
     * @param obj Map
     * @return 是否为空
     */
    public static boolean isEmpty(Map<?, ?> obj) {
        return obj == null || obj.isEmpty();
    }


    /**
     * Array是否不为null或者size为0
     *
     * @param obj Array
     * @return 是否不为空
     */
    public static boolean isNotEmpty(Object[] obj) {
        return !isEmpty(obj);
    }

    /**
     * Collection是否不为null或size为0
     *
     * @param obj Collection
     * @return 是否不为空
     */
    public static boolean isNotEmpty(Collection<?> obj) {
        return !isEmpty(obj);
    }

    /**
     * Map是否不为null或size为0
     *
     * @param obj Map
     * @return 是否不为空
     */
    public static boolean isNotEmpty(Map<?, ?> obj) {
        return !isEmpty(obj);
    }

}
