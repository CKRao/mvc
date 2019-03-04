package com.clark.mvc.handler;

import com.clark.mvc.RequestHandlerChain;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: ClarkRao
 * @Date: 2019/3/4 22:23
 * @Description: 请求预处理
 */
@Slf4j
public class PreRequestHandler implements Handler {
    /**
     * 斜线
     */
    public static final String SLASH = "/";

    /**
     * 编码格式
     */
    public static final String CHARACTER = "UTF-8";

    @Override
    public boolean handle(RequestHandlerChain handlerChain) throws Exception {
        //设置请求编码格式
        handlerChain.getRequest().setCharacterEncoding(CHARACTER);
        String requestPath = handlerChain.getRequestPath();
        if (requestPath.length() > 0 && requestPath.endsWith(SLASH)) {
            handlerChain.setRequestPath(requestPath.substring(0, requestPath.length() - 1));
        }
        log.info("[ClarkMVC] {} {}", handlerChain.getRequestMethod(), handlerChain.getRequestPath());
        return true;
    }
}
