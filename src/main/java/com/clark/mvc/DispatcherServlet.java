package com.clark.mvc;

import com.clark.mvc.handler.ControllerHandler;
import com.clark.mvc.handler.Handler;
import com.clark.mvc.handler.JspHandler;
import com.clark.mvc.handler.PreRequestHandler;
import com.clark.mvc.handler.SimpleUrlHandler;
import com.clark.util.ValidateUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ClarkRao
 * @Date: 2019/2/24 21:51
 * @Description:  DispatcherServlet 所有http请求都由此Servlet转发
 */
@Slf4j
public class DispatcherServlet extends HttpServlet {

    /**
     * 请求执行链
     */
    private final List<Handler> HANDLER = new ArrayList<>();

    /**
     * 初始化Servlet
     */
    @Override
    public void init() throws ServletException {
        HANDLER.add(new PreRequestHandler());
        HANDLER.add(new SimpleUrlHandler(getServletContext()));
        HANDLER.add(new JspHandler(getServletContext()));
        HANDLER.add(new ControllerHandler());
    }
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestHandlerChain handlerChain = new RequestHandlerChain(HANDLER.iterator(), req, resp);
        handlerChain.doHandlerChain();
        handlerChain.doRender();
    }
}
