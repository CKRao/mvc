package com.clark.mvc.render;

import com.clark.mvc.RequestHandlerChain;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: ClarkRao
 * @Date: 2019/3/4 22:11
 * @Description: 渲染500
 */
public class InternalErrorRender implements Render {
    @Override
    public void render(RequestHandlerChain handlerChain) throws Exception {
        handlerChain.getResponse().sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
}
