package com.clark.mvc.render;

import com.clark.mvc.RequestHandlerChain;

/**
 * @Author: ClarkRao
 * @Date: 2019/3/4 22:15
 * @Description: 默认渲染200
 */
public class DefaultRender implements Render {
    @Override
    public void render(RequestHandlerChain handlerChain) throws Exception {
        int status = handlerChain.getResponseStatus();
        handlerChain.setResponseStatus(status);
    }
}
