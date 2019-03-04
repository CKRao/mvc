package com.clark.mvc.handler;

import com.clark.ClarkMVC;
import com.clark.mvc.RequestHandlerChain;
import com.clark.util.ValidateUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

/**
 * @Author: ClarkRao
 * @Date: 2019/3/4 22:38
 * @Description: jsp请求处理, 主要负责jsp资源请求
 */
public class JspHandler implements Handler {
    /**
     * jsp请求的RequestDispatcher的名称
     */
    private static final String JSP_SERVLET = "jsp";

    /**
     * jsp的RequestDispatcher,处理jsp资源
     */
    private RequestDispatcher jspServlet;

    public JspHandler(ServletContext servletContext) {
        this.jspServlet = servletContext.getNamedDispatcher(JSP_SERVLET);

        if (ValidateUtil.isEmpty(jspServlet)) {
            throw new RuntimeException("没有jsp Servlet");
        }
    }

    @Override
    public boolean handle(RequestHandlerChain handlerChain) throws Exception {
        if (isPageView(handlerChain.getRequestPath())) {
            jspServlet.forward(handlerChain.getRequest(), handlerChain.getResponse());
            return false;
        }
        return true;
    }

    /**
     * 是否为jsp资源
     */
    private boolean isPageView(String url) {
        return url.startsWith(ClarkMVC.getConfiguration().getViewPath());
    }
}
