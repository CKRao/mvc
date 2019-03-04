package com.clark.mvc.render;

import com.clark.mvc.RequestHandlerChain;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: ClarkRao
 * @Date: 2019/3/4 22:16
 * @Description: 渲染404
 */
public class NotFoundRender implements Render {
    @Override
    public void render(RequestHandlerChain handlerChain) throws Exception {
        handlerChain.getResponse().sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}
