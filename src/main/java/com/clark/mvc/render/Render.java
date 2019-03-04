package com.clark.mvc.render;

import com.clark.mvc.RequestHandlerChain;

/**
 * @Author: ClarkRao
 * @Date: 2019/3/4 21:54
 * @Description: 渲染请求结果 interface
 */
public interface Render {

    void render(RequestHandlerChain handlerChain) throws Exception;
}
