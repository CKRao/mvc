package com.clark.mvc.handler;

import com.clark.ClarkMVC;
import com.clark.mvc.RequestHandlerChain;
import com.clark.util.ValidateUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

/**
 * @Author: ClarkRao
 * @Date: 2019/3/4 22:30
 * @Description: 普通url请求执行，主要处理静态资源
 */
@Slf4j
public class SimpleUrlHandler implements Handler {

    /**
     * tomcat默认RequestDispatcher的名称
     * TODO: 其他服务器默认的RequestDispatcher.如WebLogic为FileServlet
     */
    private static final String TOMCAT_DEFAULT_SERVLET = "default";

    /**
     * 默认的RequestDispatcher,处理静态资源
     */
    private RequestDispatcher defaultServlet;

    public SimpleUrlHandler(ServletContext servletContext) {
        defaultServlet = servletContext.getNamedDispatcher(TOMCAT_DEFAULT_SERVLET);

        if (ValidateUtil.isEmpty(defaultServlet)) {
            throw new RuntimeException("没有默认的Servlet");
        }

        log.info("The default servlet for serving static resource is [{}]", TOMCAT_DEFAULT_SERVLET);
    }

    @Override
    public boolean handle(RequestHandlerChain handlerChain) throws Exception {
        if (isStaticResource(handlerChain.getRequestPath())) {
            defaultServlet.forward(handlerChain.getRequest(),handlerChain.getResponse());
            return false;
        }
        return true;
    }

    /**
     * 是否为静态资源
     * @param url
     * @return
     */
    private boolean isStaticResource(String url) {
        return url.startsWith(ClarkMVC.getConfiguration().getAssetPath());
    }
}
