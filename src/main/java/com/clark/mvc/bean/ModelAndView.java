package com.clark.mvc.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ClarkRao
 * @Date: 2019/2/24 20:35
 * @Description: ModelAndView
 */
public class ModelAndView {
    /**
     * 页面路径
     */
    @Getter
    @Setter
    private String view;

    /**
     * 页面data数据
     */
    @Getter
    private Map<String, Object> model = new HashMap<>();

    public ModelAndView addObject(String name, Object attributeValue) {
        model.put(name, attributeValue);
        return this;
    }

    public ModelAndView addAllObjects(Map<String, ?> map) {
        model.putAll(map);
        return this;
    }
}
