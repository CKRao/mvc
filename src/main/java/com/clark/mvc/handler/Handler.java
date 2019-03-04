package com.clark.mvc.handler;

import com.clark.mvc.RequestHandlerChain;

/**
 * @Author: ClarkRao
 * @Date: 2019/3/4 21:50
 * @Description: 请求执行器接口
 */
public interface Handler {
    /**
     * 请求的执行器
     * @param handlerChain
     * @return
     * @throws Exception
     */
    boolean handle(final RequestHandlerChain handlerChain) throws Exception;
}
